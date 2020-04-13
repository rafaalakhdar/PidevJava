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
import java.util.Date;
import java.util.List;
import services.MessageService;
import services.UserService;
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
    UserService us =new UserService();
    ServiceSysdate ss = new ServiceSysdate();
    MessageService ms = new MessageService();
    Message message;
    String body="";
    String nomuser;
    Integer idconv;
    Integer iduser;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshmsg();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setmessage(ArrayList<String> l,Integer idc,Integer idu) {
        liste.addAll(l);
        idconv= idc;
        iduser = idu;
        nomuser = us.searchByid(iduser);
        
        

    }

    @FXML
    public void refreshmsg() {
        for (String mg : liste) {
            listviewChat.getItems().add(mg);
        }
    }
    
    public void sendmsg(){
    if (!txtFieldMsg.getText().trim().equals("")) {
        Date d = ss.selectDate();
        body = "> " +nomuser + " : " + txtFieldMsg.getText() + " à " + d;
        listviewChat.getItems().add(body);
        message = new Message(txtFieldMsg.getText(),d,idconv,iduser);
        ms.ajoutermsg(message);
        txtFieldMsg.clear();
    
    }
    }
    

}
