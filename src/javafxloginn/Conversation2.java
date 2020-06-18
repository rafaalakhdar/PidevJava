// 
// Decompiled by Procyon v0.5.36
// 

package javafxloginn;

import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import utilitez.Mailing;
import javafx.event.ActionEvent;
import java.util.ResourceBundle;
import java.net.URL;
import com.jfoenix.controls.JFXPasswordField;
import javafx.scene.control.TextArea;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;

public class Conversation2 implements Initializable
{
    String sender;
    @FXML
    private TextField txtFieldSubject;
    @FXML
    private TextArea txtAreaEmail;
    @FXML
    private JFXPasswordField txtpass;
    
    public Conversation2() {
        this.sender = "";
    }
    
    public void initialize(final URL url, final ResourceBundle rb) {
    }
    
    void setdata(final String mail) {
        this.sender = mail;
    }
    
    public void envoi(final ActionEvent event) {
        final String to = "rafaa.lkh@gmail.com";
        final String subject = "User Reclamation about " + this.txtFieldSubject.getText();
        final String message = "Chez Admin\n" + this.txtAreaEmail.getText();
        final String usermail = this.sender;
        final String passmail = this.txtpass.getText();
        Mailing.send(to, subject, message, usermail, passmail);
        final Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
        alertSuccess.setTitle("Success");
        alertSuccess.setHeaderText("Reclamation envoyer");
        alertSuccess.setContentText("Mail envoyer a Admin TGT");
        alertSuccess.showAndWait();
        try {
            final Stage stage = new Stage();
            final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("menubar.fxml"));
            final Parent root = (Parent)loader.load();
            stage.setTitle("Tunisain Got Talent");
            final Scene scene = new Scene(root);
            final MenubarController mn = (MenubarController)loader.getController();
            mn.setMail(this.sender);
            stage.setScene(scene);
            stage.show();
            ((Node)event.getSource()).getScene().getWindow().hide();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
