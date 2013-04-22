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
            resultat += "<tr>"
                    + "<td>" + p.getPseudo() + "</td>"
                    + "<td>" + p.getScore() + "</td>"
                    + "<td>" + p.getEtat() + "</td>"
                    + "<td><a onclick='defier(" + p.getId() + ");'>défier</a></td>"
                    + "</tr>";
        }
        return resultat;
    }
}
