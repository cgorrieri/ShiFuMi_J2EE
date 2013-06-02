/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.ejb.session;

import enterprise.game_room_ejb.common.PlayerNotFoundException;
import enterprise.game_room_ejb.persistence.Player;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;

/**
 *
 * @author user
 */
@Local
public interface PlayerSessionBeanLocal {
    List getAllPlayers();
    
    List getConnectedPlayers();

    Player findPlayer(String login, String mdp) throws PlayerNotFoundException;
    
    void connexion(String login, String mdp) throws PlayerNotFoundException;
    
    void deconnexion();
    
    GameSessionBeanLocal acceptChallenge(Long id);
    
    Player getPlayer();

    List<Player> getChallenges();
    
    void challengePlayer(Long id);

    boolean addChallenge(Player p);
    
    boolean isMessageForMe(Long id);

    public GameSessionBeanLocal getGameStub();

    public void startGame(GameSessionBeanLocal gSBL);

    void register(Player player) throws PersistenceException, EntityExistsException;

    void endGame();
}
