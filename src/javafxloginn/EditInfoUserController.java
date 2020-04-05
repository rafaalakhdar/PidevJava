/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;

import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Rafaa
 */
public class EditInfoUserController implements Initializable {
    
    @FXML
    private Label lblmail;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtEmail, txtSexe, txtPays;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button back;
    @FXML
    private Button update;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       // txtEmail.setText(lblmail.getText());
    }
    
    public void updateuser(){
         
    
    }
    
      void send(String email) {
        lblmail.setText(email);
        
    }

    /**
     *
     * @param event
     */
    @FXML
    public void btnBackAction(ActionEvent event) {
        try {

            Stage stage = new Stage();
            Parent parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(parent);
            stage.setTitle("Tunisain Got Talent");

            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean validateName() {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(txtUserName.getText());
        if (m.find() && m.group().equals(txtUserName.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur Name");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid First Name");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validateEmaill() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(txtEmail.getText());
        if (m.find() && m.group().equals(txtEmail.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur Email");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Email");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validatePassword() {
        Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{5,15})");
        Matcher m = p.matcher(txtPassword.getText());
        if (m.matches()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur Password");
            alert.setHeaderText(null);
            alert.setContentText("Password must contain at least one(Digit, Lowercase, UpperCase and Special Character) and length must be between 5 -15");
            alert.showAndWait();

            return false;
        }
    }

    public void clearFields() {
        txtEmail.clear();
        txtPassword.clear();
        txtUserName.clear();

        txtSexe.clear();
        txtPays.clear();

    }

    private boolean validateFields() {
        if (txtPassword.getText().isEmpty() || txtEmail.getText().isEmpty() || txtUserName.getText().isEmpty() || txtSexe.getText().isEmpty() || txtPays.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur Fields");
            alert.setHeaderText(null);
            alert.setContentText(" Fields empty");
            alert.showAndWait();

            return false;
        }

        return true;
    }

     
    
}
