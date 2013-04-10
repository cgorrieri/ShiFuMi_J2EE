/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.persistence;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author user
 */
@Entity
@NamedQueries(
    value={@NamedQuery(name="findPlayer", query="select object(c) from Player c where c.pseudo= :pseudo and c.mdp= :mdp"),
    @NamedQuery(name="getAllPlayers", query="select object(c) from Player c"),
    @NamedQuery(name="getAllPlayersExceptPseudo", query="select object(c) from Player c where c.pseudo <> :pseudo"),
    @NamedQuery(name="getConnectedPlayersExceptPseudo", query="select object(c) from Player c where c.pseudo <> :pseudo and c.connected = 1")}
)
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @NotNull
    private String pseudo;
    @NotNull
    private String email;
    @NotNull
    private String mdp;
    @NotNull
    private boolean connected = false;

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    
    public boolean getConnected() {
        return connected;
    }
    
    public void setConnected(boolean b) {
        connected = b;
    }
    

    public Player() {
        this.pseudo = "pseudo";
        this.email = "email";
        this.mdp = "mdp";
    }

    public Player(String pseudo, String email, String mdp) {
        this.pseudo = pseudo;
        this.email = email;
        this.mdp = mdp;
    }
    
    public int getScore() {
        return 0;
    }
    
    public String getEtat() {
        return "En Attente";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "enterprise.belli_gorrieri_lestel_shifumi_ejb.persistence.Player1[ id=" + id + " ]";
    }
}
