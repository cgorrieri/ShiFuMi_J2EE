/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.mdb;

/**
 * Message d'acceptation de défi
 * Lorsqu'un joueur accepte un defi (valide)
 */
public class Acceptation extends Update {
    // Id du joueur qui avait lancé le défi
    public Long who;
    
    public Acceptation(Long id, String pseudo, Long who) {
        super(id, pseudo, TypeUpdate.ACCEPTATION);
        this.who = who;
    }
    
}
