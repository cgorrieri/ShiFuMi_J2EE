/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.ejb.session;

import enterprise.game_room_ejb.common.EnumGame;
import javax.ejb.Local;

/**
 * Interface
 * @author Cyril
 */
@Local
public interface GameSessionBeanLocal {
    void sendChoice(Long id, EnumGame j);

    public int getResult(Long id);

    public int getStep();

    public void addPlayer(PlayerSessionBeanLocal j1);

    public EnumGame getOtherVal(Long id);
}
