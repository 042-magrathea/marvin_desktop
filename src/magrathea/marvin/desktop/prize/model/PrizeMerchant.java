/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.prize.model;

/**
 *
 * @author boscalent
 */
public class PrizeMerchant extends Prize {
    // If delivered is false, datePick is for user notification.
    // If delivered is true, datePick is the date of delivery.
    private boolean delivered;
    
    public PrizeMerchant( boolean delivered){
        this.delivered = delivered;
        super.setType("PrizeMerchant");
    }
    
    ////////
    // TO MUCH CASTING IN CONTROLLER
    ////////
    @Override
    public String getTypePrice(){
        return "PrizeMerchant";
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
}
