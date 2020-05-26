/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilitez.MyConnection;
import org.mindrot.jbcrypt.BCrypt;
import utilitez.ServiceSysdate;

/**
 *
 * @author Rafaa
 */
public class LoginService {

    Connection cn = MyConnection.getInstance().getCnx();
    ServiceSysdate ss = new ServiceSysdate();

    //MyConnection cnx = MyConnection.getInstance();

    public void addUser(User user) throws IOException {
        try {
            String pw_hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            
            String requete
                    = "INSERT INTO fos_user (username, username_canonical, email, email_canonical, enabled, password,last_login, roles,sexe,pays,status) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = cn.prepareStatement(requete);
            st.setString(1, user.getUsername());
            st.setString(2, user.getUsername().toLowerCase());
            st.setString(3, user.getEmail());
            st.setString(4, user.getEmail().toLowerCase());
            st.setInt(5, 1);
            st.setString(6, pw_hash);
            //date login
            st.setDate(7, ss.selectDate());

            st.setString(8, "a:0:{}");
            st.setString(9, user.getSexe());
            st.setString(10, user.getPays());
            st.setString(11, user.getStatus());

            st.executeUpdate();
            System.out.println("User ajoutée");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());

        }

    }

    public User searchUserByEmail(String pseudo, String password) throws SQLException {
        User user = null;

        String req = "SELECT (password) FROM fos_user where (username_canonical=? OR email_canonical=?)";
        PreparedStatement st1 = cn.prepareStatement(req);
        st1.setString(1, pseudo.toLowerCase());
        st1.setString(2, pseudo.toLowerCase());
        ResultSet rs1 = st1.executeQuery();
        while (rs1.next()) {
            if (BCrypt.checkpw(password, "$2a$" + rs1.getString("password").substring(4, rs1.getString("password").length()))) {
                String requete = "SELECT * FROM fos_user where (username_canonical=? OR email_canonical=?)";
                PreparedStatement st = cn.prepareStatement(requete);
                st.setString(1, pseudo.toLowerCase());
                st.setString(2, pseudo.toLowerCase());

                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setUsername_canonical(rs.getString("username_canonical"));
                    user.setEmail(rs.getString("email"));
                    user.setEmail_canonical(rs.getString("email_canonical"));
                    user.setPassword(rs.getString("password"));
                    user.setSexe(rs.getString("sexe"));
                    //user.setRoles("a:0:{}");

                    user.setPays(rs.getString("pays"));
                    user.setStatus(rs.getString("status"));
                    user.setLast_login(rs.getDate("last_login"));
                    editstatusOn(rs.getString("email"));
                    System.out.println("User found");

                }
            } else {
                System.out.println("verifier login ou mdp");
            }
        }

        return user;
    }

    public String Gettype(String s) {
        String s1 = "";
        String req = "select roles from fos_user where (username_canonical=? OR email_canonical=?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = cn.prepareStatement(req);
            preparedStatement.setString(1, s);
            preparedStatement.setString(2, s);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                s1 = resultSet.getString("roles");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return s1;
    }

    public void editstatusOn(String mail) {
        String stat = "Online";
        try {
            PreparedStatement pt = cn.prepareStatement("update fos_user set status = '" + stat + "'  where email='" + mail + "' ");

            pt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setLastdatelogin(String mail) {
       
        try {
            String requet = "update fos_user set last_login = '" + ss.selectDate() + "' where email_canonical='" + mail + "' ";
            PreparedStatement st2 = cn.prepareStatement(requet);

            st2.executeUpdate();
            System.out.println("date Log in " + ss.selectDate());
        } catch (SQLException ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
