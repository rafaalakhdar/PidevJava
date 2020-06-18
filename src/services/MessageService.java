// 
// Decompiled by Procyon v0.5.36
// 

package services;

import java.util.Map;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import entities.Conversation;
import entities.User;
import java.sql.SQLException;
import utilitez.Copy;
import java.io.File;
import java.util.UUID;
import java.sql.Date;
import entities.Message;
import utilitez.MyConnection;
import java.sql.Connection;

public class MessageService
{
    Connection cnx2;
    
    public MessageService() {
        this.cnx2 = MyConnection.getInstance().getCnx();
    }
    
    public void ajoutermsg(final Message m) {
        try {
            final int id = m.getIdM();
            final String message = m.getMessage();
            String image = m.getImage();
            final java.util.Date creationDate = m.getCreatedAt();
            final User user = m.getUserId();
            final Conversation conv = m.getConversationId();
            final String req = "INSERT INTO message (conversation_id,idM,message,image,created_at,user_id)  VALUES (?,?,?,?,?,?)";
            final PreparedStatement pst = this.cnx2.prepareStatement(req);
            pst.setInt(2, id);
            pst.setString(3, message);
            pst.setDate(5, new Date(creationDate.getTime()));
            final UUID u = UUID.randomUUID();
            final String old = image;
            final String extension = image.substring(image.lastIndexOf("."));
            image = image.substring(image.lastIndexOf("\\") + 1, image.lastIndexOf("."));
            image = image + u.toString() + extension;
            pst.setString(4, image);
            pst.setInt(6, user.getId());
            pst.setInt(1, conv.getId());
            System.out.println(pst.toString());
            pst.executeUpdate();
            final File source = new File(old);
            final File dest = new File("C:\\wamp64\\www\\pi\\pi\\web\\conversation\\images\\" + image);
            Copy.copyFileUsingStream(source, dest);
            System.out.println("ajout msg good");
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public List<Message> getmsg(final Integer r) {
        final List<Message> msgs = new ArrayList<Message>();
        final String req = "SELECT `message`,`updated_at`,`user_id` FROM `message` WHERE `conversation_id` in (SELECT user_id FROM conversation_user WHERE conversation_id ='" + r + "')";
        try {
            final PreparedStatement preparedStatement = this.cnx2.prepareStatement(req);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Map<String, Class<?>> User = null;
                final Message m = new Message(resultSet.getString("message"), resultSet.getDate("updated_at"), (User)resultSet.getObject("user_id", User));
                msgs.add(m);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return msgs;
    }
}
