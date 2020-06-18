// 
// Decompiled by Procyon v0.5.36
// 

package javafxloginn;

import com.restfb.FacebookClient;
import org.openqa.selenium.WebDriver;
import com.restfb.Parameter;
import com.restfb.DefaultFacebookClient;
import com.restfb.Version;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Optional;
import javafx.scene.control.TextInputDialog;
import java.util.ResourceBundle;
import java.net.URL;
import java.io.IOException;
import services.ServiceNotification;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import utilitez.SHA;
import entities.User;
import utilitez.Mailing;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import services.UserService;
import services.ServiceRandomMailConfirmation;
import javafx.fxml.Initializable;

public class SignupScenceController implements Initializable
{
    ServiceRandomMailConfirmation serviceMail;
    UserService us;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ComboBox<String> comboboxGender;
    ObservableList<String> genderList;
    @FXML
    private ComboBox<String> comboboxCountry;
    ObservableList<String> counrtyList;
    @FXML
    private Button btnSignup;
    
    public SignupScenceController() {
        this.serviceMail = new ServiceRandomMailConfirmation();
        this.us = new UserService();
        this.genderList = FXCollections.observableArrayList(new String[] { "Female", "Male" });
        this.counrtyList = FXCollections.observableArrayList(new String[] { "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe" });
    }
    
    @FXML
    public void ajouteruser(final ActionEvent event) {
        if (this.validateName() & this.validateEmaill() & this.validatePassword() & this.validateFields()) {
            final String code = this.serviceMail.generateRandomString();
            System.out.println(code);
            final String to = this.txtEmail.getText();
            final String subject = "Confirmation d'inscription";
            final String message = "Bienvenu " + this.txtUserName.getText() + " From " + (String)this.comboboxCountry.getValue() + " dans notre application voici votre code de confirmation " + code + "\n  Veillez saisir votre code pour confirmer votre inscription";
            final String usermail = "rafaa.lakhdhar@esprit.tn";
            final String passmail = "191SMT4905";
            Mailing.send(to, subject, message, usermail, passmail);
            final String username = this.txtUserName.getText();
            final String email = this.txtEmail.getText();
            final String password = this.txtPassword.getText();
            final String gender = (String)this.comboboxGender.getValue();
            final String country = (String)this.comboboxCountry.getValue();
            final String statu = "Offline";
            final int enable = 1;
            if (this.verifconfirMail(code)) {
                final User u = new User();
                u.setNom(username);
                u.setEmail(email);
                u.setPassword(SHA.encrypt(password));
                u.setSexe(gender);
                u.setPays(country);
                u.setStatus(statu);
                u.setEnabled(null);
                final UserService ps = new UserService();
                ps.ajouter(u);
                this.clearFields();
                System.out.println("Personne ajouter");
                final Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                alertSuccess.setTitle("Signup Successfully");
                alertSuccess.setHeaderText("Signup Successfully");
                alertSuccess.setContentText("Welcome to Our Tunisian Got Talent\nplease back and login");
                alertSuccess.showAndWait();
                try {
                    final Stage stage = new Stage();
                    final Parent parent = (Parent)FXMLLoader.load(this.getClass().getResource("FXMLDocument.fxml"));
                    final Scene scene = new Scene(parent);
                    stage.setTitle("Tunisain Got Talent");
                    stage.setScene(scene);
                    stage.show();
                    ((Node)event.getSource()).getScene().getWindow().hide();
                    ServiceNotification.showNotif("Bienvenu", "Bienvenu dans Tunisian Got Talent ");
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public void initialize(final URL url, final ResourceBundle rb) {
        this.comboboxGender.setItems((ObservableList)this.genderList);
        this.comboboxCountry.setItems((ObservableList)this.counrtyList);
        this.btnSignup.setDefaultButton(true);
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
    
    @FXML
    public void mapadresseAction(final ActionEvent event) {
        try {
            final Stage stage = new Stage();
            final Parent parent = (Parent)FXMLLoader.load(this.getClass().getResource("Map.fxml"));
            final Scene scene = new Scene(parent);
            stage.setTitle("Talent Adresse");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean verifconfirMail(final String code) {
        final TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Confirmez votre inscription");
        dialog.setHeaderText("Un mail vous a \u00e9t\u00e9 envoyer o\u00f9 vous trouvez le code");
        dialog.setContentText("Entrez votre code de confirmation:");
        final Optional<String> result = (Optional<String>)dialog.showAndWait();
        if (result.get().equals(code)) {
            return result.get().equals(code);
        }
        return this.verifconfirMail(code);
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
        if (this.us.searchByEmail(this.txtEmail.getText())) {
            final Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("duplicate");
            alert.setHeaderText((String)null);
            alert.setContentText("Email deja utilis\u00e9");
            ServiceNotification.showNotif("Alert", "Taper un autre Email ");
            alert.showAndWait();
        }
        if (this.us.searchByEmail(this.txtEmail.getText())) {
            return false;
        }
        final Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        final Matcher m = p.matcher(this.txtEmail.getText());
        if (m.find() && m.group().equals(this.txtEmail.getText())) {
            return true;
        }
        final Alert alert2 = new Alert(Alert.AlertType.WARNING);
        alert2.setTitle("Erreur Email");
        alert2.setHeaderText((String)null);
        alert2.setContentText("Please Enter Valid Email");
        alert2.showAndWait();
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
        this.comboboxCountry.setValue(null);
        this.comboboxGender.setValue(null);
    }
    
    private boolean validateFields() {
        if (this.txtPassword.getText().isEmpty() || this.txtEmail.getText().isEmpty() || this.txtUserName.getText().isEmpty()) {
            final Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur Fields");
            alert.setHeaderText((String)null);
            alert.setContentText(" Fields empty");
            alert.showAndWait();
            return false;
        }
        if (this.comboboxCountry.getValue() == null & this.comboboxGender.getValue() == null) {
            final Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("VErreur Fields");
            alert.setHeaderText((String)null);
            alert.setContentText("Please Select The sexe / pays");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    
    @FXML
    private void signInFb(final ActionEvent event) {
        final String domain = "http://localhost";
        final String appId = "270212743978426";
        final String appSecret = "a763c3ee6d23d55f098465f876295c21";
        final String authUrl = "https://www.facebook.com/dialog/oauth?\n                    client_id=270212743978426\n                    &redirect_uri=https://www.facebook.com/connect/login_success.html \n                    &client_secret=9f4784603794d68b12fc2999e77745b1&response_type=token&scope=email,user_hometown,public_profile";
        System.setProperty("webdriver.chrome.driver", "D:/aaa/chromedriver.exe");
        final WebDriver driver = (WebDriver)new ChromeDriver();
        driver.get(authUrl);
        System.out.println(driver.getCurrentUrl());
        boolean b = true;
        while (b) {
            if (!driver.getCurrentUrl().contains("facebook.com")) {
                final String url = driver.getCurrentUrl();
                final String accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$1");
                System.out.println("test");
                driver.quit();
                b = false;
                final FacebookClient fbClient = (FacebookClient)new DefaultFacebookClient(accessToken, Version.LATEST);
                final String fields = "name,first_name,last_name,email,address,picture";
                final User user = (User)fbClient.fetchObject("me", (Class)User.class, new Parameter[] { Parameter.with("fields", (Object)fields) });
                System.out.println(user.toString());
                System.out.println(user.getNom());
                System.out.println(user.toString());
                final UserService userService = new UserService();
            }
        }
    }
}
