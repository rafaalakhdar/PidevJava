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

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilitez.MyConnection;

/**
 *
 * @author Rafaa
 */
public class UserService {

    Connection cnx2;
    String sexe;
    String o;

    public UserService() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    public static List<String> getBymail(String r) {
        List<String> list = new ArrayList<>();
        try {
            String req = "select username,password,sexe,pays from fos_user where email ='" + r + "' ";

            Statement ste = MyConnection.getInstance().getCnx().createStatement();

            ResultSet result = ste.executeQuery(req);
            while (result.next()) {

                list.add(result.getString(1));
                list.add(result.getString(2));
                list.add(result.getString(3));
                list.add(result.getString(4));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public boolean searchByEmail(String mail) {

        try {
            Statement ste = MyConnection.getInstance().getCnx().createStatement();
            String req = "select * from fos_user where email='" + mail + "'";
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public void changepassword(String s1, String email) {
        try {
            String requete = "update fos_user set password=? where email=?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setString(1, s1);
            pst.setString(2, email);
            pst.executeUpdate();
            System.out.println("Modification effectué avec succés");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static boolean mailUnique(String mail) {

        int i = 0;
        try {
            Statement ste = MyConnection.getInstance().getCnx().createStatement();
            String req = "Select * from fos_user WHERE email='" + mail + "'";
            ResultSet result = ste.executeQuery(req);

            while (result.next()) {
                // int id = result.getInt(1); //int id = result.getInt("id");
                i++;

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i == 0;

    }

    public boolean nomUnique(String nom) {

        try {
            Statement ste = MyConnection.getInstance().getCnx().createStatement();
            String req = "Select * from fos_user WHERE username='" + nom + "'";
            ResultSet result = ste.executeQuery(req);

            while (result.next()) {

                return true;

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public void editstatusOff(String mail) {
        String stat = "Offline";
        try {
            PreparedStatement pt = cnx2.prepareStatement("update fos_user set status = '" + stat + "'  where (username_canonical='" + mail + "' OR email_canonical='" + mail + "')");

            pt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public User findBymail(String mail) {
        User user = null;
        try {
            String req = "select * from fos_user where email = '" + mail + "' ";
            PreparedStatement preparedStatement;

            preparedStatement = cnx2.prepareStatement(req);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Integer id = resultSet.getInt("id");
                String nom = resultSet.getString("username");
                String sexe = resultSet.getString("sexe");
                user = new User(id, nom, sexe);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public User findById(Integer id) {
        User user = null;
        try {
            String req = "select * from fos_user where id = '" + id + "' ";
            PreparedStatement preparedStatement;

            preparedStatement = cnx2.prepareStatement(req);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                String nm = resultSet.getString("username");
                String mail = resultSet.getString("email");
                String sex = resultSet.getString(13);
                String pay = resultSet.getString(14);
                String stat = resultSet.getString(15);

                user = new User(nm, mail, sex, pay, stat);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public void block(int id) {
        try {
            String requete = "update fos_user set enabled = 0 where id = " + id;
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int ckeckenabled(String s) {
        int enabled = 99;
        String req = "select enabled from fos_user where (username_canonical=? OR email_canonical=?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = cnx2.prepareStatement(req);
            preparedStatement.setString(1, s.toLowerCase());
            preparedStatement.setString(2, s.toLowerCase());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                enabled = resultSet.getInt(1);
                //   enabled = resultSet.getInt(2);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return enabled;
    }

    public int searchBynomuser(String nom) {
        int u = 9999;

        String req = "select id from fos_user where username='" + nom + "'";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = cnx2.prepareStatement(req);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                u = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return u;
    }

    public String searchByid(int id) {
        String u = "";

        String req = "select username from fos_user where id='" + id + "'";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = cnx2.prepareStatement(req);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                u = resultSet.getString("username");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return u;
    }

    public String ckecksexe(String s) {
        sexe = "";
        String req = "select sexe from fos_user where (username_canonical=? OR email_canonical=?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = cnx2.prepareStatement(req);
            preparedStatement.setString(1, s);
            preparedStatement.setString(2, s);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sexe = resultSet.getString(1);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return sexe;

    }

    public List<User> getAllUsers() {
        String o = "Online";
        List<User> users = new ArrayList<>();
        PreparedStatement pst;
        try {

            String req = "select * from fos_user where status ='" + o + "'";

            pst = cnx2.prepareStatement(req);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                Integer a = rs.getInt("id");
                String b = rs.getString("username");
                String c = rs.getString("sexe");
                User user = new User(a, b, c);
                users.add(user);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return users;
    }

}
