/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.common;

import java.io.Serializable;

/**
 * Enumération représentant les choix dans le jeu
 * @author Fabien
 */
public enum EnumGame implements Serializable {
    ROCK, PAPER, SCISSORS, UNKNOW;
    
    /**
     * Retourne le numero de joueur qui gagne
     * @param joueur1
     * @param joueur2
     * @return 0 si ex-equo ou non défini, 1 si le joueur 1 gagne, -1 s'il pert
     */
    public static int getWhoWin(EnumGame joueur1, EnumGame joueur2){
        if(joueur1 == ROCK && joueur2 == PAPER){
            return -1;
        }
        else if(joueur1 == PAPER && joueur2 == ROCK ){
            return 1;
        }
        else if(joueur1 == ROCK && joueur2 == SCISSORS ){
            return 1;
        }
        else if(joueur1 == SCISSORS && joueur2 == ROCK ){
            return -1;
        }
        else if(joueur1 == PAPER && joueur2 == SCISSORS ){
            return -1;
        }
        else if(joueur1 == SCISSORS && joueur2 == PAPER ){
            return 1;
        }
        else return 0;
    }
}
