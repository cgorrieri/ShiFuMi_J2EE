/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.common;

/**
 *
 * @author user
 */
public enum EnumGame {
    PIERRE("pierre"), FEUILLE("feuille"), CISEAU("ciseau");
    
    String elem;
    
    EnumGame(String elem){
        this.elem = elem;
    }
    
    public String getElem(){
        return elem;
    }
    
    /**
     * Retourne le numero de joueur qui gagne
     * @param joueur1
     * @param joueur2
     * @return 0 si ex-equo, 1 si le joueur 1 gagne, 2 si le joueur 2 gagne, -1 si non defini
     */
    public int getWhoWin(EnumGame joueur1, EnumGame joueur2){
        if(joueur1.equals(joueur2)) {
            return 0;
        }
        if(joueur1.equals(PIERRE.getElem()) && joueur2.equals(FEUILLE.getElem()) ){
            return 2;
        }
        
        if(joueur1.equals(FEUILLE.getElem()) && joueur2.equals(PIERRE.getElem()) ){
            return 1;
        }
        
        if(joueur1.equals(PIERRE.getElem()) && joueur2.equals(CISEAU.getElem()) ){
            return 1;
        }
        
        if(joueur1.equals(CISEAU.getElem()) && joueur2.equals(PIERRE.getElem()) ){
            return 2;
        }
        
        if(joueur1.equals(FEUILLE.getElem()) && joueur2.equals(CISEAU.getElem()) ){
            return 2;
        }
        
        if(joueur1.equals(CISEAU.getElem()) && joueur2.equals(FEUILLE.getElem()) ){
            return 1;
        }
        
        return -1;
    }
}
