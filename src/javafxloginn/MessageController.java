/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;

import entities.Conversation;
import entities.Message;
import entities.User;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import utilitez.MyConnection;

/**
 * FXML Controller class
 *
 * @author Rafaa
 */
public class MessageController implements Initializable {
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
   

    PreparedStatement pst = null;
    ResultSet rs = null;
    Connection cnx2;

    public MessageController(){
      cnx2 = MyConnection.getInstance().getCnx();

}

    public ObservableList<Message> list = FXCollections.observableArrayList();
    
    
     @FXML
    public void listeMessage() {

        try {
           

            String requete = "SELECT * FROM message";
            PreparedStatement stat = cnx2.prepareStatement(requete);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                list.add(new Message(rs.getInt(2), rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5)
                        //rs.getArray(5),
                        //rs.getObject(1,Conversation.class)
                ));

            }
     

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
        table.setEditable(true);
        id.setCellValueFactory(new PropertyValueFactory<>("idM"));
        message.setCellValueFactory(new PropertyValueFactory<>("message"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));
        date.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        //idconv.setCellValueFactory(new PropertyValueFactory<>("conversationId"));
        iduser.setCellValueFactory(new PropertyValueFactory<>("userId"));
     

        table.setItems(list);
         /**
         * *****************Recherche******************************
         */
        ObservableList data = table.getItems();
        searchfield.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                table.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<Message> subentries = FXCollections.observableArrayList();

            long count = table.getColumns().stream().count();
            for (int i = 0; i < table.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + table.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(table.getItems().get(i));
                        break;
                    }
                }
            }
             table.setItems(subentries);
        });
         // Create ContextMenu
          Label label = new Label();
        ContextMenu contextMenu = new ContextMenu();
 
        MenuItem item1 = new MenuItem("reclamer");
        item1.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                label.setText("Select Menu Item 1");
            }
        });
        MenuItem item2 = new MenuItem("delete");
        item2.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                try {
                    deletemsg(event);
                    label.setText("Select Menu Item 2");
                } catch (Exception ex) {
                    Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
 
        // Add MenuItem 
        contextMenu.getItems().addAll(item1, item2);
 
        // When user right-click 
        table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
 
            @Override
            public void handle(ContextMenuEvent event) {
 
                contextMenu.show(table, event.getScreenX(), event.getScreenY());
            }
        });
    }
    
    
    
      public void deletemsg(ActionEvent event) throws Exception {
        try {
          
            Message msg = (Message) table.getSelectionModel().getSelectedItem();

            String requete = "delete FROM message where idM=?";
            pst = cnx2.prepareStatement(requete);
            pst.setInt(1, msg.getIdM());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure to delete?");
                Optional <ButtonType> action = alert.showAndWait();
                
                if(action.get() == ButtonType.OK){
            pst.executeUpdate();
                }
            pst.close();

        } catch (SQLException e1) {
            System.err.println(e1);
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText(null);
            alert.setContentText("erreur");
            alert.showAndWait();
        }
        table.getItems().clear();
        listeMessage();

    }
    
    
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
               listeMessage();

    }    
    
}
