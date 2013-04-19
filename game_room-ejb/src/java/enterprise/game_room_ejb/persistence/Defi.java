/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprise.game_room_ejb.persistence;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author user
 */
@Entity
@Table(name = "defis")
public class Defi implements Serializable {
    @Id
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "defiantId")
    private Player defiant;
    
    @ManyToOne
    @JoinColumn(name = "defieId")
    private Player defie;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT")
    private java.util.Date createdAt;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getDefiant() {
        return defiant;
    }

    public void setDefiant(Player defiant) {
        this.defiant = defiant;
    }

    public Player getDefie() {
        return defie;
    }

    public void setDefie(Player defie) {
        this.defie = defie;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
