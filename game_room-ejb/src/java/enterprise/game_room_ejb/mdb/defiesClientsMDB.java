/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.mdb;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 * @author user
 */
public class defiesClientsMDB {
    @Resource(mappedName = "jms/ConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(mappedName = "jms/Queue")
    private static Queue queue;
    
    Connection connection = null;
    Session session = null;
    MessageProducer messageProducer = null;
    TextMessage message = null;

    public defiesClientsMDB() throws JMSException {
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        messageProducer = session.createProducer(queue);
        message = session.createTextMessage();
    }
    
    public void sendMessage(String mes) throws JMSException{
        message.setText(mes);
        messageProducer.send(message);
    }
    
    public void main4(String[] args) {
        final int NUM_MSGS = 3;
        
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            messageProducer = session.createProducer(queue);
            message = session.createTextMessage();
            
            for (int i = 0; i < NUM_MSGS; i++) {
                message.setText("This is message " + (i + 1));
                System.out.println("Sending message: " + message.getText());
                messageProducer.send(message);
            }
            
            System.out.println("To see if the bean received the messages,");
            System.out.println( "check <install_dir>/domains/domain1/logs/server.log.");
            
        } catch (JMSException e) {
            System.out.println("Exception occurred: " + e.toString());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {}
            } // if
            System.exit(0);
        } // finally
    }
}
