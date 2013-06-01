/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.ejb.session;

import enterprise.game_room_ejb.common.EnumGame;
import enterprise.game_room_ejb.persistence.Player;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class GameSessionBean implements GameSessionBeanLocal {
    // id des joueurs

    private Player j1, j2;

    @Override
    public void setJ1(Player j1) {
        this.j1 = j1;
    }

    @Override
    public void setJ2(Player j2) {
        this.j2 = j2;
    }
    // Ce que joue les joueur
    private EnumGame j1Val = EnumGame.UNKNOW, j2Val = EnumGame.UNKNOW;
    // quel joueur gagne
    private int gagne;
    private boolean firstime = true;
    private int tour = 1;

    @Override
    public EnumGame getJ1Val() {
        return j1Val;
    }

    @Override
    public EnumGame getJ2Val() {
        return j2Val;
    }

    @Override
    public void jouer(Long id, EnumGame j) {
        try{
            if (j1.getId().equals(id)) {
                j1Val = j;
                while (j2Val == EnumGame.UNKNOW) wait();
            } else {
                j2Val = j;
                while (j1Val == EnumGame.UNKNOW) wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getResult(Long id) {
        if (firstime) {
            gagne = EnumGame.getWhoWin(j1Val, j2Val);
            if (gagne != 0) {
                tour--;
            }
            if (tour == 0) {
                // save scores
            }
        }
        if (j1.getId().equals(id)) {
            return gagne;
        } else {
            return 0 - gagne;
        }
    }

    @Override
    public int getTour() {
        return tour;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
