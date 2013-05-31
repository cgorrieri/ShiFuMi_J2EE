/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.mdb;

import enterprise.game_room_ejb.ejb.session.GameSessionBeanLocal;

/**
 *
 * @author user
 */
public class UpdateAcceptation extends Update {
    public GameSessionBeanLocal gsb;
    
    public UpdateAcceptation(Long id, String pseudo, Long dest, GameSessionBeanLocal gsb) {
        super(id, pseudo, TypeUpdate.ACCEPTATION, dest);
        this.gsb = gsb;
    }
}
