/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.mdb;

/**
 * Message de connexion/déconnexion
 * Un joueur envoi ce message avec sa description
 * lorsqu'il se connecte ou déconnecte
 */
public class DeConnection extends Update {
    public int points;
    public boolean connected;
    
    public DeConnection(Long id, String pseudo, int points, boolean connected) {
        super(id, pseudo, TypeUpdate.CONNEXION);
        this.points = points;
        this.connected = connected;
    }
}
