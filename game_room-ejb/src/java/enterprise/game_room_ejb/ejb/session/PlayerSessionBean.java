/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.ejb.session;

import com.sun.xml.ws.api.tx.at.Transactional;
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
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

/**
 *
 * @author user
 */
@Stateful
public class PlayerSessionBean implements PlayerSessionBeanLocal {
    @Resource
    SessionContext sessionContext;
    
    private DataSessionBeanLocal dataSB;
    
    private GameSessionBeanLocal gameSBL = null;
    
    @Resource(mappedName = "jms/ConnexionTopicCF")
    private TopicConnectionFactory topicConnectionFactory;
    
    @Resource(mappedName = "jms/ConnexionTopic")
    private Topic topic;
    
    /**
     * Liste des joueur qui a lancé un défi
     */
    private List<Player> challengesReceived;
    
    TopicConnection topicConnection;
    TopicSession topicSession;
    TopicPublisher topicPublisher;
    String subName;

    @PersistenceContext(unitName = "persistence_sample")
    private EntityManager em;
    private Player player;

    @Override
    public Player getPlayer() {
        return player;
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
    public Player findPlayer(String pseudo, String password) throws PlayerNotFoundException {
        List l = (List) em.createNamedQuery("findPlayer")
                .setParameter("pseudo", pseudo)
                .setParameter("password", password)
                .getResultList();
        if (l.isEmpty()) {
            throw new PlayerNotFoundException();
        }
        return (Player) l.get(0);
    }
    
    private void send(Update u) throws JMSException {
        // envoi du défis dans le topic
        ObjectMessage message = topicSession.createObjectMessage(u);
        topicPublisher.publish(message);
    }
    
    private void initSession(Player p) {
        p.setConnected(true);
        persist(p);
        player = p;
        
        challengesReceived = new ArrayList<Player>();
        
        dataSB = (DataSessionBeanLocal) sessionContext.lookup("java:global/game_room/game_room-ejb/DataSessionBean");
        dataSB.register(player.getId(), this);
        
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
    public void connexion(String pseudo, String password) throws PlayerNotFoundException {
        List l = (List) em.createNamedQuery("findPlayer")
                .setParameter("pseudo", pseudo)
                .setParameter("password", password)
                .getResultList();
        if (l.isEmpty()) {
            throw new PlayerNotFoundException();
        }
        
        initSession((Player) l.get(0));
    }
    
    @Override
    public void register(Player player) throws PersistenceException, EntityExistsException {
        // vérifiaction des champs
        
        // enregistrement
        initSession(player);
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
//    public List getChallenges() {
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
    public List<Player> getChallenges() {
        return challengesReceived;
    }
    
    @Override
    public boolean addChallenge(Player p) {
        challengesReceived.add(p);
        return true;
    }
    
    @Override
    public void challengePlayer(Long id) {
        try {
            dataSB.getClient(id).addChallenge(player);
            // création du defi
            Update d = new Update(player.getId(), player.getPseudo(), TypeUpdate.CHALLENGE, id);
            //defisLance.add(d);
            send(d);
        } catch (JMSException ex) {
            Logger.getLogger(PlayerSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public GameSessionBeanLocal acceptChallenge(Long id) {
        // On envoi le message comme quoi on a accepter
        try {
            gameSBL = new GameSessionBean();
            gameSBL.addPlayer(this);
            
            dataSB.getClient(id).startGame(gameSBL);
            // création du defi
            Update u = new Update(player.getId(), player.getPseudo(), TypeUpdate.CHANLLENGE_ACCEPTED, id);
            // envoi du défis dans le topic
            send(u);
            return gameSBL;
        } catch (JMSException ex) {
            Logger.getLogger(PlayerSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        // Lancer processus de défi
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Transactional
    public void persist(Object o) throws PersistenceException, EntityExistsException {
        em.persist(o);
    }

    @Override
    public GameSessionBeanLocal getGameStub() {
        return gameSBL;
    }

    @Override
    public void startGame(GameSessionBeanLocal gSBL) {
        this.gameSBL = gSBL;
        this.gameSBL.addPlayer(this);
    }

    @Override
    public void endGame() {
        em.merge(player);
    }
    
    

}
