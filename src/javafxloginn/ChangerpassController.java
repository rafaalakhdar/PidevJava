// 
// Decompiled by Procyon v0.5.36
// 

package javafxloginn;

import javafx.scene.input.KeyEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import utilitez.SHA;
import services.UserService;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;
import tray.notification.NotificationType;
import javafx.event.ActionEvent;
import java.io.IOException;
import com.nexmo.client.NexmoClientException;
import java.util.Iterator;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.SmsSubmissionResponseMessage;
import com.nexmo.client.sms.messages.Message;
import com.nexmo.client.sms.messages.TextMessage;
import com.nexmo.client.NexmoClient;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.Random;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;

public class ChangerpassController implements Initializable
{
    @FXML
    private Button btnverif;
    @FXML
    private Button btnsuiv;
    @FXML
    private Button btnterminer;
    @FXML
    private TextField tell;
    @FXML
    private TextField cde;
    @FXML
    private TextField mail;
    @FXML
    private PasswordField nvpass2;
    @FXML
    private PasswordField nvpass;
    Random rand;
    String code;
    
    public ChangerpassController() {
        this.rand = new Random();
        this.code = String.valueOf(this.rand.nextInt(9000) + 1000);
    }
    
    public void initialize(final URL url, final ResourceBundle rb) {
        this.cde.setVisible(false);
        this.nvpass.setVisible(false);
        this.nvpass2.setVisible(false);
        this.btnterminer.setVisible(false);
        this.btnverif.setVisible(false);
    }
    
    public void sendsms() throws NexmoClientException, IOException {
        final String tel = "+216" + this.tell.getText();
        System.out.println(tel);
        System.out.println(this.code);
        final NexmoClient client = new NexmoClient.Builder().apiKey("35d2b9e4").apiSecret("OVAiRJQWWr6zsr63").build();
        final TextMessage message = new TextMessage("TunisianGot Talent", tel, "Votre code de v\u00e9rification est : " + this.code);
        final SmsSubmissionResponse response = client.getSmsClient().submitMessage((Message)message);
        for (final SmsSubmissionResponseMessage responseMessage : response.getMessages()) {
            System.out.println(responseMessage);
        }
    }
    
    @FXML
    private void changepassword(final ActionEvent event) {
        final String nt = this.tell.getText();
        if (nt.length() < 8 || nt.length() > 8) {
            this.tell.setPromptText("Vous devez entrez 8 chiffres");
        }
        else {
            try {
                System.out.println("dans change pass " + this.code);
                final TrayNotification tray = new TrayNotification("Information", "Code envoy\u00e9 au num\u00e9ro " + this.tell.getText(), NotificationType.SUCCESS);
                tray.setAnimationType(AnimationType.POPUP);
                tray.showAndDismiss(Duration.seconds(3.0));
            }
            catch (Exception e) {
                System.out.println(e);
            }
            this.tell.setVisible(false);
            this.btnsuiv.setVisible(false);
            this.cde.setVisible(true);
            this.btnverif.setVisible(false);
            this.mail.setVisible(false);
        }
    }
    
    @FXML
    private void switchlogin(final ActionEvent event) {
        final String s1 = this.nvpass.getText();
        final String s2 = this.nvpass2.getText();
        final String eml = this.mail.getText();
        final UserService us = new UserService();
        if (s1.equals(s2) && !s1.isEmpty() && !s2.isEmpty()) {
            final String hashed = SHA.encrypt(s1);
            us.changepassword(hashed, eml);
            final TrayNotification tray = new TrayNotification("Succ\u00e9s", "Modification termin\u00e9", NotificationType.SUCCESS);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(3.0));
            this.btnterminer.setVisible(false);
            System.out.println("Op\u00e9ration termin\u00e9");
            try {
                final Stage stage = new Stage();
                final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("FXMLDocument.fxml"));
                final Parent root = (Parent)loader.load();
                stage.setTitle("LOGIN Profil user " + eml);
                final Scene scene = new Scene(root);
                final FXMLDocumentController iu = (FXMLDocumentController)loader.getController();
                iu.setMaill(eml);
                stage.setScene(scene);
                stage.show();
                ((Node)event.getSource()).getScene().getWindow().hide();
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        else {
            final TrayNotification tray2 = new TrayNotification("Oups", "V\u00e9rififiez vos param\u00e9tre", NotificationType.ERROR);
            tray2.setAnimationType(AnimationType.POPUP);
            tray2.showAndDismiss(Duration.seconds(3.0));
            System.out.println("Oups V\u00e9rififiez vos param\u00e9tre");
        }
    }
    
    @FXML
    private void switchupdatepass(final ActionEvent event) {
        this.cde.setVisible(false);
        this.nvpass.setVisible(true);
        this.nvpass2.setVisible(true);
    }
    
    @FXML
    private void verifcode(final KeyEvent event) {
        System.out.println(this.code);
        if (this.cde.getText().equals(this.code)) {
            this.nvpass.setVisible(true);
            this.nvpass2.setVisible(true);
            this.btnverif.setVisible(false);
            this.btnterminer.setVisible(true);
            this.cde.setStyle("-fx-background-color:#3cbc53");
            this.cde.setVisible(false);
        }
        else {
            this.cde.setStyle("-fx-background-color:#ff0000");
        }
    }
}
