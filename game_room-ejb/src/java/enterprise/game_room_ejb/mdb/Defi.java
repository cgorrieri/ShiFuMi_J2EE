/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.mdb;

import java.util.Date;

/**
 *
 * @author user
 */
public class Defi extends Update {
    public Long defieId;
    public Date launch;
    
    public Defi(Long id, String pseudo, Long dest) {
        super(id, pseudo, TypeUpdate.DEFI);
        this.defieId = dest; 
        this.launch = new Date();
    }
    
}
