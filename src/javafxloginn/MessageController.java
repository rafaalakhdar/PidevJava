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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import utilitez.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Date;
import javafx.scene.control.TableColumn;
import entities.Message;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;

public class MessageController implements Initializable
{
    @FXML
    private TextField searchfield;
    @FXML
    private TableView<Message> table;
    @FXML
    private TableColumn<Message, Integer> id;
    @FXML
    private TableColumn<Message, String> message;
    @FXML
    private TableColumn<Message, String> image;
    @FXML
    private TableColumn<Message, Date> date;
    @FXML
    private TableColumn<Message, Integer> idconv;
    @FXML
    private TableColumn<Message, Integer> iduser;
    PreparedStatement pst;
    ResultSet rs;
    Connection cnx2;
    public ObservableList<Message> list;
    
    public MessageController() {
        this.pst = null;
        this.rs = null;
        this.list = FXCollections.observableArrayList();
        this.cnx2 = MyConnection.getInstance().getCnx();
    }
    
    @FXML
    public void listeMessage() {
        try {
            final String requete = "SELECT * FROM message";
            final PreparedStatement stat = this.cnx2.prepareStatement(requete);
            final ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                this.list.add(new Message(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDate(5)));
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        this.table.setEditable(true);
        this.id.setCellValueFactory((Callback)new PropertyValueFactory("idM"));
        this.message.setCellValueFactory((Callback)new PropertyValueFactory("message"));
        this.image.setCellValueFactory((Callback)new PropertyValueFactory("image"));
        this.date.setCellValueFactory((Callback)new PropertyValueFactory("createdAt"));
        this.iduser.setCellValueFactory((Callback)new PropertyValueFactory("userId"));
        this.table.setItems((ObservableList)this.list);
        final ObservableList data = this.table.getItems();
        this.searchfield.textProperty().addListener((ChangeListener)new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if (oldValue != null && newValue.length() < oldValue.length()) {
                    MessageController.this.table.setItems(data);
                }
                final String value = newValue.toLowerCase();
                final ObservableList<Message> subentries = FXCollections.observableArrayList();
                final long count = MessageController.this.table.getColumns().stream().count();
                for (int i = 0; i < MessageController.this.table.getItems().size(); ++i) {
                    for (int j = 0; j < count; ++j) {
                        final String entry = "" + ((TableColumn)MessageController.this.table.getColumns().get(j)).getCellData(i);
                        if (entry.toLowerCase().contains(value)) {
                            subentries.add(MessageController.this.table.getItems().get(i));
                            break;
                        }
                    }
                }
                MessageController.this.table.setItems((ObservableList)subentries);
            }
        });
        final Label label = new Label();
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem item1 = new MenuItem("reclamer");
        item1.setOnAction((EventHandler)new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                label.setText("Select Menu Item 1");
            }
        });
        final MenuItem item2 = new MenuItem("delete");
        item2.setOnAction((EventHandler)new EventHandler<ActionEvent>() {
            public void handle(final ActionEvent event) {
                try {
                    MessageController.this.deletemsg(event);
                    label.setText("Select Menu Item 2");
                }
                catch (Exception ex) {
                    Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        contextMenu.getItems().addAll(new MenuItem[] { item1, item2 });
        this.table.setOnContextMenuRequested((EventHandler)new EventHandler<ContextMenuEvent>() {
            public void handle(final ContextMenuEvent event) {
                contextMenu.show((Node)MessageController.this.table, event.getScreenX(), event.getScreenY());
            }
        });
    }
    
    public void deletemsg(final ActionEvent event) throws Exception {
        try {
            final Message msg = (Message)this.table.getSelectionModel().getSelectedItem();
            final String requete = "delete FROM message where idM=?";
            (this.pst = this.cnx2.prepareStatement(requete)).setInt(1, msg.getIdM());
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
        this.listeMessage();
    }
    
    public void initialize(final URL url, final ResourceBundle rb) {
        this.listeMessage();
    }
}
