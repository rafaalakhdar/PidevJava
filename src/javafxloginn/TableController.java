// 
// Decompiled by Procyon v0.5.36
// 

package javafxloginn;

import java.util.ResourceBundle;
import java.net.URL;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.Node;
import javafx.scene.input.ContextMenuEvent;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;
import tray.notification.NotificationType;
import services.UserService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.beans.value.ObservableValue;
import utilitez.Mailing;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import utilitez.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javafx.scene.control.TableColumn;
import entities.User;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;

public class TableController implements Initializable
{
    @FXML
    private Button efface;
    @FXML
    private TextField searchfield;
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, Integer> id;
    @FXML
    private TableColumn<User, String> nom;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TableColumn<User, String> password;
    @FXML
    private TableColumn<User, String> sexe;
    @FXML
    private TableColumn<User, String> pays;
    @FXML
    private TableColumn<User, String> status;
    PreparedStatement pst;
    ResultSet rs;
    Connection cnx2;
    public ObservableList<User> list;
    
    public TableController() {
        this.pst = null;
        this.rs = null;
        this.list = FXCollections.observableArrayList();
        this.cnx2 = MyConnection.getInstance().getCnx();
    }
    
    @FXML
    public void listeTalent() {
        try {
            final String requete = "SELECT * FROM user";
            final PreparedStatement stat = this.cnx2.prepareStatement(requete);
            final ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                this.list.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        this.id.setCellValueFactory((Callback)new PropertyValueFactory("id"));
        this.nom.setCellValueFactory((Callback)new PropertyValueFactory("nom"));
        this.email.setCellValueFactory((Callback)new PropertyValueFactory("email"));
        this.password.setCellValueFactory((Callback)new PropertyValueFactory("password"));
        this.sexe.setCellValueFactory((Callback)new PropertyValueFactory("sexe"));
        this.pays.setCellValueFactory((Callback)new PropertyValueFactory("pays"));
        this.status.setCellValueFactory((Callback)new PropertyValueFactory("status"));
        this.table.setItems((ObservableList)this.list);
        final ObservableList data = this.table.getItems();
        this.searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && newValue.length() < oldValue.length()) {
                this.table.setItems(data);
            }
            final String value = newValue.toLowerCase();
            final ObservableList<User> subentries = FXCollections.observableArrayList();
            final long count = this.table.getColumns().stream().count();
            for (int i = 0; i < this.table.getItems().size(); ++i) {
                for (int j = 0; j < count; ++j) {
                    final String entry = "" + ((TableColumn)this.table.getColumns().get(j)).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(this.table.getItems().get(i));
                        break;
                    }
                }
            }
            this.table.setItems((ObservableList)subentries);
        });
        final Label label = new Label();
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem item1 = new MenuItem("Reclamer");
        item1.setOnAction((EventHandler)new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                label.setText("Select Menu Item 1");
                final User user = (User)TableController.this.table.getSelectionModel().getSelectedItem();
                final ObservableValue<String> x = TableController.this.email.getCellObservableValue(user);
                final String to = (String)x.getValue();
                final String subject = "Reclamation Admin TGT";
                final String message = " Vous avez recu cette Reclamation From service reclamation de notre application\na cause d'une mauvaise utilisation de la conversation au cours du Chat ";
                final String usermail = "rafaa.lakhdhar@esprit.tn";
                final String passmail = "191SMT4905";
                Mailing.send(to, subject, message, usermail, passmail);
            }
        });
        final MenuItem item2 = new MenuItem("Delete");
        item2.setOnAction((EventHandler)new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                try {
                    TableController.this.deleteuser(event);
                    label.setText("Select Menu Item 2");
                }
                catch (Exception ex) {
                    Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        final MenuItem item3 = new MenuItem("Block");
        item3.setOnAction((EventHandler)new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                try {
                    final User user = (User)TableController.this.table.getSelectionModel().getSelectedItem();
                    final ObservableValue<Integer> y = TableController.this.id.getCellObservableValue(user);
                    final ObservableValue<String> n = TableController.this.nom.getCellObservableValue(user);
                    final UserService us = new UserService();
                    us.block((int)y.getValue());
                    final TrayNotification tray = new TrayNotification("Information", "L'utilisateur " + (String)n.getValue() + " block\u00e9 ", NotificationType.SUCCESS);
                    tray.setAnimationType(AnimationType.POPUP);
                    tray.showAndDismiss(Duration.seconds(3.0));
                    label.setText("Select Menu Item 3");
                }
                catch (Exception ex) {
                    Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        contextMenu.getItems().addAll(new MenuItem[] { item1, item2, item3 });
        this.table.setOnContextMenuRequested((EventHandler)new EventHandler<ContextMenuEvent>() {
            public void handle(final ContextMenuEvent event) {
                contextMenu.show((Node)TableController.this.table, event.getScreenX(), event.getScreenY());
            }
        });
    }
    
    public void deleteuser(final ActionEvent event) throws Exception {
        try {
            final User user = (User)this.table.getSelectionModel().getSelectedItem();
            final String requete = "delete FROM user where id=?";
            (this.pst = this.cnx2.prepareStatement(requete)).setInt(1, user.getId());
            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText((String)null);
            alert.setContentText("Are you sure to delete?");
            final Optional<ButtonType> action = (Optional<ButtonType>)alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                this.pst.executeUpdate();
            }
            this.pst.close();
        }
        catch (SQLException e1) {
            System.err.println(e1);
            final Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setHeaderText((String)null);
            alert2.setContentText("erreur");
            alert2.showAndWait();
        }
        this.table.getItems().clear();
        this.listeTalent();
    }
    
    public void updateuser(final ActionEvent event) {
        try {
            final String requete = "SELECT * FROM user";
            final PreparedStatement stat = this.cnx2.prepareStatement(requete);
            final ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                this.list.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        this.table.getItems().clear();
        this.id.setCellValueFactory((Callback)new PropertyValueFactory("id"));
        this.nom.setCellValueFactory((Callback)new PropertyValueFactory("nom"));
        this.email.setCellValueFactory((Callback)new PropertyValueFactory("email"));
        this.password.setCellValueFactory((Callback)new PropertyValueFactory("password"));
        this.sexe.setCellValueFactory((Callback)new PropertyValueFactory("sexe"));
        this.pays.setCellValueFactory((Callback)new PropertyValueFactory("pays"));
        this.status.setCellValueFactory((Callback)new PropertyValueFactory("status"));
        this.table.setItems((ObservableList)this.list);
    }
    
    public void initialize(final URL url, final ResourceBundle rb) {
        this.listeTalent();
    }
}
