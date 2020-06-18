// 
// Decompiled by Procyon v0.5.36
// 

package entities;

import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.ManyToMany;
import javax.persistence.Basic;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"), @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"), @NamedQuery(name = "User.findByNom", query = "SELECT u FROM User u WHERE u.nom = :nom"), @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"), @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"), @NamedQuery(name = "User.findBySexe", query = "SELECT u FROM User u WHERE u.sexe = :sexe"), @NamedQuery(name = "User.findByPays", query = "SELECT u FROM User u WHERE u.pays = :pays"), @NamedQuery(name = "User.findByStatus", query = "SELECT u FROM User u WHERE u.status = :status") })
public class User implements Serializable
{
    @Column(name = "enabled")
    private Boolean enabled;
    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "userFk")
    private Collection<Audition> auditionCollection;
    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "userId")
    private Collection<Categorie> categorieCollection;
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
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "sexe")
    private String sexe;
    @Basic(optional = false)
    @Column(name = "pays")
    private String pays;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @ManyToMany(mappedBy = "userCollection")
    private Collection<Conversation> conversationCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Message> messageCollection;
    
    public User() {
        this.id = 1000;
        this.nom = "nom";
        this.email = "email";
        this.password = "password";
        this.sexe = "sexe";
        this.pays = "pays";
        this.status = "status";
        this.enabled = null;
    }
    
    public User(final Integer id) {
        this.id = id;
    }
    
    public User(final Integer id, final String nom, final String sexe) {
        this.id = id;
        this.nom = nom;
        this.sexe = sexe;
    }
    
    public User(final Integer id, final String nom, final String email, final String password, final String sexe, final String pays, final String status, final int enabled, final Collection<Conversation> conversationCollection, final Collection<Message> messageCollection) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.sexe = sexe;
        this.pays = pays;
        this.status = status;
        this.enabled = null;
        this.conversationCollection = conversationCollection;
        this.messageCollection = messageCollection;
    }
    
    public User(final Integer id, final String nom, final String email, final int enabled) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.enabled = null;
    }
    
    public User(final String nom, final String email, final String password, final String sexe, final String pays) {
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.sexe = sexe;
        this.pays = pays;
    }
    
    public User(final Integer id, final String nom, final String email, final String password, final String sexe, final String pays, final String status, final int enabled) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.sexe = sexe;
        this.pays = pays;
        this.status = status;
        this.enabled = null;
    }
    
    public User(final Integer id, final String nom, final String email, final String password, final String sexe, final String pays, final String status) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.sexe = sexe;
        this.pays = pays;
        this.status = status;
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
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(final String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public String getSexe() {
        return this.sexe;
    }
    
    public void setSexe(final String sexe) {
        this.sexe = sexe;
    }
    
    public String getPays() {
        return this.pays;
    }
    
    public void setPays(final String pays) {
        this.pays = pays;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(final String status) {
        this.status = status;
    }
    
    @XmlTransient
    public Collection<Conversation> getConversationCollection() {
        return this.conversationCollection;
    }
    
    public void setConversationCollection(final Collection<Conversation> conversationCollection) {
        this.conversationCollection = conversationCollection;
    }
    
    @XmlTransient
    public Collection<Message> getMessageCollection() {
        return this.messageCollection;
    }
    
    public void setMessageCollection(final Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += ((this.id != null) ? this.id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        final User other = (User)object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }
    
    @Override
    public String toString() {
        return "" + this.id + "";
    }
    
    public Boolean getEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }
    
    @XmlTransient
    public Collection<Audition> getAuditionCollection() {
        return this.auditionCollection;
    }
    
    public void setAuditionCollection(final Collection<Audition> auditionCollection) {
        this.auditionCollection = auditionCollection;
    }
    
    @XmlTransient
    public Collection<Categorie> getCategorieCollection() {
        return this.categorieCollection;
    }
    
    public void setCategorieCollection(final Collection<Categorie> categorieCollection) {
        this.categorieCollection = categorieCollection;
    }
}
