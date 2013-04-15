/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.persistence;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author user
 */
@Stateless
@LocalBean
public class Defies implements Serializable{
       
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    /**
     * Un defie se fait entre deux players
     * Le player1 est celui qui à lancé le defie
     * Le player2 est celui qui accept ou pas le defi 
     */
    private Player player1;
    private Player player2;

    public Defies() {
        player1 = null;
        player2 = null;
    }
    
    /**
     * Constructeur normal
     * @param Player p1
     * @param Player p2 
     */
    public Defies(Player p1, Player p2) {
        player1 = p1;
        player2 = p2;
    }
}
