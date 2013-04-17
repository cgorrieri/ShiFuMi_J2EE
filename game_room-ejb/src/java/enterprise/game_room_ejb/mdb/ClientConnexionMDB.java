/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.mdb;

import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *  Cette classe permet de traiter les MDB provenant des clients qui se connectent
 *  Elle traitent les messages provenant de la queue jms/QueueConnexions
 * @author user
 */
@MessageDriven(mappedName = "jms/QueueConnexions", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ClientConnexionMDB implements MessageListener {
    
        static final Logger logger = Logger.getLogger("MessageConnxion");
    
    @Resource
    private MessageDrivenContext mdc;
    
    public ClientConnexionMDB() {
    }
    
    @Override
    public void onMessage(Message message) {
        TextMessage msg = null;
        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                logger.info("------> : " + msg.getText());
            } else {
                logger.warning("Message of wrong type: " + message.getClass().getName());
            }
        } catch (JMSException e) {
            e.printStackTrace();
            mdc.setRollbackOnly();
        } catch (Throwable te) {
            te.printStackTrace();
        }
    }
}
