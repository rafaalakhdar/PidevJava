/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import entities.Message;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javafx.util.Duration;
import services.MessageService;
import services.UserService;
import tray.animations.AnimationType;
import static tray.notification.NotificationType.SUCCESS;
import tray.notification.TrayNotification;
import utilitez.Copy;
import utilitez.MyConnection;
import utilitez.ServiceSysdate;

/**
 * FXML Controller class
 *
 * @author rafaa
 */
public class ChatBoxController implements Initializable {

    @FXML
    private VBox chatBox;
    @FXML
    private TextField txtFieldMsg;
    @FXML
    private ListView<String> listviewChat;
    ArrayList<String> liste = new ArrayList<>();
    UserService us = new UserService();
    ServiceSysdate ss = new ServiceSysdate();
    MessageService ms = new MessageService();
    Message message;
    String body = "";
    String nomuser;
    String img;
    Integer idconv;
    Integer iduser;
    Connection cnx2;

    public ChatBoxController() {
        cnx2 = MyConnection.getInstance().getCnx();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshmsg();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setmessage(ArrayList<String> l, Integer idc, Integer idu) {
        liste.addAll(l);
        idconv = idc;
        iduser = idu;
        nomuser = us.searchByid(iduser);

    }

    @FXML
    public void refreshmsg() {

        for (String mg : liste) {
            listviewChat.getItems().add(mg);
        }
    }

 

    public void sendmsg() {
        if (!txtFieldMsg.getText().trim().equals("")) {
            Date d = ss.selectDate();
            body = "> " + nomuser + " à " + d + " \n " + txtFieldMsg.getText();
            listviewChat.getItems().add(body);
            message = new Message(txtFieldMsg.getText(), d, idconv, iduser);

            txtFieldMsg.clear();

            TrayNotification tray = new TrayNotification("Sucsess", "Message envoyé ", SUCCESS);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(2));
            ms.ajoutermsg(message);

        }
    }

    public void addimage(ActionEvent event) throws IOException {
        Date dd = ss.selectDate();
        String bdy = " send image!! vous le trouver a dossier\n destination du serveur web";
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("images", "*.png", "*.jpg"));
        List<File> selctedFiles = fc.showOpenMultipleDialog(null);

        if (selctedFiles != null) {
            for (int i = 0; i < selctedFiles.size(); i++) {
                try {
                    img = selctedFiles.get(i).getAbsolutePath();
                    listviewChat.getItems().add(img);
                    UUID u = UUID.randomUUID();
                    String old = img;
                    String extension = img.substring(img.lastIndexOf("."));
                    img = img.substring(img.lastIndexOf("\\") + 1, img.lastIndexOf("."));
                    img = img + u.toString() + extension;
                    String requete3 = "insert into message (conversation_id,message,image,created_at,user_id) values('" + idconv + "','" + bdy + "','" + img + "','" + dd + "','" + iduser + "')";
                    Statement st3 = cnx2.createStatement();
                    st3.executeUpdate(requete3);
                    File source = new File(old);
                    File dest = new File("C:\\wamp64\\www\\pi\\pi\\web\\conversation\\images\\" + img);

                    Copy.copyFileUsingStream(source, dest);
                    TrayNotification tray = new TrayNotification("Sucsess", "Image envoyé et placé au dossier destination :\nC:\\wamp64\\www\\pi\\pi\\web\\conversation\\images\\ ", SUCCESS);
                    tray.setAnimationType(AnimationType.POPUP);
                    tray.showAndDismiss(Duration.seconds(2));
                    System.out.println("ajout image good");

                } catch (SQLException ex) {
                    Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else {
            System.out.println("file is not valid");
        }
    }

}
