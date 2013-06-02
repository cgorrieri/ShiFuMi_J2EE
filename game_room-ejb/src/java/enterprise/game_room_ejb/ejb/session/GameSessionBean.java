/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.ejb.session;

import enterprise.game_room_ejb.common.EnumGame;
import enterprise.game_room_ejb.persistence.Player;
import java.util.concurrent.Semaphore;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Singleton
@Lock(LockType.READ)
public class GameSessionBean implements GameSessionBeanLocal {
    private Semaphore sema = new Semaphore(0);
    
    // id des joueurs
    private Player j1, j2;

    @Override
    @Lock(LockType.WRITE)
    public void addPlayer(Player p) {
        if(j1 == null) {
            j1 = p;
            System.out.println("<Player 1 : "+j1.getPseudo());
        }
        else if(j2 == null){
            j2 = p;
            System.out.println("<Player 2 : "+j2.getPseudo());
        }
    }
    
    // Ce que joue les joueur
    private EnumGame j1Val = EnumGame.UNKNOW, j2Val = EnumGame.UNKNOW;
    private EnumGame j1ValSave = EnumGame.UNKNOW, j2ValSave = EnumGame.UNKNOW;
    // quel joueur gagne
    private int gagne;
    private boolean firstime;
    private boolean restart = true;
    private int tour = 1;

    @Override
    public EnumGame getOtherVal(Long id) {
        if(j1.getId().equals(id)) return j2ValSave;
        else return j1ValSave;
    }

    @Override
    public void sendChoice(Long id, EnumGame j) {
        try{
            if (j1.getId().equals(id)) {
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
                System.out.println(j1Val +", "+j2Val);
                gagne = EnumGame.getWhoWin(j1Val, j2Val);
                System.out.println("Gagne: "+gagne);
                if (gagne != 0) {
                    tour--;
                }
                if (tour == 0) {
                    // save scores
                }
                firstime = false;
                restart = true;
                j1ValSave = j1Val;
                j1Val = EnumGame.UNKNOW;
                j2ValSave = j2Val;
                j2Val = EnumGame.UNKNOW;
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
    
    
}
