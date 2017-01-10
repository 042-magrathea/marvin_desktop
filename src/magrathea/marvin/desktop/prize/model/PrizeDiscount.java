package magrathea.marvin.desktop.prize.model;

/**
 *
 * @author boscalent
 */
public class PrizeDiscount extends Prize {
    /*private static Disccount disccount;
    private Date dateEnd;
    
    public PrizeDiscount(){
        // defaults
        disccount = Disccount.A;
        dateEnd = Calendar.getInstance(Locale.GERMAN).getTime();
    } */
    
    private int disc;
    
    public PrizeDiscount( int disc){
        this.disc = disc;
    }

    public int getDisc() {
        return disc;
    }

    public void setDisc(int disc) {
        this.disc = disc;
    }
    
}
