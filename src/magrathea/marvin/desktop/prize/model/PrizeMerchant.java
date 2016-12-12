/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.prize.model;

import java.util.Date;

/**
 *
 * @author boscalent
 */
public class PrizeMerchant extends Prize {
    // If claimed_B is false, datePick is for user notification.
    // If claimed_B is true, datePick is the date of delivery.
    private Date receptionDate;
    private boolean claimed_B;
    
    public PrizeMerchant(){
        
    }
}
