/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;

import com.sun.javafx.geom.ConcentricShapePair;
import entities.Conversation;
import entities.Message;
import entities.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ConversationService;
import services.MessageService;
import services.ServiceNotification;
import services.UserService;
import utilitez.MyConnection;

/**
 * FXML Controller class
 *
 * @author Rafaa
 */
public class ConversationController implements Initializable {

    @FXML
    private MenuBar menubar;
    @FXML
    private TabPane tabPane;

    @FXML
    private Label lblmail;
    @FXML
    private Tab homeBox;
    Map<String, Tab> tabsOpened = new HashMap<>();
    Map<String, ChatBoxController> tabsControllers = new HashMap<>();

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
    String muser = "";
    Integer i;
    Integer x;

    MessageService ms = new MessageService();
    ConversationService cs = new ConversationService();
    UserService us = new UserService();
    User owner = new User();
    public ObservableList<Conversation> list = FXCollections.observableArrayList();
    public ObservableList<User> listu = FXCollections.observableArrayList();
    public ObservableList<Message> listm = FXCollections.observableArrayList();
    ArrayList<String> listm2 = new ArrayList<>();
    PreparedStatement pst = null;
    ResultSet rs = null;
    User userloged = null;
    int userloggedid;
    String nomconv = "";
    String sendr = "";

    Connection cnx2;

    public ConversationController() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    void setdata(String mail) {

        lblmail.setText(mail);
        muser = lblmail.getText();
        userloged = us.findBymail(muser);
        userloggedid = userloged.getId();

    }

    public void getlisteconv() {

        owner = us.findBymail(muser);
        i = owner.getId();
        System.out.println("numero id " + i);
        list.addAll(cs.getAllConv(i));

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        id.setVisible(false);
        tableconv.setItems(list);
        //recherche
        ObservableList data = tableconv.getItems();
        searchfield.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tableconv.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<Conversation> subentries = FXCollections.observableArrayList();

            long count = tableconv.getColumns().stream().count();
            for (int i = 0; i < tableconv.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tableconv.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(tableconv.getItems().get(i));
                        break;
                    }
                }
            }
            tableconv.setItems(subentries);
        });

        // Create ContextMenu
        Label label = new Label();
        ContextMenu contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("Update Name");
        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    TextInputDialog dialog = new TextInputDialog("");
                    dialog.setTitle("Update Nom Conversation");
                    dialog.setHeaderText("Updating");
                    dialog.setContentText("Enter new name : ");
                    Optional<String> result = dialog.showAndWait();
                    editnomconv(result.get());
                    label.setText("Select Menu Item 1");
                } catch (Exception ex) {
                    Logger.getLogger(ConversationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        MenuItem item2 = new MenuItem("Delete");
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
        MenuItem item3 = new MenuItem("Open");
        item3.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    Conversation conv = (Conversation) tableconv.getSelectionModel().getSelectedItem();
                    String re = "select * FROM conversation where id=?";
                    pst = cnx2.prepareStatement(re);
                    pst.setInt(1, conv.getId());

                    rs = pst.executeQuery();
                    while (rs.next()) {
                        nomconv = rs.getString("nom");
                        x = rs.getInt("id");
                        //System.out.println(nomconv+" "+x);

                        listm.addAll((ms.getallmsg(x, userloggedid)));

                        //
                        for (Message mmm : listm) {
                            sendr = us.searchByid(mmm.getUsermsg());
                            String mesg = ">>> " + sendr + " : " + mmm.getMessage() + " à " + mmm.getDtmsg();
                            //System.out.println(""+mesg+" ");
                            listm2.add(mesg);
                        }

                        cellClickAction(nomconv);
                        listm.clear();

                        label.setText("Select Menu Item 3");
                    }

                } catch (Exception ex) {
                    Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Add MenuItem 
        contextMenu.getItems().addAll(item3, item1, item2);

        // When user right-click 
        tableconv.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {

                contextMenu.show(tableconv, event.getScreenX(), event.getScreenY());
            }
        });

    }

    public void getonlineusers() {

        tableconv.getItems().clear();
        tableuser.getItems().clear();
        listu.addAll(us.getAllUsers());
        idu.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        origine.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        idu.setVisible(false);
        id.setVisible(false);

        tableuser.setItems(listu);

        //recherche
        ObservableList data = tableuser.getItems();
        searchfield.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tableuser.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<User> subentries1 = FXCollections.observableArrayList();

            long count = tableuser.getColumns().stream().count();
            for (int i = 0; i < tableuser.getItems().size(); i++) {
                for (int j = 0; j < count; j++) {
                    String entry = "" + tableuser.getColumns().get(j).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries1.add(tableuser.getItems().get(i));
                        break;
                    }
                }
            }
            tableuser.setItems(subentries1);
        });

        // Create ContextMenu
        Label label = new Label();
        ContextMenu contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("Voir Profil");
        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    /////////////////////////
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("UserInfoView.fxml"));

                    Parent root = loader.load();

                    Stage stage = new Stage();
                    stage.setTitle("Profil");
                    Scene scene = new Scene(root);
                    User u = (User) tableuser.getSelectionModel().getSelectedItem();
                    Integer i;
                    i = u.getId();
                    UserInfoController iu = loader.getController();
                    iu.send(i);

                    stage.setScene(scene);
                    stage.show();

                    label.setText("Select Menu Item 1");
                } catch (IOException ex) {
                    Logger.getLogger(ConversationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Add MenuItem 
        contextMenu.getItems().addAll(item1);

        // When user right-click 
        tableuser.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {

                contextMenu.show(tableuser, event.getScreenX(), event.getScreenY());
            }
        });

    }

    @FXML
    private void homeAction(ActionEvent event) {
        try {
            Stage stage = (Stage) menubar.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menubar.fxml"));
            Parent parent = loader.load();
            MenubarController m = loader.getController();
            m.setMail(muser);

            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Tunisain Got Talent");
            stage.show();

            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteconv(ActionEvent event) throws Exception {
        try {

            Conversation conv = (Conversation) tableconv.getSelectionModel().getSelectedItem();

            String requete = "delete FROM conversation where id=?";
            pst = cnx2.prepareStatement(requete);
            pst.setInt(1, conv.getId());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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
        tableconv.getItems().clear();
        getlisteconv();
    }

    public void editnomconv(String nom) throws Exception {
        try {

            Conversation conv = (Conversation) tableconv.getSelectionModel().getSelectedItem();
            String requete = "update conversation set nom='" + nom + "' where id=?";
            pst = cnx2.prepareStatement(requete);
            pst.setInt(1, conv.getId());
            pst.executeUpdate();
            ServiceNotification.showNotif("Sucsess", "Name Updated ");

        } catch (SQLException e1) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("erreur");
            alert.showAndWait();
        }
        tableconv.getItems().clear();
        getlisteconv();
    }

    public void gotomail(ActionEvent event) {
        String p = lblmail.getText();
        try {

            Stage stage = (Stage) menubar.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SendEmailScene.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Nouvelle reclamation");
            Conversation2 cn = loader.getController();
            cn.setdata(p);

            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void addconv(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GroupScene.fxml"));
            Parent parent = loader.load();
            GroupSceneController gr = loader.getController();
            gr.setdata2(userloggedid);
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Create New conversation");
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        getonlineusers();
        try {
            homeBox.setContent(FXMLLoader.load(getClass().getResource("Map2.fxml")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void logout(ActionEvent event) {

        us.editstatusOff(muser);
        Platform.exit();
        System.exit(0);
    }

    void cellClickAction(String ConvName) {
        if (!tabsOpened.containsKey(ConvName)) {
            try {

                Tab newTab = new Tab();

                newTab.setId(ConvName);
                newTab.setText(ConvName);

                newTab.setClosable(true);
                newTab.setOnCloseRequest(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {

                        tabsOpened.remove(newTab.getId());
                        tabsControllers.remove(newTab.getId());
                    }
                });

                tabPane.getTabs().add(newTab);
                tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatBox.fxml"));
                ChatBoxController chatBoxController = new ChatBoxController();
                loader.setController(chatBoxController);

                chatBoxController.setmessage(listm2, x, userloggedid);
                listm2.clear();

                tabsOpened.put(ConvName, newTab);
                tabsControllers.put(ConvName, chatBoxController);

                newTab.setContent(loader.load());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            tabPane.getSelectionModel().select(tabsOpened.get(ConvName));
        }
    }

}
