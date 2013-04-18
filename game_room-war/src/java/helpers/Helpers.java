package helpers;


import enterprise.game_room_ejb.persistence.Player;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class Helpers {
    /**
     * Transforme la liste des joueurs en ligne de tableau HTML
     * @param players
     * @return Les joueurs en <tr>
     */
    public static String playersListToHTML(List players) {
        String resultat = "";
        for (int i = 0; i < players.size(); i++) {
            Player p = (Player) players.get(i);
            resultat += "<tr onclick=\"activdesactiv('<% out.print(p.getPseudo());%>');\" >"
                    + "<td><input id=" + p.getPseudo() + " type=\"radio\" name=\"id\" value=" + p.getId() + "/></td>"
                    + "<td>" + p.getPseudo() + "</td>"
                    + "<td>" + p.getScore() + "</td>"
                    + "<td>" + p.getEtat() + "</td>"
                    + "</tr>";
        }
        return resultat;
    }
}
