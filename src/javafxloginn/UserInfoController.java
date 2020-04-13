/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import entities.User;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author rafaa
 */
public class UserInfoController implements Initializable {
   
     User user;
  

    @FXML
    private Text txtEmail;

    @FXML
    private Text txtFullName;

    @FXML
    private Text txtCountry;

    @FXML
    private Text txtGender;

    Integer idu;

     void send(Integer id) {
         User user = new User();
        idu = id;
        System.out.println(idu);
        UserService us = new UserService();
        user =  us.findById(idu);
        txtFullName.setText(user.getNom());
        txtEmail.setText(user.getEmail());
       
        txtCountry.setText(user.getPays());
        txtGender.setText(user.getSexe());
       
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
