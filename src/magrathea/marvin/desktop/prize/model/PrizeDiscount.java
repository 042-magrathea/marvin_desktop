package magrathea.marvin.desktop.prize.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author boscalent
 */
public class PrizeDiscount extends Prize {
    private static Disccount dsc;
    private Date expirationDate;
    
    public PrizeDiscount(){
        // defaults
        dsc = Disccount.A;
        expirationDate = Calendar.getInstance(Locale.GERMAN).getTime();
    }
}
