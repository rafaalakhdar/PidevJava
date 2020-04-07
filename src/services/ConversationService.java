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
import java.util.logging.Level;
import java.util.logging.Logger;
import utilitez.MyConnection;

/**
 *
 * @author Rafaa
 */
public class ConversationService {
 
     Connection cnx2;
    
       public ConversationService() {
        cnx2 = MyConnection.getInstance().getCnx();
    }
        public void ajouterConv (Conversation conv)
    {
        try {
            Statement st =cnx2.createStatement();
            String req="insert into conversation values("+conv.getNom()+"','"+conv.getUserCollection()+"')";
            st.executeUpdate(req);
            PreparedStatement pt = cnx2.prepareStatement("select id from conversation ORDER BY id DESC LIMIT 0, 1");
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {            
                conv.setId(rs.getInt(1));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConversationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
