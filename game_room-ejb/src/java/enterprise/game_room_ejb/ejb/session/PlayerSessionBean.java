/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.ejb.session;

import enterprise.game_room_ejb.common.PlayerNotFoundException;
import enterprise.game_room_ejb.persistence.Player;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@Stateful
public class PlayerSessionBean implements PlayerSessionBeanLocal {
    
    @PersistenceContext(unitName = "persistence_sample")
    private EntityManager em;

    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List getAllPlayers() {
        return (List)em.createNamedQuery("getAllPlayersExceptPseudo")
                .setParameter("pseudo", player.getPseudo())
                .getResultList();
    }
    
    @Override
    public List getConnectedPlayers() {
        return (List) em.createNamedQuery("getConnectedPlayersExceptPseudo")
                .setParameter("pseudo", player.getPseudo())
                .getResultList();
    }
    
    @Override
    public Player findPlayer(String pseudo, String mdp) throws PlayerNotFoundException {
        List l = (List)em.createNamedQuery("findPlayer")
                .setParameter("pseudo", pseudo)
                .setParameter("mdp", mdp)
                .getResultList();
        if(l.size() == 0) throw new PlayerNotFoundException();
        return (Player) l.get(0);
    }
    
    @Override
    public void connexion(String pseudo, String mdp) throws PlayerNotFoundException {
        List l = (List)em.createNamedQuery("findPlayer")
                .setParameter("pseudo", pseudo)
                .setParameter("mdp", mdp)
                .getResultList();
        if(l.size() == 0) throw new PlayerNotFoundException();
        player = (Player) l.get(0);
        player.setConnected(true);
        persist(player);
    }

    @Override
    public void defier(String pseudo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void accepterDefi(String pseudo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void persist(Object o) {
        em.persist(o);
    }

}
