// 
// Decompiled by Procyon v0.5.36
// 

package services;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import entities.User;
import utilitez.MyConnection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

public class UserService
{
    Connection cnx2;
    String sexe;
    String o;
    private Statement ste;
    private ResultSet rs;
    
    public UserService() {
        this.cnx2 = MyConnection.getInstance().getCnx();
    }
    
    public void ajouter(final User user) {
        try {
            final Statement st = this.cnx2.createStatement();
            final String req = "insert into user (nom,email,password,sexe,pays) values(" + user.getNom() + ",'" + user.getEmail() + "','" + user.getPassword() + "','" + user.getSexe() + "','" + user.getPays() + "')";
            st.executeUpdate(req);
            final PreparedStatement pt = this.cnx2.prepareStatement("select id from user ORDER BY id DESC LIMIT 0, 1");
            final ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                user.setStatus("Offline");
                user.setEnabled(null);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getNombyId(final int id) throws SQLException {
        final PreparedStatement pt = this.cnx2.prepareStatement("select nom from user where id =" + id);
        final ResultSet rs = pt.executeQuery();
        return rs.toString();
    }
    
    public static List<String> getBymail(final String r) {
        final List<String> list = new ArrayList<String>();
        try {
            final String req = "select nom,password,sexe,pays from user where email ='" + r + "' ";
            final Statement ste = MyConnection.getInstance().getCnx().createStatement();
            final ResultSet result = ste.executeQuery(req);
            while (result.next()) {
                list.add(result.getString(1));
                list.add(result.getString(2));
                list.add(result.getString(3));
                list.add(result.getString(4));
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    public boolean searchByEmail(final String mail) {
        try {
            final Statement ste = MyConnection.getInstance().getCnx().createStatement();
            final String req = "select * from user where email='" + mail + "'";
            final ResultSet rs = ste.executeQuery(req);
            if (rs.next()) {
                return true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void changepassword(final String s1, final String email) {
        try {
            final String requete = "update user set password=? where email=?";
            final PreparedStatement pst = this.cnx2.prepareStatement(requete);
            pst.setString(1, s1);
            pst.setString(2, email);
            pst.executeUpdate();
            System.out.println("Modification effectu\u00e9 avec succ\u00e9s");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static boolean mailUnique(final String mail) {
        int i = 0;
        try {
            final Statement ste = MyConnection.getInstance().getCnx().createStatement();
            final String req = "Select * from user WHERE email='" + mail + "'";
            final ResultSet result = ste.executeQuery(req);
            while (result.next()) {
                ++i;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i == 0;
    }
    
    public boolean connect(final String mail, final String mdp) {
        boolean test = true;
        try {
            final String stat = "select * from user where user.email='" + mail + "' and user.password='" + mdp + "' ";
            final PreparedStatement pt = this.cnx2.prepareStatement(stat);
            final ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                this.editstatusOn(mail);
                test = true;
            }
            else {
                System.out.println("verifier login ou mdp");
                test = false;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return test;
    }
    
    public List<User> afficher(final String mail) {
        final List<User> arr = new ArrayList<User>();
        try {
            final String stat = "select * from user  where email='" + mail + "' ";
            final PreparedStatement pt = this.cnx2.prepareStatement(stat);
            final ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                final User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8));
                arr.add(user);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }
    
    public User readByUsername(final String email) {
        final String req = "SELECT *  FROM user WHERE email = '" + email + "'";
        User user1 = new User();
        try {
            this.ste = this.cnx2.createStatement();
            this.rs = this.ste.executeQuery(req);
            if (this.rs.next()) {
                user1 = new User(this.rs.getInt("id"), this.rs.getString("nom"), this.rs.getString("email"));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user1;
    }
    
    public void modifier(final String nom, final String email, final String sexe, final String pays, final String password) {
        try {
            final PreparedStatement pt = this.cnx2.prepareStatement("update user set nom=? ,sexe=? ,pays=?  where password=? ");
            pt.setString(1, nom);
            pt.setString(2, email);
            pt.setString(3, sexe);
            pt.setString(4, pays);
            pt.setString(5, password);
            pt.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void editstatusOff(final String mail) {
        final String stat = "Offline";
        try {
            final PreparedStatement pt = this.cnx2.prepareStatement("update user set status = '" + stat + "'  where email='" + mail + "' ");
            pt.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void editstatusOn(final String mail) {
        final String stat = "Online";
        try {
            final PreparedStatement pt = this.cnx2.prepareStatement("update user set status = '" + stat + "'  where email='" + mail + "' ");
            pt.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public User findBymail(final String mail) {
        User user = null;
        try {
            final String req = "select * from user where email = '" + mail + "' ";
            final PreparedStatement preparedStatement = this.cnx2.prepareStatement(req);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Integer id = resultSet.getInt("id");
                final String nom = resultSet.getString("nom");
                final String sexe = resultSet.getString("sexe");
                user = new User(id, nom, sexe);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }
    
    public User findById(final Integer id) {
        User user = null;
        try {
            final String req = "select * from user where id = '" + id + "' ";
            final PreparedStatement preparedStatement = this.cnx2.prepareStatement(req);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final String nm = resultSet.getString("nom");
                final String mail = resultSet.getString("email");
                final String sex = resultSet.getString(4);
                final String pay = resultSet.getString(5);
                final String stat = resultSet.getString(6);
                user = new User(nm, mail, sex, pay, stat);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }
    
    public void editname(final String mail, final String nom) {
        try {
            final PreparedStatement pt = this.cnx2.prepareStatement("update user set lastname=? where email=?");
            pt.setString(1, nom);
            pt.setString(2, mail);
            pt.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void edituser(final User user) {
        try {
            final PreparedStatement pt = this.cnx2.prepareStatement("update user set id=?, nom=? , password=?  ,email=? ,sexe=? ,pays=?,status=?  where email=? ");
            pt.setInt(1, user.getId());
            pt.setString(2, user.getNom());
            pt.setString(3, user.getPassword());
            pt.setString(4, user.getEmail());
            pt.setString(5, user.getSexe());
            pt.setString(6, user.getPays());
            pt.setString(7, user.getStatus());
            pt.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void block(final int id) {
        try {
            final String requete = "update user set enabled = 0 where id = " + id;
            final PreparedStatement pst = this.cnx2.prepareStatement(requete);
            pst.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public int ckeckenabled(final String s) {
        int enabled = 99;
        final String req = "select enabled from user where email =?";
        try {
            final PreparedStatement preparedStatement = this.cnx2.prepareStatement(req);
            preparedStatement.setString(1, s);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                enabled = resultSet.getInt(1);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return enabled;
    }
    
    public String ckecksexe(final String s) {
        this.sexe = "";
        final String req = "select sexe from user where email =?";
        try {
            final PreparedStatement preparedStatement = this.cnx2.prepareStatement(req);
            preparedStatement.setString(1, s);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                this.sexe = resultSet.getString(1);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return this.sexe;
    }
    
    public List<User> getAllUsers() {
        final String o = "Online";
        final List<User> users = new ArrayList<User>();
        try {
            final String req = "select * from user where status ='" + o + "'";
            final PreparedStatement pst = this.cnx2.prepareStatement(req);
            final ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                final Integer a = rs.getInt("id");
                final String b = rs.getString("nom");
                final String c = rs.getString("sexe");
                final User user = new User(a, b, c);
                users.add(user);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }
}
