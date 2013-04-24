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
abstract public class Update implements Serializable {
    public TypeUpdate type;
    public Long id;
    public String pseudo;
    
    public Update(Long id, String pseudo, TypeUpdate type) {
        this.type = type;
        this.id = id;
        this.pseudo = pseudo;
    }
}
