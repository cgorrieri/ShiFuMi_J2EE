/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.ejb.session;

import enterprise.game_room_ejb.common.PlayerNotFoundException;
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
    
    GameSessionBeanLocal accepterDefi(Long id);
    
    Player getPlayer();

    List<Player> getDefies();
    
    void defier(Long id);

    boolean addDefis(Long id);
    
    boolean isMessageForMe(Long id);
}
