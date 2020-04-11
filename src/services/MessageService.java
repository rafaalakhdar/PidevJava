/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Conversation;
import entities.Message;
import entities.User;
import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import utilitez.Copy;
import utilitez.MyConnection;

/**
 *
 * @author Rafaa
 */
public class MessageService {
     Connection cnx2;

    public MessageService() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    public void ajoutermsg(Message m) {
        try {
            int id = m.getIdM();
            
            String message = m.getMessage();
            String image = m.getImage();
            java.util.Date creationDate = m.getCreatedAt();
            User user = m.getUserId();
            Conversation conv = m.getConversationId();
            
            
            String req = "INSERT INTO message (conversation_id,idM,message,image,created_at,user_id)  VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = cnx2.prepareStatement(req);
            pst.setInt(2, id);
            
            
            pst.setString(3, message);
            pst.setDate(5, new java.sql.Date(creationDate.getTime()));
      // ajout image, avec un id unique    
            UUID u = UUID.randomUUID();
            String old = image;
            String extension = image.substring(image.lastIndexOf("."));
           image = image.substring(image.lastIndexOf("\\")+1,image.lastIndexOf("."));
           image = image + u.toString() + extension;
          // fin ajout image
            pst.setString(4, image);
            pst.setInt(6, user.getId());
            pst.setInt(1, conv.getId());
            System.out.println(pst.toString());
            pst.executeUpdate();
          //deplacement vers le dossier du serveur web
            File source = new File(old);
            File dest = new File("C:\\wamp64\\www\\pi\\pi\\web\\conversation\\images\\"+image);
         
        Copy.copyFileUsingStream(source,dest);
        //fin deplacement
            System.out.println("ajout msg good");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
}
    
     public List<Message> getmsg(Integer r) {
         //User user = new User();
        List<Message> msgs = new ArrayList<>();
 String req = "SELECT `message`,`updated_at`,`user_id` FROM `message` WHERE `conversation_id` in (SELECT user_id FROM conversation_user WHERE conversation_id ='"+r+"')";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = cnx2.prepareStatement(req);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message m;
                Map<String, Class<?>> User = null;
                m = new Message(
                        resultSet.getString("message"),
                        resultSet.getDate("updated_at"), 
                        (User) resultSet.getObject("user_id",User));
                msgs.add(m);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return msgs;
    } 
    
}