/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.mdb;

/**
 *  Message d'un défi
 * 
 */
public class Defi extends Update {
    // L'id du joueur défié
    public Long defieId;
    
    public Defi(Long id, String pseudo, Long dest) {
        super(id, pseudo, TypeUpdate.DEFI);
        this.defieId = dest;
    }
    
}
