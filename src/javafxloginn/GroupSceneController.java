/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;

import entities.Conversation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import entities.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import services.ConversationService;
import services.ServiceNotification;
import services.UserService;
import utilitez.MyConnection;
import utilitez.SHA;
import utilitez.ServiceSysdate;

/**
 * FXML Controller class
 *
 * @author rafaa
 */
public class GroupSceneController implements Initializable {

    ServiceSysdate ss = new ServiceSysdate();
    @FXML
    private TextField txtFieldGroupName;
    @FXML
    private ListView<String> listviewGroup;
    @FXML
    private Button btnCreate;
    @FXML
    private Text txtErrorGroupName;
    ConversationService cs = new ConversationService();
    Connection cnx2;
    Integer i;

    ArrayList<String> groupMembers = new ArrayList<>();
    ArrayList<Integer> groupMembersid = new ArrayList<>();
    UserService us = new UserService();

    public GroupSceneController() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    void setdata2(Integer id) {
        i = id;

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<User> friendsList = (ArrayList<User>) us.getAllUsers();
        ArrayList<String> contactsName = new ArrayList<>();

        if (friendsList != null) {
            for (User contact : friendsList) {
                contactsName.add(contact.getNom());

            }
        }
        //set it within the last if .. this just for test
        ObservableList<String> data = FXCollections.observableArrayList(contactsName);
        listviewGroup.setItems(data);
        listviewGroup.setCellFactory(listView -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    CheckBox checkbox = new CheckBox(item);
                    checkbox.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (!groupMembers.contains(checkbox.getText())) {
                                groupMembers.add(checkbox.getText());
                                groupMembersid.add(us.searchBynomuser(checkbox.getText()));
                            } else {
                                groupMembers.remove(checkbox.getText());
                                groupMembersid.remove(us.searchBynomuser(checkbox.getText()));
                            }
                        }
                    });
                    setGraphic(checkbox);
                }
            }
        });

        txtErrorGroupName.setVisible(false);
    }

    @FXML
    public void btnCreateAction(ActionEvent event) {
        txtErrorGroupName.setVisible(false);
        try {
            int idconv = 9999;
            Date d = ss.selectDate();
            String groupName = txtFieldGroupName.getText();
            String requete = "insert into conversation (nom,date_creation) values('" + groupName + "','" + d + "')";
            Statement st = cnx2.createStatement();
            st.executeUpdate(requete);
            System.out.println("Conversation Ajouter");
            Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
            alertSuccess.setTitle("Successfully");
            alertSuccess.setHeaderText("Updated with Sucsess ");
            alertSuccess.setContentText("Welcome to chhat");
            alertSuccess.showAndWait();

            idconv = cs.searchBynom(groupName);

            for (Integer idd : groupMembersid) {
                //System.out.println("" + idd + " id conv = " + idconv + "conv name " + groupName);
                String requete2 = "insert into conversation_user (conversation_id,user_id) values('" + idconv + "','" + idd + "')";

                Statement st2 = cnx2.createStatement();
                st2.executeUpdate(requete2);
            }
            String requete3 = "insert into conversation_user (conversation_id,user_id) values('" + idconv + "','" + i + "')";
                Statement st3 = cnx2.createStatement();
                st3.executeUpdate(requete3);
                ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (SQLException ex) {
            Logger.getLogger(GroupSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
