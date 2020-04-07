/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Rafaa
 */
public class ConversationController implements Initializable {

    @FXML
    private Button btn1;
    @FXML
    private MenuBar menubar;

    @FXML
    private Button btn2;
    
    @FXML
    private Label lblmail;

    @FXML
    private ListView listeMsg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void logout(ActionEvent event) {
        UserService us =new UserService();
        us.editstatusOff(lblmail.getText());
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void homeAction(ActionEvent event) {
        try {

            Stage stage = (Stage) menubar.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("menubar.fxml"));

            stage.setTitle("Tunisain Got Talent");

            Scene scene = new Scene(parent);
            // scene.getStylesheets().add(getClass().getResource("conv.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    void setdata(String mail){
    lblmail.setText(mail);
    }

    public void editconversationAction(ActionEvent event) {
        try {

            Stage stage = (Stage) menubar.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("TGT.fxml"));
            Scene scene = new Scene(parent);
            stage.setTitle("Tunisain Got Talent");

            stage.setScene(scene);
            stage.show();
            //((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void editinfouserAction(ActionEvent event) {
        try {

            Stage stage = (Stage) menubar.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("EditInfoUser.fxml"));
            Scene scene = new Scene(parent);
            stage.setTitle("Update Information du compte");

            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void addimage(ActionEvent event) {

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("images", "*.png", "*.jpg"));
        List<File> selctedFiles = fc.showOpenMultipleDialog(null);

        if (selctedFiles != null) {
            for (int i = 0; i < selctedFiles.size(); i++) {
                listeMsg.getItems().add(selctedFiles.get(i).getAbsolutePath());
            }
        } else {
            System.out.println("file is not valid");
        }
    }

    public void addpdf(ActionEvent event) {

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Pdf", "*.pdf"));
        List<File> selctedFiles = fc.showOpenMultipleDialog(null);

        if (selctedFiles != null) {
            for (int i = 0; i < selctedFiles.size(); i++) {
                listeMsg.getItems().add(selctedFiles.get(i).getAbsolutePath());
            }
        } else {
            System.out.println("file is not valid");
        }
    }
    public void gotomail (ActionEvent event) {
        try {

            Stage stage = (Stage) menubar.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("SendEmailScene.fxml"));
            Scene scene = new Scene(parent);
            stage.setTitle("Nouvelle reclamation");

            stage.setScene(scene);
            stage.show();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void addconv(ActionEvent event){
         try {

            Stage stage = (Stage) menubar.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("GroupScene.fxml"));
            Scene scene = new Scene(parent);
            stage.setTitle("New conversation");

            stage.setScene(scene);
            stage.show();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
    }

}
