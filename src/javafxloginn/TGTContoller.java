/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Rafaa
 */
public class TGTContoller implements Initializable {

    // @FXML public ListView<String> mylist;
    @FXML
    public TreeView<String> mytree;

    // ObservableList<String> list = FXCollections.observableArrayList("ferdaws", "rafaa", "youssef", "fadhila");
    Image icon = new Image(getClass().getResourceAsStream("/img/param.png"));
    Image icon2 = new Image(getClass().getResourceAsStream("/img/param2.png"));

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // mylist.setItems(list);
        // mylist.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TreeItem<String> root = new TreeItem<>("Gestion Tunisian Got Talent", new ImageView(icon2));
        TreeItem<String> conv = new TreeItem<>("Conversation", new ImageView(icon));
        TreeItem<String> mail = new TreeItem<>("Send Mail", new ImageView(icon));

        TreeItem<String> shop = new TreeItem<>("Shop", new ImageView(icon));

        TreeItem<String> recl = new TreeItem<>("Reclamations", new ImageView(icon));
        TreeItem<String> pub = new TreeItem<>("Publications", new ImageView(icon));
        TreeItem<String> even = new TreeItem<>("Evenement", new ImageView(icon));
        TreeItem<String> partic = new TreeItem<>("Partcipation", new ImageView(icon));
        TreeItem<String> aud = new TreeItem<>("Audition", new ImageView(icon));
        root.getChildren().addAll(conv, mail, shop, recl, pub, even, partic, aud);

        mytree.setRoot(root);

    }

    /*   public void choiwliste(ActionEvent event) {
        ObservableList<String> messages;
        messages = mylist.getSelectionModel().getSelectedItems();
        messages.forEach((msg) -> {
            System.out.println(msg);
        });
    }*/
    public void choixtree(MouseEvent mouse) throws Exception {
        TreeItem<String> item = mytree.getSelectionModel().getSelectedItem();
        if (item.getValue().equals("Shop")) {

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("SendEmailScene.fxml"));
            stage.setTitle("Gestion du Shop");
            Scene scene = new Scene(root, 1280, 720);

            stage.setScene(scene);
            stage.show();

        } else if (item.getValue().equals("Conversation")) {

            //System.out.println("nono");
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
            stage.setTitle("Gestion des conversations");
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

        } else if (item.getValue().equals("Partcipation")) {

            //System.out.println("nono");
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("SendEmailScene.fxml"));
            stage.setTitle("Gestion de participation");
            Scene scene = new Scene(root, 1280, 720);

            stage.setScene(scene);
            stage.show();

            //((Node) (mouse.getSource())).getScene().getWindow().hide();
        } else if (item.getValue().equals("Send Mail")) {

            //System.out.println("nono");
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("SendEmailScene.fxml"));
            stage.setTitle("Gestion Conversation <reclamation>");
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

        }

    }

    public void logout(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
}
