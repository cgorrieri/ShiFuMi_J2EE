/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.mdb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 *
 * @author user
 */
@MessageDriven(mappedName = "jms/ConnexionTopic", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "ConnexionMessageBean"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "ConnexionMessageBean")
})
public class ConnexionMessageBean implements MessageListener {
    
    public ConnexionMessageBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        if(message instanceof ObjectMessage) {
            try {
                ObjectMessage obj = (ObjectMessage) message;
                Update u = (Update) obj.getObject();
                if(u.type == TypeUpdate.CONNEXION) {
                    System.out.print("<!!"+u.pseudo+" est connecte!!>");
                } else if(u.type == TypeUpdate.DECONNEXION) {
                    System.out.print("<!!"+u.pseudo+" est deconnecte!!>");
                } else if(u.type == TypeUpdate.ACCEPTATION) {
                    System.out.print("<!!"+u.pseudo+" à accepter le defi de "+u.dest+"!!>");
                } else if(u.type == TypeUpdate.ANNULATION) {
                    System.out.print("<!! Le defi de "+u.pseudo+" à "+u.dest+" est annule!!>");
                } else {
                    System.out.println("<!!"+u.pseudo+" à défié "+u.dest+"!!>");
                }
            } catch (JMSException ex) {
                Logger.getLogger(ConnexionMessageBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
