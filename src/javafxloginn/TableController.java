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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.util.Duration;
import services.UserService;
import tray.animations.AnimationType;
import static tray.notification.NotificationType.SUCCESS;
import tray.notification.TrayNotification;
import utilitez.Mailing;

/**
 * FXML Controller class
 *
 * @author Rafaa
 */
public class TableController implements Initializable {

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

    PreparedStatement pst = null;
    ResultSet rs = null;
    Connection cnx2;

    public TableController() {
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
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        pays.setCellValueFactory(new PropertyValueFactory<>("pays"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
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
            ObservableList<User> subentries = FXCollections.observableArrayList();

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

        MenuItem item1 = new MenuItem("Reclamer");
        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                label.setText("Select Menu Item 1");
                User user = (User) table.getSelectionModel().getSelectedItem();
                ObservableValue<String> x = email.getCellObservableValue(user);
                //System.out.println(x.getValue());

                String to = x.getValue();
                String subject = "Reclamation Admin TGT";
                String message = " Vous avez recu cette Reclamation From service reclamation de notre application\na cause d'une mauvaise utilisation de la conversation au cours du Chat ";
                String usermail = "rafaa.lakhdhar@esprit.tn";
                String passmail = "191SMT4905";
                Mailing.send(to, subject, message, usermail, passmail);
            }
        });
        MenuItem item2 = new MenuItem("Delete");
        item2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    deleteuser(event);
                    label.setText("Select Menu Item 2");
                } catch (Exception ex) {
                    Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        MenuItem item3 = new MenuItem("Block");
        item3.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    User user = (User) table.getSelectionModel().getSelectedItem();
                    ObservableValue<Integer> y = id.getCellObservableValue(user);
                    ObservableValue<String> n = nom.getCellObservableValue(user);

                    UserService us = new UserService();
                    us.block(y.getValue());
                    TrayNotification tray = new TrayNotification("Information", "L'utilisateur " + n.getValue() + " blocké ", SUCCESS);
                    tray.setAnimationType(AnimationType.POPUP);
                    tray.showAndDismiss(Duration.seconds(3));

                    label.setText("Select Menu Item 3");
                } catch (Exception ex) {
                    Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Add MenuItem 
        contextMenu.getItems().addAll(item1, item2, item3);

        // When user right-click 
        table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {

                contextMenu.show(table, event.getScreenX(), event.getScreenY());
            }
        });

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
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
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
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        pays.setCellValueFactory(new PropertyValueFactory<>("pays"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        table.setItems(list);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listeTalent();
    }

}
