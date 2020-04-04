/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;

import java.net.URL;
import java.sql.Connection;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utilitez.MyConnection;

import entities.User;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionModel;

/**
 * FXML Controller class
 *
 * @author Rafaa
 */
public class TableController implements Initializable {

    @FXML
    private Button efface;
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

    PreparedStatement pst = null;
    ResultSet rs = null;
        Connection cnx2;

    public TableController(){
      cnx2 = MyConnection.getInstance().getCnx();

}

    public ObservableList<User> list = FXCollections.observableArrayList();


    @FXML
    public void listeTalent() {

        try {
           

            String requete = "SELECT * FROM user";
            PreparedStatement stat = cnx2.prepareStatement(requete);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                list.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));

            }
     

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("username"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        sexe.setCellValueFactory(new PropertyValueFactory<>("gender"));
        pays.setCellValueFactory(new PropertyValueFactory<>("country"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        table.setItems(list);

    }

    public void deleteuser(ActionEvent event) throws Exception {
        try {
          
            User user = (User) table.getSelectionModel().getSelectedItem();

            String requete = "delete FROM user where id=?";
            pst = cnx2.prepareStatement(requete);
            pst.setInt(1, user.getId());
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
        listeTalent();

    }

    public void updateuser(ActionEvent event) {

        try {
           

            String requete = "SELECT * FROM user";
            PreparedStatement stat = cnx2.prepareStatement(requete);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                list.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));

            }
            
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
        }
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("username"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        sexe.setCellValueFactory(new PropertyValueFactory<>("gender"));
        pays.setCellValueFactory(new PropertyValueFactory<>("country"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        table.setItems(list);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listeTalent();
    }

}
