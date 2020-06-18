// 
// Decompiled by Procyon v0.5.36
// 

package javafxloginn;

import java.util.ResourceBundle;
import java.net.URL;
import services.UserService;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import entities.User;
import javafx.fxml.Initializable;

public class UserInfoController implements Initializable
{
    User user;
    @FXML
    private Text txtEmail;
    @FXML
    private Text txtFullName;
    @FXML
    private Text txtCountry;
    @FXML
    private Text txtGender;
    Integer idu;
    
    void send(final Integer id) {
        User user = new User();
        this.idu = id;
        System.out.println(this.idu);
        final UserService us = new UserService();
        user = us.findById(this.idu);
        this.txtFullName.setText(user.getNom());
        this.txtEmail.setText(user.getEmail());
        this.txtCountry.setText(user.getPays());
        this.txtGender.setText(user.getSexe());
    }
    
    public void initialize(final URL url, final ResourceBundle rb) {
    }
}
