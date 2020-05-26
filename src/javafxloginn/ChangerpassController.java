/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;

import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.SmsSubmissionResponseMessage;
import com.nexmo.client.sms.messages.TextMessage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;
import services.ServiceNotification;
import services.UserService;
import tray.animations.AnimationType;
import static tray.notification.NotificationType.ERROR;
import static tray.notification.NotificationType.SUCCESS;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Rafaa
 */
public class ChangerpassController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnverif, btnsuiv, btnterminer, back;
    @FXML
    private TextField tell, cde, mail;
    @FXML
    private PasswordField nvpass2, nvpass;
    UserService us = new UserService();

    Random rand = new Random();
    String code = String.valueOf(rand.nextInt(9000) + 1000);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cde.setVisible(false);
        nvpass.setVisible(false);
        nvpass2.setVisible(false);
        btnterminer.setVisible(false);
        btnverif.setVisible(false);

    }

    public void sendsms() throws NexmoClientException, IOException {
        String tel = "216" + tell.getText();
        System.out.println(tel);
        System.out.println(code);
        NexmoClient client = new NexmoClient.Builder()
                .apiKey("35d2b9e4")
                .apiSecret("OVAiRJQWWr6zsr63")
                .build();
        //aa8ccf52
        //uT2IRxYWd3usCNOR

        TextMessage message = new TextMessage("TunisianGot Talent", tel, "Votre code de vérification est : " + code);
        SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);

        for (SmsSubmissionResponseMessage responseMessage : response.getMessages()) {
            System.out.println(responseMessage);

        }
    }

    @FXML
    private void changepassword(ActionEvent event) {

        String nt = tell.getText();
        if (validateFields()) {
            System.out.println(" error");

            if (nt.length() < 8 || nt.length() > 8) {
                tell.setPromptText("Vous devez entrez 8 chiffres");
                ServiceNotification.showNotif("Alert", "Vous devez entrez 8 chiffres ");

            }
            if (validateEmaill()) {
                try {
                    sendsms();
                    System.out.println("code pour change pass " + code);
                    TrayNotification tray = new TrayNotification("Information", "Code envoyé au numéro " + tell.getText(), SUCCESS);
                    tray.setAnimationType(AnimationType.POPUP);
                    tray.showAndDismiss(Duration.seconds(3));
                } catch (Exception e) {
                    System.out.println(e);
                }
                tell.setVisible(false);
                btnsuiv.setVisible(false);
                cde.setVisible(true);
                btnverif.setVisible(false);
                mail.setVisible(false);
            }
        }
    }

    @FXML
    private void switchlogin(ActionEvent event) {
        String s1 = nvpass.getText();
        String s2 = nvpass2.getText();
        String eml = mail.getText();
        UserService us = new UserService();

        if (s1.equals(s2) && s1.isEmpty() == false && s2.isEmpty() == false) {

            String hashed = BCrypt.hashpw(s1, BCrypt.gensalt());

            us.changepassword(hashed, eml);
            TrayNotification tray = new TrayNotification("Succés", "Modification terminé", SUCCESS);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(3));
            btnterminer.setVisible(false);

            System.out.println("Opération terminé");
            try {

                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));

                Parent root = loader.load();
                stage.setTitle("LOGIN Profil user " + eml);
                Scene scene = new Scene(root);

                FXMLDocumentController iu = loader.getController();
                iu.setMaill(eml);

                stage.setScene(scene);
                stage.show();
                ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        } else {
            TrayNotification tray = new TrayNotification("Oups", "Vérififiez vos paramétre", ERROR);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(3));
            System.out.println("Oups Vérififiez vos paramétre");
        }
    }

    @FXML
    private void switchupdatepass(ActionEvent event) {
        cde.setVisible(false);
        nvpass.setVisible(true);
        nvpass2.setVisible(true);
    }

    @FXML
    private void verifcode(KeyEvent event) {
        System.out.println(code);
        if (cde.getText().equals(code)) {
            nvpass.setVisible(true);
            nvpass2.setVisible(true);
            btnverif.setVisible(false);
            btnterminer.setVisible(true);
            cde.setStyle("-fx-background-color:#3cbc53");
            cde.setVisible(false);
        } else {
            cde.setStyle("-fx-background-color:#ff0000");
        }

    }

    private boolean validateEmaill() {
        if (us.searchByEmail(mail.getText()) == false) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur Email");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Email");
            ServiceNotification.showNotif("Alert", "No user found with this email ");
            alert.showAndWait();
            return false;

        }

        return true;
    }

    private boolean validateFields() {
        if (tell.getText().isEmpty() || mail.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur Fields");
            alert.setHeaderText(null);
            alert.setContentText("Champs Vide");
            alert.showAndWait();

            return false;
        }

        return true;
    }

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
}
