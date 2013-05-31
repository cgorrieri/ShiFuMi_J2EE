/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.ejb.session;

import enterprise.game_room_ejb.common.PlayerNotFoundException;
import enterprise.game_room_ejb.mdb.TypeUpdate;
import enterprise.game_room_ejb.mdb.Update;
import enterprise.game_room_ejb.persistence.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@Stateful
public class PlayerSessionBean implements PlayerSessionBeanLocal {
    
    @Resource(mappedName = "jms/ConnexionTopicCF")
    private TopicConnectionFactory topicConnectionFactory;
    
    @Resource(mappedName = "jms/ConnexionTopic")
    private Topic topic;
    
    /**
     * Liste des joueur qui a lancé un défi
     */
    private List<Player> defisRecus;
    
    /**
     * Les défis que nous avons lancé
     */
    //private List<Defi> defisLance;
    
    TopicConnection topicConnection;
    TopicSession topicSession;
    TopicPublisher topicPublisher;
    String subName;

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
        return (List) em.createNamedQuery("getAllPlayersExceptPseudo")
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
        List l = (List) em.createNamedQuery("findPlayer")
                .setParameter("pseudo", pseudo)
                .setParameter("mdp", mdp)
                .getResultList();
        if (l.isEmpty()) {
            throw new PlayerNotFoundException();
        }
        return (Player) l.get(0);
    }
    
    private void send(Update u) throws JMSException {
        // envoi du défis dans le topic
        ObjectMessage message = topicSession.createObjectMessage(u);
        topicPublisher.publish(message, DeliveryMode.PERSISTENT, 0, 1000);
    }

    @Override
    public void connexion(String pseudo, String mdp) throws PlayerNotFoundException {
        List l = (List) em.createNamedQuery("findPlayer")
                .setParameter("pseudo", pseudo)
                .setParameter("mdp", mdp)
                .getResultList();
        if (l.isEmpty()) {
            throw new PlayerNotFoundException();
        }
        player = (Player) l.get(0);
        player.setConnected(true);
        persist(player);
        
        //defisLance = new ArrayList<Defi>();
        defisRecus = new ArrayList<Player>();
        
        try {
            // Init de la connexion avec le topic
            topicConnection = topicConnectionFactory.createTopicConnection();
            topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            // Publish
            topicPublisher = topicSession.createPublisher(topic);
            // création et envoi du message
            send(new Update(player.getId(),player.getPseudo(),TypeUpdate.CONNEXION));
            
            // Subscribe
        } catch (JMSException e) {
            System.out.println("Exception occurred: " + e.toString());
        }
    }
    
    @Override
    @Remove
    public void deconnexion() {
        // Enregistrement en BDD que le player est déconnecter
        player.setConnected(false);
        em.merge(player);
        try {
            // Publish
            //topicPublisher = topicSession.createPublisher(topic);
            // création et envoi du message
            send(new Update(player.getId(),player.getPseudo(),TypeUpdate.DECONNEXION));
            // fermeture de toutes les connexions
            topicPublisher.close();
            topicSession.close();
            topicConnection.close();
            // Suppression du player de bean
            player = null;
        } catch (JMSException ex) {
            Logger.getLogger(PlayerSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @Override
//    public List getDefies() {
//        List players = new ArrayList();
//        Iterator<Defi> i = player.getDefiesRecu().iterator();
//        while(i.hasNext()) {
//            players.add(i.next().getDefiant());
//        }
//        return players;
//    }
    
    @Override
    public boolean isMessageForMe(Long id) {
        return (id == null || player.getId().equals(id));
    }
    
    @Override
    public List<Player> getDefies() {
        return defisRecus;
    }
    
    @Override
    public boolean addDefis(Long id) {
        Player p = (Player) em.find(Player.class, id);
        defisRecus.add(p);
        return true;
    }
    
    @Override
    public void defier(Long id) {
        try {
            // création du defi
            Update d = new Update(player.getId(), player.getPseudo(), TypeUpdate.DEFI, id);
            //defisLance.add(d);
            send(d);
        } catch (JMSException ex) {
            Logger.getLogger(PlayerSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void accepterDefi(Long id) {
        // On envoi le message comme quoi on a accepter
        try {
            // création du defi
            Update u = new Update(player.getId(), player.getPseudo(), TypeUpdate.ACCEPTATION, id);
            //defisLance.add(d);
            // envoi du défis dans le topic
            send(u);
        } catch (JMSException ex) {
            Logger.getLogger(PlayerSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Lancer processus de défi
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void persist(Object o) {
        em.persist(o);
    }
}
