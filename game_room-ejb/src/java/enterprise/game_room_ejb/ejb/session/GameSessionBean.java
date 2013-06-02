/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.ejb.session;

import enterprise.game_room_ejb.common.EnumGame;
import java.util.concurrent.Semaphore;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class GameSessionBean implements GameSessionBeanLocal {
    
    private Semaphore sema = new Semaphore(0);
    
    // id des joueurs
    private PlayerSessionBeanLocal j1, j2;

    @Override
    public void addPlayer(PlayerSessionBeanLocal p) {
        if(j1 == null) {
            j1 = p;
        }
        else if(j2 == null){
            j2 = p;
        }
    }
    
    // Ce que joue les joueur
    private EnumGame j1Val = EnumGame.UNKNOW, j2Val = EnumGame.UNKNOW;
    private EnumGame j1ValSave = EnumGame.UNKNOW, j2ValSave = EnumGame.UNKNOW;
    // quel joueur gagne
    private int whoWin;
    private boolean firstime;
    private boolean restart = true;
    private int step = 1;

    @Override
    public EnumGame getOtherVal(Long id) {
        if(j1.getPlayer().getId().equals(id)) return j2ValSave;
        else return j1ValSave;
    }

    @Override
    public void sendChoice(Long id, EnumGame j) {
        try{
            if (j1.getPlayer().getId().equals(id)) {
                j1Val = j;
                if (j2Val == EnumGame.UNKNOW) sema.acquire();
                else sema.release();
            } else {
                j2Val = j;
                if (j1Val == EnumGame.UNKNOW) sema.acquire();
                else sema.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(restart) {
                firstime = true;
                restart = false;
            }
        }
    }

    @Override
    public int getResult(Long id) {
        if (firstime) {
            whoWin = EnumGame.getWhoWin(j1Val, j2Val);
            if (whoWin != 0) {
                step--;
            }
            if (step == 0) {
                // save scores
                j1.getPlayer().addScore(whoWin);
                //j1.endGame(); // erreur lors de la sauvegarde
                j2.getPlayer().addScore(0-whoWin);
                //j2.endGame();
            }
            firstime = false;
            restart = true;
            j1ValSave = j1Val;
            j1Val = EnumGame.UNKNOW;
            j2ValSave = j2Val;
            j2Val = EnumGame.UNKNOW;
        }
        if (j1.getPlayer().getId().equals(id)) {
            return whoWin;
        } else {
            return 0 - whoWin;
        }
    }

    @Override
    public int getStep() {
        return step;
    }    
}
