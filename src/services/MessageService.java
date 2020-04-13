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
            
            
            String message = m.getMessage();
            //String image = m.getImage();
            java.util.Date creationDate;
            creationDate = m.getDtmsg();
            Integer user = m.getUsermsg();
            Integer conv = m.getCvid();
            
            
            String req = "INSERT INTO message (conversation_id,message,created_at,user_id)  VALUES (?,?,?,?)";
            PreparedStatement pst = cnx2.prepareStatement(req);
            
            
            
            pst.setString(2, message);
            pst.setDate(3, new java.sql.Date(creationDate.getTime()));
      /* ajout image, avec un id unique    
            UUID u = UUID.randomUUID();
           String old = image;
            String extension = image.substring(image.lastIndexOf("."));
           image = image.substring(image.lastIndexOf("\\")+1,image.lastIndexOf("."));
           image = image + u.toString() + extension;
          // fin ajout image
            pst.setString(4, image);*/
            pst.setInt(4, user);
            pst.setInt(1, conv);
            System.out.println(pst.toString());
            pst.executeUpdate();
          //deplacement vers le dossier du serveur web
           /* File source = new File(old);
            File dest = new File("C:\\wamp64\\www\\pi\\pi\\web\\conversation\\images\\"+image);
         
        Copy.copyFileUsingStream(source,dest);*/
        //fin deplacement
            System.out.println("ajout msg good");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
}
    
     public List<Message> getallmsg(Integer c,Integer u) {
         //User user = new User();
        List<Message> msgs = new ArrayList<>();
 String req = "SELECT m.message,m.user_id,m.created_at FROM message m,conversation c, user u,conversation_user cu WHERE u.id=cu.user_id AND cu.conversation_id=c.id and m.conversation_id=c.id and c.id='"+c+"' and u.id='"+u+"'";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = cnx2.prepareStatement(req);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message m;
                
                m = new Message(
                        resultSet.getString("message"),
                        resultSet.getInt("user_id"),
                        resultSet.getDate("created_at")
                        );
                msgs.add(m);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return msgs;
    } 
    
  
     
     /*
     
        public synchronized void insertMessage(Message message) {
        try {
            getConnection();
            query = "insert into Message(fontSize,`from`,`to`,date,fontColor,fontFamily,fontStyle,body,fontWeight,underLine)values (" + message.getFontsSize() + ",'" + message.getFrom() + "','"
                    + message.getTo() + "','" + message.getDate() + "','" + message.getFontColor() + "','" + message.getFontFamily() + "','"
                    + message.getFontStyle() + "','" + message.getBody() + "','" + message.getFontWeight() + "','" + message.getUnderline().toString() + "')";

            statement = connection.createStatement();
            statement.executeUpdate(query);
            closeResources();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
     */
}