/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;

import entities.Conversation;
import entities.User;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javax.xml.datatype.XMLGregorianCalendar;
import sun.util.calendar.LocalGregorianCalendar;
import utilitez.MyConnection;

/**
 * FXML Controller class
 *
 * @author Rafaa
 */
public class FXMLController implements Initializable {
    Connection cnx2;

    public FXMLController(){
      cnx2 = MyConnection.getInstance().getCnx();

}
    @FXML
    private Button btneface, btnupdate;

    @FXML
    private TableView<Conversation> table;
    @FXML
    private TableColumn<Conversation, Integer> id;
    @FXML
    private TableColumn<Conversation, String> nom;
    @FXML
    private TableColumn<Conversation, String> datecreation;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public ObservableList<Conversation> list = FXCollections.observableArrayList();
    //private PreparedStatement PreparedStatement;

    @FXML
    public void listeConversation() {

        try {
            //Connection cnx2 = MyConnection.getInstance().getCnx();

            String requete = "SELECT * FROM conversation";
            PreparedStatement stat = cnx2.prepareStatement(requete);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                list.add(new Conversation(rs.getInt(1), rs.getString(2), rs.getString(3)));

            }
            //cnx2.close();
           // rs.close();

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
        id.setCellValueFactory(new PropertyValueFactory<Conversation, Integer>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<Conversation, String>("nom"));
        datecreation.setCellValueFactory(new PropertyValueFactory<Conversation, String>("dateCreation"));

        table.setItems(list);

    }
     public void deleteconv(ActionEvent event) throws Exception {
        try {
            
            Conversation conv = (Conversation) table.getSelectionModel().getSelectedItem();

            String requete = "delete FROM conversation where id=?" ;
           pst = cnx2.prepareStatement(requete);
            pst.setInt(1, conv.getId());
            
             Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure to delete?");
                Optional <ButtonType> action = alert.showAndWait();
                
                if(action.get() == ButtonType.OK){
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
