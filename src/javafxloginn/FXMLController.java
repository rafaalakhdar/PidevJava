/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;

import entities.Conversation;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.FileChooser;
import javax.xml.datatype.XMLGregorianCalendar;
import sun.util.calendar.LocalGregorianCalendar;
import utilitez.Mailing;
import utilitez.MyConnection;

/**
 * FXML Controller class
 *
 * @author Rafaa
 */
public class FXMLController implements Initializable {

    Connection cnx2;

    public FXMLController() {
        cnx2 = MyConnection.getInstance().getCnx();

    }
    @FXML
    private Button btneface, btnupdate;
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
    PreparedStatement pst = null;
    ResultSet rs = null;

    public ObservableList<Conversation> list = FXCollections.observableArrayList();
    //private PreparedStatement PreparedStatement;

    @FXML
    public void listeConversation() {

        try {
            //Connection cnx2 = MyConnection.getInstance().getCnx();

            String requete = "SELECT * FROM conversation order by date_creation DESC";
            PreparedStatement stat = cnx2.prepareStatement(requete);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                list.add(new Conversation(rs.getInt(1), rs.getString(2), rs.getDate(3)));

            }
            //cnx2.close();
            // rs.close();

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        datecreation.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));

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
            ObservableList<Conversation> subentries = FXCollections.observableArrayList();

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

        /*  MenuItem item1 = new MenuItem("reclamer");
        item1.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                try {
                label.setText("Select Menu Item 1");
            Conversation conv = (Conversation) table.getSelectionModel().getSelectedItem();
                ObservableValue<Integer> x= id.getCellObservableValue(conv);
                //System.out.println(x.getValue());
                String requete = " SELECT user.id FROM user JOIN conversation_user ON user.id = conversation_user.user_id "
                        + "JOIN conversation ON conversation.id = conversation_user.conversation_id "
                        + "GROUP BY conversation.id ='"+x+"' ";
                
                    pst = cnx2.prepareStatement(requete);
                    ResultSet resultSet = pst.executeQuery();
                    while (resultSet.next()) {
                    int a =resultSet.getInt(1);
                    System.out.println(x.getValue());
                    System.out.println(a+" ");
                    String to = x.getValue();
                    String subject = "Reclamation Admin TGT";
                    String message =  " Vous avez recu cette Reclamation From service reclamation de notre application\na cause d'une mauvaise utilisation de la conversation au cours du Chat " ;
                    String usermail = "rafaa.lakhdhar@esprit.tn";
                    String passmail = "191SMT4905";
                    Mailing.send(to,subject, message, usermail, passmail);
                    }} catch (SQLException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });*/
        MenuItem item2 = new MenuItem("delete");
        item2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    deleteconv(event);
                    label.setText("Select Menu Item 2");
                } catch (Exception ex) {
                    Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Add MenuItem
        contextMenu.getItems().addAll(item2);

        // When user right-click 
        table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {

                contextMenu.show(table, event.getScreenX(), event.getScreenY());
            }
        });

    }

    public void deleteconv(ActionEvent event) throws Exception {
        try {

            Conversation conv = (Conversation) table.getSelectionModel().getSelectedItem();

            String requete = "delete FROM conversation where id=?";
            pst = cnx2.prepareStatement(requete);
            pst.setInt(1, conv.getId());

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure to delete?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                pst.executeUpdate();

            }
            //pst.close();

        } catch (SQLException e1) {
            System.err.println(e1);
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText(null);
            alert.setContentText("erreur");
            alert.showAndWait();
        }
        table.getItems().clear();
        listeConversation();

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listeConversation();
    }

}
