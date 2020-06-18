// 
// Decompiled by Procyon v0.5.36
// 

package javafxloginn;

import javafx.beans.value.ObservableValue;
import javafx.application.Platform;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.List;
import java.io.File;
import javafx.stage.Window;
import javafx.stage.FileChooser;
import services.ServiceNotification;
import java.sql.SQLException;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.ContextMenuEvent;
import javafx.event.Event;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextInputDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ContextMenu;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Collection;
import utilitez.MyConnection;
import javafx.collections.FXCollections;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javafx.collections.ObservableList;
import services.UserService;
import services.ConversationService;
import entities.User;
import java.sql.Date;
import javafx.scene.control.TableColumn;
import entities.Conversation;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;

public class ConversationController implements Initializable
{
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
    @FXML
    private TextField searchfield;
    @FXML
    private TableView<Conversation> tableconv;
    @FXML
    private TableColumn<Conversation, Integer> id;
    @FXML
    private TableColumn<Conversation, String> nom;
    @FXML
    private TableColumn<Conversation, Date> date;
    @FXML
    private TableView<User> tableuser;
    @FXML
    private TableColumn<User, Integer> idu;
    @FXML
    private TableColumn<User, String> name;
    @FXML
    private TableColumn<User, String> origine;
    String muser;
    Integer i;
    ConversationService cs;
    UserService us;
    User owner;
    public ObservableList<Conversation> list;
    public ObservableList<User> listu;
    PreparedStatement pst;
    ResultSet rs;
    Connection cnx2;
    
    public ConversationController() {
        this.muser = "";
        this.cs = new ConversationService();
        this.us = new UserService();
        this.owner = new User();
        this.list = FXCollections.observableArrayList();
        this.listu = FXCollections.observableArrayList();
        this.pst = null;
        this.rs = null;
        this.cnx2 = MyConnection.getInstance().getCnx();
    }
    
    void setdata(final String mail) {
        this.lblmail.setText(mail);
        this.muser = this.lblmail.getText();
    }
    
    public void getlisteconv() {
        this.owner = this.us.findBymail(this.muser);
        this.i = this.owner.getId();
        System.out.println("numero id " + this.i);
        this.list.addAll((Collection)this.cs.getAllConv(this.i));
        this.id.setCellValueFactory((Callback)new PropertyValueFactory("id"));
        this.nom.setCellValueFactory((Callback)new PropertyValueFactory("nom"));
        this.date.setCellValueFactory((Callback)new PropertyValueFactory("dateCreation"));
        this.id.setVisible(false);
        this.tableconv.setItems((ObservableList)this.list);
        final ObservableList data = this.tableconv.getItems();
        this.searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && newValue.length() < oldValue.length()) {
                this.tableconv.setItems(data);
            }
            final String value = newValue.toLowerCase();
            final ObservableList<Conversation> subentries =FXCollections.observableArrayList();
            final long count = this.tableconv.getColumns().stream().count();
            for (int i = 0; i < this.tableconv.getItems().size(); ++i) {
                for (int j = 0; j < count; ++j) {
                    final String entry = "" + ((TableColumn)this.tableconv.getColumns().get(j)).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(this.tableconv.getItems().get(i));
                        break;
                    }
                }
            }
            this.tableconv.setItems((ObservableList)subentries);
        });
        final Label label = new Label();
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem item1 = new MenuItem("Update Name");
        item1.setOnAction((EventHandler)new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                try {
                    final TextInputDialog dialog = new TextInputDialog("");
                    dialog.setTitle("Update Nom Conversation");
                    dialog.setHeaderText("Updating");
                    dialog.setContentText("Enter new name : ");
                    final Optional<String> result = (Optional<String>)dialog.showAndWait();
                    ConversationController.this.editnomconv(result.get());
                    label.setText("Select Menu Item 1");
                }
                catch (Exception ex) {
                    Logger.getLogger(ConversationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        final MenuItem item2 = new MenuItem("Delete");
        item2.setOnAction((EventHandler)new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                try {
                    ConversationController.this.deleteconv(event);
                    label.setText("Select Menu Item 2");
                }
                catch (Exception ex) {
                    Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        contextMenu.getItems().addAll(new MenuItem[] { item1, item2 });
        this.tableconv.setOnContextMenuRequested((EventHandler)new EventHandler<ContextMenuEvent>() {
            public void handle(final ContextMenuEvent event) {
                contextMenu.show((Node)ConversationController.this.tableconv, event.getScreenX(), event.getScreenY());
            }
        });
    }
    
    public void getonlineusers() {
        this.tableconv.getItems().clear();
        this.tableuser.getItems().clear();
        this.listu.addAll((Collection)this.us.getAllUsers());
        this.idu.setCellValueFactory((Callback)new PropertyValueFactory("id"));
        this.name.setCellValueFactory((Callback)new PropertyValueFactory("nom"));
        this.origine.setCellValueFactory((Callback)new PropertyValueFactory("sexe"));
        this.idu.setVisible(false);
        this.id.setVisible(false);
        this.tableuser.setItems((ObservableList)this.listu);
        final ObservableList data = this.tableuser.getItems();
        this.searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && newValue.length() < oldValue.length()) {
                this.tableuser.setItems(data);
            }
            final String value = newValue.toLowerCase();
            final ObservableList<User> subentries1 = FXCollections.observableArrayList();
            final long count = this.tableuser.getColumns().stream().count();
            for (int i = 0; i < this.tableuser.getItems().size(); ++i) {
                for (int j = 0; j < count; ++j) {
                    final String entry = "" + ((TableColumn)this.tableuser.getColumns().get(j)).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries1.add(this.tableuser.getItems().get(i));
                        break;
                    }
                }
            }
            this.tableuser.setItems((ObservableList)subentries1);
        });
        final Label label = new Label();
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem item1 = new MenuItem("Voir Profil");
        item1.setOnAction((EventHandler)new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                try {
                    final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("UserInfoView.fxml"));
                    final Parent root = (Parent)loader.load();
                    final Stage stage = new Stage();
                    stage.setTitle("Profil");
                    final Scene scene = new Scene(root);
                    final User u = (User)ConversationController.this.tableuser.getSelectionModel().getSelectedItem();
                    final Integer i = u.getId();
                    final UserInfoController iu = (UserInfoController)loader.getController();
                    iu.send(i);
                    stage.setScene(scene);
                    stage.show();
                    label.setText("Select Menu Item 1");
                }
                catch (IOException ex) {
                    Logger.getLogger(ConversationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        contextMenu.getItems().addAll(new MenuItem[] { item1 });
        this.tableuser.setOnContextMenuRequested((EventHandler)new EventHandler<ContextMenuEvent>() {
            public void handle(final ContextMenuEvent event) {
                contextMenu.show((Node)ConversationController.this.tableuser, event.getScreenX(), event.getScreenY());
            }
        });
    }
    
    @FXML
    private void homeAction(final ActionEvent event) {
        try {
            final Stage stage = (Stage)this.menubar.getScene().getWindow();
            final Parent parent = (Parent)FXMLLoader.load(this.getClass().getResource("menubar.fxml"));
            stage.setTitle("Tunisain Got Talent");
            final Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
            ((Node)event.getSource()).getScene().getWindow().hide();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteconv(final ActionEvent event) throws Exception {
        try {
            final Conversation conv = (Conversation)this.tableconv.getSelectionModel().getSelectedItem();
            final String requete = "delete FROM conversation where id=?";
            (this.pst = this.cnx2.prepareStatement(requete)).setInt(1, conv.getId());
            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText((String)null);
            alert.setContentText("Are you sure to delete?");
            final Optional<ButtonType> action = (Optional<ButtonType>)alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                this.pst.executeUpdate();
            }
        }
        catch (SQLException e1) {
            System.err.println(e1);
            final Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setHeaderText((String)null);
            alert2.setContentText("erreur");
            alert2.showAndWait();
        }
        this.tableconv.getItems().clear();
        this.getlisteconv();
    }
    
    public void editnomconv(final String nom) throws Exception {
        try {
            final Conversation conv = (Conversation)this.tableconv.getSelectionModel().getSelectedItem();
            final String requete = "update conversation set nom='" + nom + "' where id=?";
            (this.pst = this.cnx2.prepareStatement(requete)).setInt(1, conv.getId());
            this.pst.executeUpdate();
            ServiceNotification.showNotif("Sucsess", "Name Updated ");
        }
        catch (SQLException e1) {
            final Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText((String)null);
            alert.setContentText("erreur");
            alert.showAndWait();
        }
        this.tableconv.getItems().clear();
        this.getlisteconv();
    }
    
    public void addimage(final ActionEvent event) {
        final FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter[] { new FileChooser.ExtensionFilter("images", new String[] { "*.png", "*.jpg" }) });
        final List<File> selctedFiles = (List<File>)fc.showOpenMultipleDialog((Window)null);
        if (selctedFiles != null) {
            for (int i = 0; i < selctedFiles.size(); ++i) {
                this.listeMsg.getItems().add((Object)selctedFiles.get(i).getAbsolutePath());
            }
        }
        else {
            System.out.println("file is not valid");
        }
    }
    
    public void addpdf(final ActionEvent event) {
        final FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter[] { new FileChooser.ExtensionFilter("Pdf", new String[] { "*.pdf" }) });
        final List<File> selctedFiles = (List<File>)fc.showOpenMultipleDialog((Window)null);
        if (selctedFiles != null) {
            for (int i = 0; i < selctedFiles.size(); ++i) {
                this.listeMsg.getItems().add((Object)selctedFiles.get(i).getAbsolutePath());
            }
        }
        else {
            System.out.println("file is not valid");
        }
    }
    
    public void gotomail(final ActionEvent event) {
        final String p = this.lblmail.getText();
        try {
            final Stage stage = (Stage)this.menubar.getScene().getWindow();
            final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("SendEmailScene.fxml"));
            final Parent root = (Parent)loader.load();
            final Scene scene = new Scene(root);
            stage.setTitle("Nouvelle reclamation");
            final Conversation2 cn = (Conversation2)loader.getController();
            cn.setdata(p);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void addconv(final ActionEvent event) {
        try {
            final Stage stage = (Stage)this.menubar.getScene().getWindow();
            final Parent parent = (Parent)FXMLLoader.load(this.getClass().getResource("GroupScene.fxml"));
            final Scene scene = new Scene(parent);
            stage.setTitle("New conversation");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void initialize(final URL url, final ResourceBundle rb) {
        this.getonlineusers();
    }
    
    public void logout(final ActionEvent event) {
        this.us.editstatusOff(this.muser);
        Platform.exit();
        System.exit(0);
    }
}
