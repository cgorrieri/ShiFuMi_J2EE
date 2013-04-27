/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.mdb;

/**
 * Message d'annulation de défi
 * Lorsque le délai d'acceptation est écoulé
 */
public class Annulation extends Update {
    // Id du joueur défié
    public Long who;
    
    public Annulation(Long id, String pseudo, Long who) {
        super(id, pseudo, TypeUpdate.ANNULATION);
        this.who = who;
    }
    
}
