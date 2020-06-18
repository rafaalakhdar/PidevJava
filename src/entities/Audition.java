// 
// Decompiled by Procyon v0.5.36
// 

package entities;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
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
@Table(name = "audition")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Audition.findAll", query = "SELECT a FROM Audition a"), @NamedQuery(name = "Audition.findById", query = "SELECT a FROM Audition a WHERE a.id = :id"), @NamedQuery(name = "Audition.findByNom", query = "SELECT a FROM Audition a WHERE a.nom = :nom"), @NamedQuery(name = "Audition.findByDescription", query = "SELECT a FROM Audition a WHERE a.description = :description"), @NamedQuery(name = "Audition.findByDate", query = "SELECT a FROM Audition a WHERE a.date = :date"), @NamedQuery(name = "Audition.findByImage", query = "SELECT a FROM Audition a WHERE a.image = :image"), @NamedQuery(name = "Audition.findByVideo", query = "SELECT a FROM Audition a WHERE a.video = :video"), @NamedQuery(name = "Audition.findByChecked", query = "SELECT a FROM Audition a WHERE a.checked = :checked"), @NamedQuery(name = "Audition.findByQualified", query = "SELECT a FROM Audition a WHERE a.qualified = :qualified") })
public class Audition implements Serializable
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
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "image")
    private String image;
    @Basic(optional = false)
    @Column(name = "video")
    private String video;
    @Basic(optional = false)
    @Column(name = "checked")
    private boolean checked;
    @Basic(optional = false)
    @Column(name = "qualified")
    private boolean qualified;
    @JoinColumn(name = "categorieFk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Categorie categorieFk;
    @JoinColumn(name = "userFk", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userFk;
    
    public Audition() {
        this.nom = "newAudition";
        this.description = "newDescription";
        this.date = new Date();
        this.image = "newImage";
        this.video = "newVideo";
        this.checked = false;
        this.qualified = false;
        this.categorieFk = new Categorie(1, "newCategorie");
        this.userFk = new User(3);
    }
    
    public Audition(final Integer id) {
        this.id = id;
    }
    
    public Audition(final Integer id, final String nom, final String description, final Date date, final String image, final String video, final boolean checked, final boolean qualified) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.image = image;
        this.video = video;
        this.checked = checked;
        this.qualified = qualified;
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
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(final Date date) {
        this.date = date;
    }
    
    public String getImage() {
        return this.image;
    }
    
    public void setImage(final String image) {
        this.image = image;
    }
    
    public String getVideo() {
        return this.video;
    }
    
    public void setVideo(final String video) {
        this.video = video;
    }
    
    public boolean getChecked() {
        return this.checked;
    }
    
    public void setChecked(final boolean checked) {
        this.checked = checked;
    }
    
    public boolean getQualified() {
        return this.qualified;
    }
    
    public void setQualified(final boolean qualified) {
        this.qualified = qualified;
    }
    
    public Categorie getCategorieFk() {
        return this.categorieFk;
    }
    
    public void setCategorieFk(final Categorie categorieFk) {
        this.categorieFk = categorieFk;
    }
    
    public User getUserFk() {
        return this.userFk;
    }
    
    public void setUserFk(final User userFk) {
        this.userFk = userFk;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += ((this.id != null) ? this.id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof Audition)) {
            return false;
        }
        final Audition other = (Audition)object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }
    
    @Override
    public String toString() {
        return "entities.Audition[ id=" + this.id + " ]";
    }
}
