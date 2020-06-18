// 
// Decompiled by Procyon v0.5.36
// 

package entities;

import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.Collection;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Basic;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "categorie")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Categorie.findAll", query = "SELECT c FROM Categorie c"), @NamedQuery(name = "Categorie.findById", query = "SELECT c FROM Categorie c WHERE c.id = :id"), @NamedQuery(name = "Categorie.findByNom", query = "SELECT c FROM Categorie c WHERE c.nom = :nom"), @NamedQuery(name = "Categorie.findByDate", query = "SELECT c FROM Categorie c WHERE c.date = :date") })
public class Categorie implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nom")
    private String nom;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "categorieFk")
    private Collection<Audition> auditionCollection;
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;
    
    public Categorie() {
        this.id = 1;
        this.nom = "newCategorie";
        this.date = new Date();
        this.userId = new User(3);
    }
    
    public Categorie(final int id) {
        this.id = id;
    }
    
    public Categorie(final String nom, final int userId) {
        this.nom = nom;
        this.userId = new User(userId);
    }
    
    public Categorie(final int i, final String newCategorie) {
        this.id = i;
        this.nom = newCategorie;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(final String nom) {
        this.nom = nom;
    }
    
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(final Date date) {
        this.date = date;
    }
    
    @XmlTransient
    public Collection<Audition> getAuditionCollection() {
        return this.auditionCollection;
    }
    
    public void setAuditionCollection(final Collection<Audition> auditionCollection) {
        this.auditionCollection = auditionCollection;
    }
    
    public User getUserId() {
        return this.userId;
    }
    
    public void setUserId(final User userId) {
        this.userId = userId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += ((this.id != null) ? this.id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof Categorie)) {
            return false;
        }
        final Categorie other = (Categorie)object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }
    
    @Override
    public String toString() {
        return "" + this.id + "";
    }
}
