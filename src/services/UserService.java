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
import utilitez.SHA;

/**
 *
 * @author Rafaa
 */
public class UserService {
    Connection cnx2;
    
       public UserService() {
        cnx2 = MyConnection.getInstance().getCnx();
    }
       
  public void ajouter(User user)
    {
        try {
            Statement st =cnx2.createStatement();
            String req = "insert into user (nom,email,password,sexe,pays) values("+user.getNom()+",'"+user.getEmail()+"','"+user.getPassword()+"','"+user.getSexe()+"','"+user.getPays()+"')";
            st.executeUpdate(req);
            PreparedStatement pt = cnx2.prepareStatement("select id from user ORDER BY id DESC LIMIT 0, 1");
            ResultSet rs = pt.executeQuery();
            
            
            while (rs.next()) {            
                user.setStatus("Offline");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      public void changepassword(String s1, String email) {
        try {
            String requete = "update user set password=? where email=?";
            PreparedStatement pst = cnx2.prepareStatement(requete);
            pst.setString(1, s1);
            pst.setString(2, email);
            pst.executeUpdate();
            System.out.println("Modification effectué avec succés");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
  
   public boolean connect(String mail,String mdp)
    {   boolean test= true;
        try {
           
            String stat = ("select * from user where user.email='"+mail+"' and user.password='"+mdp+"' ");
           
            PreparedStatement pt=cnx2.prepareStatement(stat);
            ResultSet rs = pt.executeQuery();  
            
            if (rs.next()) {
                editstatusOn(mail);
            test = true;
            }
            else 
            {
                System.out.println("verifier login ou mdp");
                test=false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
      return test;  
        
    }
   
     public List<User> afficher (String mail)
    {    List<User> arr = new ArrayList<>();
        try {
            String stat = ("select * from user  where email='"+mail+"' ");
            PreparedStatement pt=cnx2.prepareStatement(stat);
            ResultSet rs = pt.executeQuery();            
            while (rs.next()) 
            {
 
                User user =new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));

                arr.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
        
    }
     
       public void modifier(String nom,String email,String password,String sexe,String pays)
    { 
        try {
            
            PreparedStatement pt = cnx2.prepareStatement("update user set nom=? , password=?  ,sexe=? ,pays=?  where email=? ");
            
            pt.setString(1,nom);
            pt.setString(2,email);
            pt.setString(3,password);
            pt.setString(4,sexe);
            pt.setString(5,pays);
            pt.executeUpdate();
         } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
       
        public void editstatusOff (String mail)
      {
           String stat="Offline";
            try {
            PreparedStatement pt = cnx2.prepareStatement("update user set status = '"+stat+"'  where email='"+mail+"' ");
        
                 pt.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
       
              
      }
        
          public void editstatusOn (String mail)
      {
           String stat="Online";
            try {
            PreparedStatement pt = cnx2.prepareStatement("update user set status = '"+stat+"'  where email='"+mail+"' ");
        
                 pt.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
       
              
      }
       
        public void editname (String mail,String nom)
      {
           
            try {
                PreparedStatement pt = cnx2.prepareStatement("update user set lastname=? where email=?");
                pt.setString(1,nom);
                pt.setString(2,mail);
                pt.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
       
              
      }
        
        
          public void edituser (User user)
      {
           
            try {
                PreparedStatement pt = cnx2.prepareStatement("update user set id=?, nom=? , password=?  ,email=? ,sexe=? ,pays=?,status=?  where email=? ");
                 pt.setInt(1,user.getId());
                pt.setString(2,user.getNom());
                pt.setString(3,user.getPassword());
               
                pt.setString(4,user.getEmail());
                pt.setString(5,user.getSexe());
                pt.setString(6,user.getPays());
                pt.setString(7,user.getStatus());
               
                
                 pt.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
       
              
      }
}
