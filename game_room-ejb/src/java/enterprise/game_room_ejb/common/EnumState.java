/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.common;

/**
 *
 * @author fabien cyril
 */
public enum EnumState {
    ATTENTE("En Attente..."), JOUE("En train de jouer..."), ENDEFIE("En defie..."),
    DECO("Deconnecté");
    
    public String etat;
    private EnumState(String state) {
        etat = state;
    }
    
    public String toString(){
        return etat;
    }
}
