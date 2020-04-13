/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rafaa
 */
@Entity
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m")
    , @NamedQuery(name = "Message.findByIdM", query = "SELECT m FROM Message m WHERE m.idM = :idM")
    , @NamedQuery(name = "Message.findByImage", query = "SELECT m FROM Message m WHERE m.image = :image")
    , @NamedQuery(name = "Message.findByCreatedAt", query = "SELECT m FROM Message m WHERE m.createdAt = :createdAt")})
public class Message implements Serializable {

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
    private Integer usermsg;
    private Date dtmsg;
    private Integer cvid;

    public Message() {
    }

    public Message(Integer idM) {
        this.idM = idM;
    }

    public Message(Integer idM, String message, Date createdAt) {
        this.idM = idM;
        this.message = message;
        this.createdAt = createdAt;
    }

    public Message(String message, Date createdAt, User userId) {
        this.message = message;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public Message(String message, Integer usermsg, Date dtmsg) {
        this.message = message;
        this.usermsg = usermsg;
        this.dtmsg = dtmsg;
    }
     public Message(String message, Date dtmsg, Integer cvid, Integer usermsg) {
        this.message = message;
       
        this.dtmsg = dtmsg;
        this.cvid = cvid;
        this.usermsg = usermsg;
    }
    

  

    
    public Message(Integer idM, String message) {
        this.idM = idM;
        this.message = message;
    }

    public Message(Integer idM, String message, String image, Date createdAt, Conversation conversationId, User userId) {
        this.idM = idM;
        this.message = message;
        this.image = image;
        this.createdAt = createdAt;
        this.conversationId = conversationId;
        this.userId = userId;
    }

    public Message(String message, String image, Date createdAt, Conversation conversationId, User userId) {
        this.message = message;
        this.image = image;
        this.createdAt = createdAt;
        this.conversationId = conversationId;
        this.userId = userId;
    }
    

    public Message(Integer idM, String message, String image, Date createdAt) {
        this.idM = idM;
        this.message = message;
        this.image = image;
        this.createdAt = createdAt;
    }

    public Message(Integer idM, String message, String image, Date createdAt, User userId) {
        this.idM = idM;
        this.message = message;
        this.image = image;
        this.createdAt = createdAt;
        this.userId = userId;
    }
    
    

    public Integer getIdM() {
        return idM;
    }

    public void setIdM(Integer idM) {
        this.idM = idM;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCvid() {
        return cvid;
    }

    public void setCvid(Integer cvid) {
        this.cvid = cvid;
    }

    public Conversation getConversationId() {
        return conversationId;
    }

    public void setConversationId(Conversation conversationId) {
        this.conversationId = conversationId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Integer getUsermsg() {
        return usermsg;
    }

    public void setUsermsg(Integer usermsg) {
        this.usermsg = usermsg;
    }

    public Date getDtmsg() {
        return dtmsg;
    }

    public void setDtmsg(Date dtmsg) {
        this.dtmsg = dtmsg;
    }

   
   

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idM != null ? idM.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.idM == null && other.idM != null) || (this.idM != null && !this.idM.equals(other.idM))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Message[ idM=" + idM + " ]";
    }
    
}
