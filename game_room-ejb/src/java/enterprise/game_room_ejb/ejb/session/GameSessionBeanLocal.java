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
    void jouer(Long id, EnumGame j);

    public int getResult(java.lang.Long id);

    public int getTour();

    public void setJ1(enterprise.game_room_ejb.persistence.Player j1);

    public void setJ2(enterprise.game_room_ejb.persistence.Player j2);

    public enterprise.game_room_ejb.common.EnumGame getJ1Val();

    public enterprise.game_room_ejb.common.EnumGame getJ2Val();
}
