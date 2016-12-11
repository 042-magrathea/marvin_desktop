package magrathea.marvin.desktop.prize.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author boscalent
 */
public class PrizeDiscount extends Prize {
    private static Disccount disccount;
    private Date dateEnd;
    
    public PrizeDiscount(){
        // defaults
        disccount = Disccount.A;
        dateEnd = Calendar.getInstance(Locale.GERMAN).getTime();
    }
}
