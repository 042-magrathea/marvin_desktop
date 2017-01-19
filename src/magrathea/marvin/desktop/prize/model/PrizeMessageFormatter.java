package magrathea.marvin.desktop.prize.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import magrathea.marvin.desktop.app.service.LoginService;

public class PrizeMessageFormatter {

    // Return i18N
    ResourceBundle bundle;

    private enum State {
        UNASSIGNED, ASSIGNED, NO_ASSIGNED, // Tournament
        RUNNING, WINNER, AVAILABLE, // User
        PRIZE, READY, LOST, DELIVERED, ACTIVE, EXPIRED // Date & Boolean
    }

    private PrizeMessageFormatter() {
        bundle = LoginService.getInstance().getBundle();
    }

    ////////////////////// SINGLETON ///////////////////////////
    private static class LazyHolder {

        private static final PrizeMessageFormatter INSTANCE = new PrizeMessageFormatter();
    }

    public static PrizeMessageFormatter getInstance() {
        return LazyHolder.INSTANCE;
    }
    ////////////////////// END SINGLETON ////////////////////////

    public String getPrizeInfoMessage(Prize prize, boolean verbose) /*throws PrizeException*/ {
        State state;
        // try {
        // PASS 1 Check for Tournament
        // NO_ASSIGNED & UNASSIGNE can't be assigned
        // Only for internal stuff.
        
        if (prize.getIdTournament() == null ) { //0
            state = State.NO_ASSIGNED;
        } else {
            state = State.ASSIGNED;
        }

        // PASS 2 Check for User
        switch (state) {
            case ASSIGNED:
                if (prize.getIdUser() == null) {
                    state = State.RUNNING;
                } else {
                    state = State.WINNER;
                }
                break;
            case NO_ASSIGNED:
                if (prize.getIdUser() == null) {
                    state = State.AVAILABLE;
                } else {
                    state = State.UNASSIGNED;
                    //throw new PrizeException("A prize can be assigned out of tournament");
                }
                break;
            default:
                //throw new PrizeException("Initial state for Prize. Can't set Tournament state");
                System.err.println("Initial state for Prize. Can't set Tournament state");
        }

        // PASS 3 Check for DATE & BOOL for ever Subtype
        // ONLY For ASSIGNED
        if (state == State.WINNER) {
            String subtype = prize.getTypePrice();
            LocalDate localDateTime = LocalDate.now();
            LocalDate datePrize = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = prize.getDate();

            switch (subtype) {
                case "Prize":
                    state = State.PRIZE;
                    break;
                case "PrizeDiscount":
                    datePrize = LocalDate.parse(date, formatter);
                    if (!localDateTime.isAfter(datePrize)) {
                        state = State.ACTIVE;
                    } else {
                        state = State.EXPIRED;
                    }
                    break;
                case "PrizeMerchant":    // Merchant
                    boolean delivered = ((PrizeMerchant) prize).isDelivered();
                    if (delivered) {
                        state = State.DELIVERED;
                    } else {
                        datePrize = LocalDate.parse(date, formatter);
                        if (localDateTime.isAfter(datePrize)) {
                            state = State.LOST;
                        } else {
                            state = State.READY;
                        }
                    }                
            }
            //System.out.println(state);
        }

        if (verbose == false) {
            // i18N response
            return bundle.getString("prize_state_" + state.name());
        } else {
            return prettyPrint(prize, state);
        }

        //return state.name();
        //} finally { return state.name(); }
    }

    private String prettyPrint(Prize prize, State state) {
        StringBuilder sb = new StringBuilder();
        
        // TYPE
        String type = prize.getTypePrice();
        switch (type){
            case "Prize": type = bundle.getString("prize_filter_prize"); break;
            case "PrizeDiscount": type = bundle.getString("prize_filter_discount");break;
            case "PrizeMerchant": type = bundle.getString("prize_filter_merchant");        
        }
        
        sb.append(type);
        sb.append(" - ");
        sb.append(bundle.getString("prize_state_" + state.name()).toUpperCase());
        sb.append("\n================================\n");

        // TOURNAMENT
        switch (state) {
            case UNASSIGNED: break;
            case NO_ASSIGNED: 
                sb.append(bundle.getString("prize_state_NO_ASSIGNED_TOURNAMENT")); break;
            default:
                sb.append(String.format( 
                        bundle.getString("prize_state_message_ASSIGNED"), 
                        prize.getIdTournament()));
        }
        // sb.append("\n");
        sb.append(". ");
        
        // USER
        switch (state) {
            case RUNNING:
                // sb.append(bundle.getString("prize_state_RUNNING_TOURNAMENT"));
                sb.append(bundle.getString("prize_state_message_RUNNING")); break;
            case AVAILABLE:
                sb.append(bundle.getString("prize_state_message_AVAILABLE")); break;
            case UNASSIGNED:
                 NO_ASSIGNED: break;    
            default: 
                sb.append(String.format(
                        bundle.getString("prize_state_message_WINNER"),
                        prize.getIdUser()));
        }
        sb.append("\n");
                
        // DATE & BOOL
        switch ( state ){
            case PRIZE:
                sb.append(bundle.getString("prize_state_message_PRIZE")); break;
            case READY:
            case LOST:
            case DELIVERED:
            case ACTIVE:
            case EXPIRED:
                sb.append(String.format(
                        bundle.getString( "prize_state_message_" + state.name() ),
                        prize.getDate()));break;
            default:                
        }

        return sb.toString();
    }

    private static class PrizeException extends Exception {

        public PrizeException(String message) {
            System.err.println("PrizeException: " + message);
        }
    }
}
