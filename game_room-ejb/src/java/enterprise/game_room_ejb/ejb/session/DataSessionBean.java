/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.ejb.session;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.Singleton;

/**
 *
 * @author user
 */
@Singleton
public class DataSessionBean implements DataSessionBeanLocal {
    
    private Map<Long, PlayerSessionBeanLocal> clients = new HashMap<Long, PlayerSessionBeanLocal>();

    @Override
    public void register(long Id, PlayerSessionBeanLocal Bean) {
        clients.put(Id, Bean);
        System.out.println("<Register ID:"+Id+">");
    }

    @Override
    public PlayerSessionBeanLocal getClient(long Id) {
        return clients.get(Id);
    }
}
