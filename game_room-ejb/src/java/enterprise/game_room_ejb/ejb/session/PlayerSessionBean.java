/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.ejb.session;

import enterprise.game_room_ejb.common.PlayerNotFoundException;
import enterprise.game_room_ejb.mdb.Connexion;
import enterprise.game_room_ejb.mdb.Deconnexion;
import enterprise.game_room_ejb.mdb.Update;
import enterprise.game_room_ejb.persistence.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
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
    
    TopicConnection topicConnection;
    TopicSession topicSession;
    TopicPublisher topicPublisher;
    String subName;
    MessageConsumer topicSubscriber;

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
        
//        try {
//            // Init de la connexion avec le topic
//            topicConnection = topicConnectionFactory.createTopicConnection();
//            topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
//            // Publish
//            topicPublisher = topicSession.createPublisher(topic);
//            // création et envoi du message
//            ObjectMessage message = topicSession.createObjectMessage(new Connexion(player.getId(),player.getPseudo(),player.getScore()));
//            topicPublisher.publish(message);
//            // fermeture car on ne plishera plus rien jusqu'a la fermeture de la session
//            topicPublisher.close();
//            // Subscribe
//            topicSubscriber = topicSession.createDurableSubscriber(topic, subName);
//        } catch (JMSException e) {
//            System.out.println("Exception occurred: " + e.toString());
//        }
    }
    
//    public List<Update> getUpdates() {
//        
//            List<Update> res = new ArrayList<Update>();
//            Message m;
//        try {
//            while((m = topicSubscriber.receive(100)) != null) {
//                res.add((Update)((ObjectMessage)m).getObject());
//            }
//            return res;
//        } catch (JMSException ex) {
//            Logger.getLogger(PlayerSessionBean.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }
    
    @Override
    @Remove
    public void deconnexion() {
        // Enregistrement en BDD que le player est déconnecter
        player.setConnected(false);
        em.merge(player);
        // Suppression du player de bean
        player = null;
//        try {
//            // Publish
//            topicPublisher = topicSession.createPublisher(topic);
//            // création et envoi du message
//            ObjectMessage message = topicSession.createObjectMessage(new Deconnexion(player.getId()));
//            topicPublisher.publish(message);
//            // fermeture de toutes les connexions
//            topicPublisher.close();
//            topicSubscriber.close();
//            topicConnection.close();
//        } catch (JMSException ex) {
//            Logger.getLogger(PlayerSessionBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
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

    @Override
    public List<Update> getUpdates() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
