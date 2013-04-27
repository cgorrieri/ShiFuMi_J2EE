/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.mdb;

import java.io.Serializable;

/**
 * Base de message envoyé par un joueur
 */
abstract public class Update implements Serializable {
    // type de la mise à jour
    public TypeUpdate type;
    // Id du joueur émétant
    public Long id;
    // Pseudo du joueur émétant
    public String pseudo;
    
    public Update(Long id, String pseudo, TypeUpdate type) {
        this.type = type;
        this.id = id;
        this.pseudo = pseudo;
    }
}
