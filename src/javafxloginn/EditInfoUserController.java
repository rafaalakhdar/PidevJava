// 
// Decompiled by Procyon v0.5.36
// 

package javafxloginn;

import java.util.regex.Matcher;
import javafx.scene.control.Alert;
import java.util.regex.Pattern;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.ServiceNotification;
import utilitez.MyConnection;
import javafx.event.ActionEvent;
import java.util.ResourceBundle;
import java.net.URL;
import services.UserService;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;

public class EditInfoUserController implements Initializable
{
    @FXML
    private Label lblmail;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtSexe;
    @FXML
    private TextField txtPays;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button back;
    @FXML
    private Button update;
    private List<String> liste;
    
    public List<String> getListe() {
        return this.liste;
    }
    
    public void setListe(final List<String> liste) {
        this.liste = liste;
    }
    
    void send(final String email) {
        this.lblmail.setText(email);
        this.txtEmail.setText(this.lblmail.getText());
        this.liste = UserService.getBymail(this.lblmail.getText());
        this.lblmail.setVisible(false);
        this.txtUserName.setText((String)this.liste.get(0));
        this.txtSexe.setText((String)this.liste.get(2));
        this.txtPays.setText((String)this.liste.get(3));
        this.txtPassword.setText((String)this.liste.get(1));
        this.txtPassword.setEditable(false);
    }
    
    public void initialize(final URL url, final ResourceBundle rb) {
    }
    
    public void updateuser(final ActionEvent event) {
        try {
            final PreparedStatement pt = MyConnection.getInstance().getCnx().prepareStatement("update user set nom=?,email=? ,sexe=? ,pays=?  where email='" + this.lblmail.getText() + "' ");
            pt.setString(1, this.txtUserName.getText());
            pt.setString(2, this.txtEmail.getText());
            pt.setString(3, this.txtSexe.getText());
            pt.setString(4, this.txtPays.getText());
            pt.executeUpdate();
            this.btnBackAction(event);
            ServiceNotification.showNotif("Success ", this.txtUserName.getText() + " Updated !!\nLogin with New Informations");
        }
        catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void btnBackAction(final ActionEvent event) {
        try {
            final Stage stage = new Stage();
            final Parent parent = (Parent)FXMLLoader.load(this.getClass().getResource("FXMLDocument.fxml"));
            final Scene scene = new Scene(parent);
            stage.setTitle("Tunisain Got Talent");
            stage.setScene(scene);
            stage.show();
            ((Node)event.getSource()).getScene().getWindow().hide();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private boolean validateName() {
        final Pattern p = Pattern.compile("[a-zA-Z]+");
        final Matcher m = p.matcher(this.txtUserName.getText());
        if (m.find() && m.group().equals(this.txtUserName.getText())) {
            return true;
        }
        final Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur Name");
        alert.setHeaderText((String)null);
        alert.setContentText("Please Enter Valid First Name");
        alert.showAndWait();
        return false;
    }
    
    private boolean validateEmaill() {
        final Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        final Matcher m = p.matcher(this.txtEmail.getText());
        if (m.find() && m.group().equals(this.txtEmail.getText())) {
            return true;
        }
        final Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur Email");
        alert.setHeaderText((String)null);
        alert.setContentText("Please Enter Valid Email");
        alert.showAndWait();
        return false;
    }
    
    private boolean validatePassword() {
        final Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{5,15})");
        final Matcher m = p.matcher(this.txtPassword.getText());
        if (m.matches()) {
            return true;
        }
        final Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur Password");
        alert.setHeaderText((String)null);
        alert.setContentText("Password must contain at least one(Digit, Lowercase, UpperCase and Special Character) and length must be between 5 -15");
        alert.showAndWait();
        return false;
    }
    
    public void clearFields() {
        this.txtEmail.clear();
        this.txtPassword.clear();
        this.txtUserName.clear();
        this.txtSexe.clear();
        this.txtPays.clear();
    }
    
    private boolean validateFields() {
        if (this.txtPassword.getText().isEmpty() || this.txtEmail.getText().isEmpty() || this.txtUserName.getText().isEmpty() || this.txtSexe.getText().isEmpty() || this.txtPays.getText().isEmpty()) {
            final Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur Fields");
            alert.setHeaderText((String)null);
            alert.setContentText(" Fields empty");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
