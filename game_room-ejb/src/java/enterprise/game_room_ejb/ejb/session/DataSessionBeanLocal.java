/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.ejb.session;

import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface DataSessionBeanLocal {

    void register(long Id, PlayerSessionBeanLocal Bean);

    PlayerSessionBeanLocal getClient(long Id);
    
}
