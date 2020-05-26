/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Conversation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilitez.MyConnection;
import utilitez.ServiceSysdate;

/**
 *
 * @author Rafaa
 */
public class ConversationService {
 
     Connection cnx2;
     ServiceSysdate ss = new ServiceSysdate();
    
       public ConversationService() {
        cnx2 = MyConnection.getInstance().getCnx();
    }
        public void ajouterConv (Conversation conv)
    {
        
        try {
            Statement st =cnx2.createStatement();
            String req="insert into conversation values("+conv.getNom()+"')";
            st.executeUpdate(req);
            PreparedStatement pt = cnx2.prepareStatement("select id from conversation ORDER BY id DESC LIMIT 0, 1");
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {            
                conv.setDateCreation(ss.selectDate());
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConversationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        public int searchBynom(String nom) {
         int x = 9999;
     
          String req = "select id from conversation where nom='" + nom + "'";
          PreparedStatement preparedStatement;
        try {
            preparedStatement = cnx2.prepareStatement(req);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                x = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return x;
    }
         public List<Conversation> getAllConv(Integer r) {
        List<Conversation> conv = new ArrayList<>();
        String req = " SELECT * FROM `conversation` WHERE conversation.id IN(SELECT `conversation_id` FROM conversation_user WHERE `user_id`= '"+r+"' ) ";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = cnx2.prepareStatement(req);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Conversation c = new Conversation(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getDate("date_creation")
                        );
                conv.add(c);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conv;
    }

    
}
