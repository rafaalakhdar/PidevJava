/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;

import entities.User;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Rafaa
 */
public class MenubarController implements Initializable {

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
    private ImageView img, img2;

    @FXML
    private WebView web;
    private WebEngine engine;
    String x = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        img.setVisible(false);
        img2.setVisible(false);

        engine = web.getEngine();
        engine.load("http://127.0.0.1:8000");
        

    }

    void setMail(String email) {

        mailfield.setText(email);
        String x = mailfield.getText();
        System.out.println(x);
        UserService us = new UserService();
        if (!us.ckecksexe(x).equals("Male")) {

            img2.setVisible(true);
        } else {

            img.setVisible(true);
            img2.setVisible(false);
        }
    }

    public void chatpageAction(ActionEvent event) {
        String p = mailfield.getText();
        try {

            //Stage stage = new Stage();
            Stage stage = (Stage) myMenubar.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("conversation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Chat Box " + p);
            ConversationController cc = loader.getController();
            cc.setdata(p);

            stage.setScene(scene);
            stage.show();
            //((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void updateinfoAction(ActionEvent event) {
        profil = mailfield.getText();
        try {

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditInfoUser.fxml"));

            Parent root = loader.load();
            stage.setTitle("Update Profil user " + profil);
            Scene scene = new Scene(root);

            EditInfoUserController iu = loader.getController();
            iu.send(profil);

            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void dateshow(ActionEvent event) {
        LocalDate datenaissance = dp.getValue();
        // lbl.setText(datenaissance.toString());
    }

    public void btn1(ActionEvent event) {
        engine.load("https://www.facebook.com/");
    }

    public void btn2(ActionEvent event) {
        engine.load("https://www.youtube.com/");
    }

    public void btn3(ActionEvent event) {
        engine.load("https://mail.google.com/");
    }

    public void btn4(ActionEvent event) {
        engine.load("http://127.0.0.1:8000");
    }

    public void btn5(ActionEvent event) {
        engine.reload();
    }

    public void logout(ActionEvent event) {
        UserService us = new UserService();
        us.editstatusOff(mailfield.getText());
        img.setVisible(false);
        img2.setVisible(false);
        Platform.exit();
        System.exit(0);

    }

}
