// 
// Decompiled by Procyon v0.5.36
// 

package entities;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;
import java.util.Date;
import javax.persistence.Lob;
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
@Table(name = "message")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"), @NamedQuery(name = "Message.findByIdM", query = "SELECT m FROM Message m WHERE m.idM = :idM"), @NamedQuery(name = "Message.findByImage", query = "SELECT m FROM Message m WHERE m.image = :image"), @NamedQuery(name = "Message.findByCreatedAt", query = "SELECT m FROM Message m WHERE m.createdAt = :createdAt") })
public class Message implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idM")
    private Integer idM;
    @Basic(optional = false)
    @Lob
    @Column(name = "message")
    private String message;
    @Column(name = "image")
    private String image;
    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    @ManyToOne
    private Conversation conversationId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;
    
    public Message() {
    }
    
    public Message(final Integer idM) {
        this.idM = idM;
    }
    
    public Message(final String message, final Date createdAt, final User userId) {
        this.message = message;
        this.createdAt = createdAt;
        this.userId = userId;
    }
    
    public Message(final Integer idM, final String message) {
        this.idM = idM;
        this.message = message;
    }
    
    public Message(final Integer idM, final String message, final String image, final Date createdAt, final Conversation conversationId, final User userId) {
        this.idM = idM;
        this.message = message;
        this.image = image;
        this.createdAt = createdAt;
        this.conversationId = conversationId;
        this.userId = userId;
    }
    
    public Message(final String message, final String image, final Date createdAt, final Conversation conversationId, final User userId) {
        this.message = message;
        this.image = image;
        this.createdAt = createdAt;
        this.conversationId = conversationId;
        this.userId = userId;
    }
    
    public Message(final Integer idM, final String message, final String image, final Date createdAt) {
        this.idM = idM;
        this.message = message;
        this.image = image;
        this.createdAt = createdAt;
    }
    
    public Message(final Integer idM, final String message, final String image, final Date createdAt, final User userId) {
        this.idM = idM;
        this.message = message;
        this.image = image;
        this.createdAt = createdAt;
        this.userId = userId;
    }
    
    public Integer getIdM() {
        return this.idM;
    }
    
    public void setIdM(final Integer idM) {
        this.idM = idM;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public String getImage() {
        return this.image;
    }
    
    public void setImage(final String image) {
        this.image = image;
    }
    
    public Date getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public Conversation getConversationId() {
        return this.conversationId;
    }
    
    public void setConversationId(final Conversation conversationId) {
        this.conversationId = conversationId;
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
        hash += ((this.idM != null) ? this.idM.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof Message)) {
            return false;
        }
        final Message other = (Message)object;
        return (this.idM != null || other.idM == null) && (this.idM == null || this.idM.equals(other.idM));
    }
    
    @Override
    public String toString() {
        return "entities.Message[ idM=" + this.idM + " ]";
    }
}
