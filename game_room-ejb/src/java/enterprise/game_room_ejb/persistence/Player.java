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
    value={@NamedQuery(name="findPlayer", query="select object(c) from Player c where c.pseudo= :pseudo and c.mdp= :mdp"),
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
    private String mdp;
    @NotNull
    private boolean connected = false;
//    
//    // Relation avec le champ privé "defie" de Defi
//    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "defie")
//    private List<Defi> defiesRecu;
//    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy = "defiant")
//    private List<Defi> defiesLance;
//    
    private int points;
    private EnumState etat;

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
        points = 0;
        etat = EnumState.ATTENTE;
    }

    public Player(String pseudo, String email, String mdp) {
        this.pseudo = pseudo;
        this.email = email;
        this.mdp = mdp;
    }
    
    public Player(String pseudo, String email, String mdp, EnumState etat, int po){
        this.pseudo = pseudo;
        this.email = email;
        this.mdp = mdp;
        this.points = po;
        this.etat = etat;
    }
    
    public int getScore() {
        return 0;
    }
    
    public String getEtat() {
        return "En Attente";
    }

//    public List<Defi> getDefiesRecu() {
//        return defiesRecu;
//    }
//
//    public void setDefiesRecu(List<Defi> defiesRecu) {
//        this.defiesRecu = defiesRecu;
//    }
//
//    public List<Defi> getDefiesLance() {
//        return defiesLance;
//    }
//
//    public void setDefiesLance(List<Defi> defiesLance) {
//        this.defiesLance = defiesLance;
//    }

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
    
        /**
     * Nombre de point à ajouter au score
     * Avec verification pour ne par aller en dessous de zero
     * @param nbToAdd 
     */
    public void addScore(int nbToAdd){
        if((points + nbToAdd) < 0) {
            points = 0;
        }
        points += nbToAdd;
    }
    
    /**
     * Modification du score
     * @param score 
     */
    public void setScore(int score){
        points = score;
    }   
    
    /**
     * Modification de l'etat du joueur
     * @param etat 
     */
    public void setEtat(EnumState etat){
        this.etat = etat;
    }

//    /**
//     * Récupère tous les défies lancé
//     * @return 
//     */
//    @Transient
//    //since the signature starts with a get, need to annotate it as @Transient
//    public ArrayList getDefiesRecu() {
//        ArrayList list = new ArrayList();
//        Iterator c = defiRecu.iterator();
//        while (c.hasNext()) {
//            list.add((Player)c.next());
//        }
//        return list;
//    }
}
