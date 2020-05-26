/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;

import com.jfoenix.controls.JFXPasswordField;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceNotification;
import utilitez.Mailing;

/**
 * FXML Controller class
 *
 * @author Rafaa
 */
public class Conversation2 implements Initializable {

    User logeduser = new User();

    String sender = "";
    @FXML
    private TextField txtFieldSubject;
    @FXML
    private TextArea txtAreaEmail;
    @FXML
    private JFXPasswordField txtpass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        logeduser = JavaFXloginn.user;
        sender = logeduser.getEmail();
    }

    public void envoi(ActionEvent event) {
        String to = "rafaa.lkh@gmail.com";
        String subject = "User Reclamation about " + txtFieldSubject.getText();
        String message = "Chez Admin\n" + txtAreaEmail.getText();
        String usermail = sender;
        String passmail = txtpass.getText();
        Mailing.send(to, subject, message, usermail, passmail);
        Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
        alertSuccess.setTitle("Success");
        alertSuccess.setHeaderText("Reclamation envoyer");
        alertSuccess.setContentText("Mail envoyer a Admin TGT");
        alertSuccess.showAndWait();

        try {

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menubar.fxml"));

            Parent root = loader.load();

            stage.setTitle("Tunisain Got Talent");
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
     @FXML
    public void btnBackAction(ActionEvent event) {
        try {

            Stage stage = new Stage();
            Parent parent = FXMLLoader.load(getClass().getResource("conversation.fxml"));
            Scene scene = new Scene(parent);
            stage.setTitle("Chat Box");

            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
