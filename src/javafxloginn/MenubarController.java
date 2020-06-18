// 
// Decompiled by Procyon v0.5.36
// 

package javafxloginn;

import javafx.application.Platform;
import java.time.LocalDate;
import javafx.scene.Node;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import services.UserService;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.image.ImageView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;

public class MenubarController implements Initializable
{
    String profil;
    @FXML
    private Label mailfield;
    @FXML
    private Button updateinfo;
    @FXML
    private DatePicker dp;
    @FXML
    private MenuBar myMenubar;
    @FXML
    private ImageView img;
    @FXML
    private ImageView img2;
    @FXML
    private WebView web;
    private WebEngine engine;
    String x;
    
    public MenubarController() {
        this.x = "";
    }
    
    public void initialize(final URL url, final ResourceBundle rb) {
        this.img.setVisible(false);
        this.img2.setVisible(false);
        (this.engine = this.web.getEngine()).load("http://127.0.0.1:8000");
    }
    
    void setMail(final String email) {
        this.mailfield.setText(email);
        final String x = this.mailfield.getText();
        System.out.println(x);
        final UserService us = new UserService();
        if (!us.ckecksexe(x).equals("Male")) {
            this.img2.setVisible(true);
        }
        else {
            this.img.setVisible(true);
            this.img2.setVisible(false);
        }
    }
    
    public void chatpageAction(final ActionEvent event) {
        final String p = this.mailfield.getText();
        try {
            final Stage stage = (Stage)this.myMenubar.getScene().getWindow();
            final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("conversation.fxml"));
            final Parent root = (Parent)loader.load();
            final Scene scene = new Scene(root);
            stage.setTitle("Chat Box " + p);
            final ConversationController cc = (ConversationController)loader.getController();
            cc.setdata(p);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @FXML
    public void updateinfoAction(final ActionEvent event) {
        this.profil = this.mailfield.getText();
        try {
            final Stage stage = new Stage();
            final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("EditInfoUser.fxml"));
            final Parent root = (Parent)loader.load();
            stage.setTitle("Update Profil user " + this.profil);
            final Scene scene = new Scene(root);
            final EditInfoUserController iu = (EditInfoUserController)loader.getController();
            iu.send(this.profil);
            stage.setScene(scene);
            stage.show();
            ((Node)event.getSource()).getScene().getWindow().hide();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void dateshow(final ActionEvent event) {
        final LocalDate datenaissance = (LocalDate)this.dp.getValue();
    }
    
    public void btn1(final ActionEvent event) {
        this.engine.load("https://www.facebook.com/");
    }
    
    public void btn2(final ActionEvent event) {
        this.engine.load("https://www.youtube.com/");
    }
    
    public void btn3(final ActionEvent event) {
        this.engine.load("https://mail.google.com/");
    }
    
    public void btn4(final ActionEvent event) {
        this.engine.load("http://127.0.0.1:8000");
    }
    
    public void btn5(final ActionEvent event) {
        this.engine.reload();
    }
    
    public void logout(final ActionEvent event) {
        final UserService us = new UserService();
        us.editstatusOff(this.mailfield.getText());
        this.img.setVisible(false);
        this.img2.setVisible(false);
        Platform.exit();
        System.exit(0);
    }
    
    @FXML
    public void test(final ActionEvent event) {
        try {
            final Stage stage = new Stage();
            final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ajoutAudition.fxml"));
            final Parent root = (Parent)loader.load();
            final Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();        
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
