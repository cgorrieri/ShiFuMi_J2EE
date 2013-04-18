/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.ejb.session;

import enterprise.game_room_ejb.common.PlayerNotFoundException;
import enterprise.game_room_ejb.mdb.Update;
import enterprise.game_room_ejb.persistence.Player;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PlayerSessionBeanLocal {
    void persist(Object o);
    
    List getAllPlayers();
    
    List getConnectedPlayers();

    Player findPlayer(String login, String mdp) throws PlayerNotFoundException;
    
    void connexion(String login, String mdp) throws PlayerNotFoundException;
    
    void deconnexion();
    
    void defier(String pseudo);
    
    void accepterDefi(String pseudo);
    
    Player getPlayer();

    public List<Update> getUpdates();
}
