// 
// Decompiled by Procyon v0.5.36
// 

package services;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import entities.Conversation;
import utilitez.MyConnection;
import java.sql.Connection;

public class ConversationService
{
    Connection cnx2;
    
    public ConversationService() {
        this.cnx2 = MyConnection.getInstance().getCnx();
    }
    
    public void ajouterConv(final Conversation conv) {
        try {
            final Statement st = this.cnx2.createStatement();
            final String req = "insert into conversation values(" + conv.getNom() + "','" + conv.getUserCollection() + "')";
            st.executeUpdate(req);
            final PreparedStatement pt = this.cnx2.prepareStatement("select id from conversation ORDER BY id DESC LIMIT 0, 1");
            final ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                conv.setId(rs.getInt(1));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ConversationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Conversation> getAllConv(final Integer r) {
        final List<Conversation> conv = new ArrayList<Conversation>();
        final String req = " SELECT * FROM `conversation` WHERE conversation.id IN(SELECT `conversation_id` FROM conversation_user WHERE `user_id`= '" + r + "' ) ";
        try {
            final PreparedStatement preparedStatement = this.cnx2.prepareStatement(req);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Conversation c = new Conversation(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getDate("date_creation"));
                conv.add(c);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conv;
    }
}
