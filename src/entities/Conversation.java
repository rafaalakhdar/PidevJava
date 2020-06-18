// 
// Decompiled by Procyon v0.5.36
// 

package entities;

import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "conversation")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Conversation.findAll", query = "SELECT c FROM Conversation c"), @NamedQuery(name = "Conversation.findById", query = "SELECT c FROM Conversation c WHERE c.id = :id"), @NamedQuery(name = "Conversation.findByNom", query = "SELECT c FROM Conversation c WHERE c.nom = :nom"), @NamedQuery(name = "Conversation.findByDateCreation", query = "SELECT c FROM Conversation c WHERE c.dateCreation = :dateCreation") })
public class Conversation implements Serializable
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
    @Column(name = "date_creation")
    @Temporal(TemporalType.DATE)
    private Date dateCreation;
    @JoinTable(name = "conversation_user", joinColumns = { @JoinColumn(name = "conversation_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") })
    @ManyToMany
    private Collection<User> userCollection;
    @OneToMany(mappedBy = "conversationId")
    private Collection<Message> messageCollection;
    
    public Conversation() {
    }
    
    public Conversation(final Integer id) {
        this.id = id;
    }
    
    public Conversation(final Integer id, final String nom, final Date dateCreation) {
        this.id = id;
        this.nom = nom;
        this.dateCreation = dateCreation;
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
    
    public Date getDateCreation() {
        return this.dateCreation;
    }
    
    public void setDateCreation(final Date dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    @XmlTransient
    public Collection<User> getUserCollection() {
        return this.userCollection;
    }
    
    public void setUserCollection(final Collection<User> userCollection) {
        this.userCollection = userCollection;
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
        if (!(object instanceof Conversation)) {
            return false;
        }
        final Conversation other = (Conversation)object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }
    
    @Override
    public String toString() {
        return "entities.Conversation[ id=" + this.id + " ]";
    }
}
