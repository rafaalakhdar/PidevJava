// 
// Decompiled by Procyon v0.5.36
// 

package javafxloginn;

import javafx.beans.value.ObservableValue;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.Node;
import javafx.scene.input.ContextMenuEvent;
import javafx.event.Event;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Date;
import javafx.scene.control.TableColumn;
import entities.Conversation;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.sql.Connection;
import javafx.fxml.Initializable;

public class FXMLController implements Initializable
{
    Connection cnx2;
    @FXML
    private Button btneface;
    @FXML
    private Button btnupdate;
    @FXML
    private TextField searchfield;
    @FXML
    private TableView<Conversation> table;
    @FXML
    private TableColumn<Conversation, Integer> id;
    @FXML
    private TableColumn<Conversation, String> nom;
    @FXML
    private TableColumn<Conversation, Date> datecreation;
    PreparedStatement pst;
    ResultSet rs;
    public ObservableList<Conversation> list;
    
    public FXMLController() {
        this.pst = null;
        this.rs = null;
        this.list = FXCollections.observableArrayList();
        this.cnx2 = MyConnection.getInstance().getCnx();
    }
    
    @FXML
    public void listeConversation() {
        try {
            final String requete = "SELECT * FROM conversation";
            final PreparedStatement stat = this.cnx2.prepareStatement(requete);
            final ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                this.list.add(new Conversation(rs.getInt(1), rs.getString(2), rs.getDate(3)));
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        this.id.setCellValueFactory((Callback)new PropertyValueFactory("id"));
        this.nom.setCellValueFactory((Callback)new PropertyValueFactory("nom"));
        this.datecreation.setCellValueFactory((Callback)new PropertyValueFactory("dateCreation"));
        this.table.setItems((ObservableList)this.list);
        final ObservableList data = this.table.getItems();
        this.searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && newValue.length() < oldValue.length()) {
                this.table.setItems(data);
            }
            final String value = newValue.toLowerCase();
            final ObservableList<Conversation> subentries = FXCollections.observableArrayList();
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
        final MenuItem item2 = new MenuItem("delete");
        item2.setOnAction((EventHandler)new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                try {
                    FXMLController.this.deleteconv(event);
                    label.setText("Select Menu Item 2");
                }
                catch (Exception ex) {
                    Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        contextMenu.getItems().addAll(new MenuItem[] { item2 });
        this.table.setOnContextMenuRequested((EventHandler)new EventHandler<ContextMenuEvent>() {
            public void handle(final ContextMenuEvent event) {
                contextMenu.show((Node)FXMLController.this.table, event.getScreenX(), event.getScreenY());
            }
        });
    }
    
    public void deleteconv(final ActionEvent event) throws Exception {
        try {
            final Conversation conv = (Conversation)this.table.getSelectionModel().getSelectedItem();
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
        this.table.getItems().clear();
        this.listeConversation();
    }
    
    public void initialize(final URL url, final ResourceBundle rb) {
        this.listeConversation();
    }
}
