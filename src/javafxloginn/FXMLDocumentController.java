/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;

import entities.User;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import services.ServiceNotification;
import services.UserService;
import services.LoginService;

/**
 *
 * @author Rafaa
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label status;
    @FXML
    private TextField user;
    @FXML
    private TextField pass;
    @FXML
    private Hyperlink linkCreateAccount;
    @FXML
    private Region region;
    @FXML
    private MediaView mediaView;
    @FXML
    private ProgressIndicator indicator;
    @FXML
    private Label connectingLabel;
    
    ResultSet rs = null;
    UserService us = new UserService();
    String email, pwd;
    public static MediaPlayer mediaPlayer;
    
    void setMaill(String email) {
        user.setText(email);
        user.setEditable(false);
        
    }

    /**
     *
     */
    public void Login(ActionEvent event) throws Exception {
        indicator.setVisible(true);
        if (validateFields()) {
            email = user.getText();
            pwd = pass.getText();
            
            if (us.ckeckenabled(email) == 0) {
                Notifications n = Notifications.create()
                        .title("Erreur")
                        .text("Compte désactivé\n utilisé un autre compte")
                        .graphic(null)
                        .position(Pos.TOP_CENTER)
                        .hideAfter(Duration.seconds(5));
                n.showWarning();
                user.clear();
                pass.clear();
                indicator.setVisible(false);
            } else {
                LoginService uss = new LoginService();
                User u = uss.searchUserByEmail(email, pwd);
                
                try {
                    
                    if (u != null) {
                        
                        JavaFXloginn.user = u;
                        uss.setLastdatelogin(u.getEmail_canonical());
                        indicator.setVisible(false);
                        if (!uss.Gettype(email).equals("a:0:{}")) {
                            status.setText("login success");
                            Stage stage = new Stage();
                            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TGT.fxml"));
                            fxmlLoader.getNamespace().put("labelText", user.getText());
                            
                            final Parent root = fxmlLoader.load();
                            stage.setTitle("ADMIN Tunisain Got Talent");
                            Scene scene = new Scene(root, 1260, 550);
                            //stage.setFullScreen(true);
                            stage.setScene(scene);
                            stage.show();
                            ((Node) (event.getSource())).getScene().getWindow().hide();
                            ServiceNotification.showNotif("Bienvenu Admin", " Tunisian Got Talent ");
                            
                        } else if (uss.Gettype(email).equals("a:0:{}")) {
                            status.setText("login success");
                            
                            Stage stage = new Stage();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("menubar.fxml"));
                            Parent root = loader.load();
                            stage.setTitle("Tunisain Got Talent");
                            Scene scene = new Scene(root, 1280, 720);
                            
                            stage.setScene(scene);
                            stage.show();
                            ((Node) (event.getSource())).getScene().getWindow().hide();
                            ServiceNotification.showNotif("Welcome ", "Bienvenu dans Tunisian Got Talent ");
                            
                        }
                    } else {
                        indicator.setVisible(true);
                        status.setText("login failed");
                        
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("login failed");
                        alert.setHeaderText(null);
                        alert.setContentText("login ou mot de pass invalide");
                        alert.showAndWait();
                        indicator.setVisible(false);
                        
                    }
                    user.clear();
                    pass.clear();
                    
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                
            }
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Media media = null;
        try {
            media = new Media(getClass().getResource("/videos/d.mp4").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaView.setMediaPlayer(mediaPlayer);
        
        mediaPlayer.setOnError(() -> System.out.println("Media error:" + mediaPlayer.getError().toString()));

        // Region
        region.setStyle("-fx-background-color:rgb(0,0,0,0.7);");
        region.visibleProperty().bind(indicator.visibleProperty());
        // ConnectingLabel
        connectingLabel.setStyle("-fx-text-fill:white; -fx-font-size:17px; -fx-font-weight:bold;");
        connectingLabel.visibleProperty().bind(indicator.visibleProperty());
        
    }
    
    @FXML
    private void linkCreatAccountAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent parent = FXMLLoader.load(getClass().getResource("SignupScence.fxml"));
            
            stage.setTitle("Noveau Compte");
            
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private boolean validateFields() {
        if (user.getText().isEmpty() || pass.getText().isEmpty()) {
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur Fields");
            alert.setHeaderText(null);
            alert.setContentText(" Champs Vide");
            alert.showAndWait();
            indicator.setVisible(false);
            
            return false;
        }
        
        return true;
    }
    
    private boolean validateEmaill() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(user.getText());
        if (m.find() && m.group().equals(user.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur Email");
            alert.setHeaderText(null);
            alert.setContentText("Email invalide ou vide");
            alert.showAndWait();
            indicator.setVisible(false);
            
            return false;
        }
    }
    
    @FXML
    private void switchMdo(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent parent = FXMLLoader.load(getClass().getResource("changerpass.fxml"));
            
            stage.setTitle("Noveau Compte");
            
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
