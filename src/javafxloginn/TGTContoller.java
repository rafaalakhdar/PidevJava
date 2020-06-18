// 
// Decompiled by Procyon v0.5.36
// 

package javafxloginn;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import javafx.fxml.Initializable;

public class TGTContoller implements Initializable
{
    @FXML
    public TreeView<String> mytree;
    Image icon;
    Image icon2;
    
    public TGTContoller() {
        this.icon = new Image(this.getClass().getResourceAsStream("/img/param.png"));
        this.icon2 = new Image(this.getClass().getResourceAsStream("/img/param2.png"));
    }
    
    public void initialize(final URL url, final ResourceBundle rb) {
        final TreeItem<String> root = (TreeItem<String>)new TreeItem((Object)"Gestion Tunisian Got Talent", new ImageView(this.icon2));
        final TreeItem<String> mail = (TreeItem<String>)new TreeItem((Object)"Reclamer une conversation", new ImageView(this.icon));
        final TreeItem<String> shop = (TreeItem<String>)new TreeItem((Object)"Shop", new ImageView(this.icon));
        final TreeItem<String> recl = (TreeItem<String>)new TreeItem((Object)"Reclamations", new ImageView(this.icon));
        final TreeItem<String> pub = (TreeItem<String>)new TreeItem((Object)"Publications", new ImageView(this.icon));
        final TreeItem<String> even = (TreeItem<String>)new TreeItem((Object)"Evenement", new ImageView(this.icon));
        final TreeItem<String> partic = (TreeItem<String>)new TreeItem((Object)"Partcipation", new ImageView(this.icon));
        final TreeItem<String> aud = (TreeItem<String>)new TreeItem((Object)"Audition", new ImageView(this.icon));
        final TreeItem<String> cat = (TreeItem<String>)new TreeItem((Object)"Categorie", new ImageView(this.icon));
        root.getChildren().addAll(new TreeItem[] { mail, shop, recl, pub, even, partic, aud, cat });
        this.mytree.setRoot((TreeItem)root);
    }
    
    public void choixtree(final MouseEvent mouse) throws Exception {
        final TreeItem<String> item = this.mytree.getSelectionModel().getSelectedItem();
        final String s = (String)item.getValue();
        switch (s) {
            case "Shop": {
                final Stage stage = new Stage();
                final Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("FXML.fxml"));
                stage.setTitle("Gestion du Shop");
                final Scene scene = new Scene(root, 1280.0, 720.0);
                stage.setScene(scene);
                stage.show();
                break;
            }
            case "Publications": {
                final Stage stage = new Stage();
                final Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("FXML.fxml"));
                stage.setTitle("Gestion des conversations");
                final Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            }
            case "Partcipation": {
                final Stage stage = new Stage();
                final Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("FXML.fxml"));
                stage.setTitle("Gestion de participation");
                final Scene scene = new Scene(root, 1280.0, 720.0);
                stage.setScene(scene);
                stage.show();
                break;
            }
            case "Reclamer une conversation": {
                final Stage stage = new Stage();
                final Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("SendEmailScene.fxml"));
                stage.setTitle("Gestion Conversation");
                final Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            }
            case "Audition": {
                final Stage stage = new Stage();
                final Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("GestionAudition.fxml"));
                stage.setTitle("Gestion des Auditions");
                final Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            }
            case "Categorie": {
                final Stage stage = new Stage();
                final Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("GestionCategorie.fxml"));
                stage.setTitle("Gestion des Cat\u00e9gories");
                final Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            }
        }
    }
    
    public void logout(final ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
}
