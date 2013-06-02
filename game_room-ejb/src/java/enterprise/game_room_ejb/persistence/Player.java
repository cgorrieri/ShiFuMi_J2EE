/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.persistence;

import enterprise.game_room_ejb.common.EnumState;
import java.io.Serializable;
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
    value={@NamedQuery(name="findPlayer", query="select object(c) from Player c where c.pseudo= :pseudo and c.password= :password"),
    @NamedQuery(name="getAllPlayers", query="select object(c) from Player c"),
    @NamedQuery(name="getAllPlayersExceptPseudo", query="select object(c) from Player c where c.pseudo <> :pseudo"),
    @NamedQuery(name="getConnectedPlayersExceptPseudo", query="select object(c) from Player c where c.pseudo <> :pseudo and c.connected = 1")}
)
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="player_id", nullable =false)
    private Long id;
    @Column(unique=true)
    private String pseudo;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private boolean connected = false;
//    
//    // Relation avec le champ privé "defie" de Defi
//    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "defie")
//    private List<Defi> defiesRecu;
//    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy = "defiant")
//    private List<Defi> defiesLance;
//    
    private int played = 0;
    private int won = 0;
    private EnumState state;

    

    public Player() {
        this.pseudo = "pseudo";
        this.email = "email";
        this.password = "1234";
        state = EnumState.DECONNECTED;
    }

    public Player(String pseudo, String email, String mdp) {
        this.pseudo = pseudo;
        this.email = email;
        this.password = mdp;
    }
    
    public Player(String pseudo, String email, String mdp, EnumState etat){
        this.pseudo = pseudo;
        this.email = email;
        this.password = mdp;
        this.state = etat;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String pswd) {
        this.password = pswd;
    }
    
    public boolean getConnected() {
        return connected;
    }
    
    public void setConnected(boolean b) {
        connected = b;
    }
    
    public double getScore() {
        if(played > 0) return won/played;
        return 0;
    }
    
    /**
     * 
     * @param win -1 or 1
     */
    public void addScore(int win) {
        played++;
        won += win;
    }
    
    public EnumState getEtat() {
        return state;
    }
    
    /**
     * Modification de l'state du joueur
     * @param state 
     */
    public void setEtat(EnumState etat){
        this.state = etat;
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
        return "Player{" + "id=" + id + ", pseudo=" + pseudo + ", email=" + email + ", played=" + played + ", won=" + won + ", state=" + state + '}';
    }

    
}
