/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.ejb.session;

import enterprise.game_room_ejb.common.EnumGame;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface GameSessionBeanLocal {
    void sendChoice(Long id, EnumGame j);

    public int getResult(java.lang.Long id);

    public int getTour();

    public void addPlayer(enterprise.game_room_ejb.persistence.Player j1);

    public enterprise.game_room_ejb.common.EnumGame getOtherVal(Long id);
}
