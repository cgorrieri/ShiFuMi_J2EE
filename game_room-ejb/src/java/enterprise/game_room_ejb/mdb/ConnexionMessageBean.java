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
                Update c = (Update) obj.getObject();
                System.out.println("<!!"+c.pseudo+","+c.id+"!!>");
            } catch (JMSException ex) {
                Logger.getLogger(ConnexionMessageBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
