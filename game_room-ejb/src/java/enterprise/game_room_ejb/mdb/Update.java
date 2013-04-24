/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.mdb;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class Update implements Serializable {
    public Long id;
    public String pseudo;
    public int points;
    public boolean connected;
    
    public Update(Long id, String pseudo, int points, boolean connected) {
        this.id = id;
        this.pseudo = pseudo;
        this.points = points;
        this.connected = connected;
    }
    
}
