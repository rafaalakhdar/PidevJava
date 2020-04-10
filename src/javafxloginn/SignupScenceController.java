package javafxloginn;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import entities.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.Node;
import javafx.scene.control.TextInputDialog;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import services.ServiceNotification;
import services.ServiceRandomMailConfirmation;
import services.UserService;
import utilitez.Mailing;

import utilitez.MyConnection;
import utilitez.SHA;

/**
 * FXML Controller class
 *
 * @author Merna
 */
public class SignupScenceController implements Initializable {
    ServiceRandomMailConfirmation serviceMail = new ServiceRandomMailConfirmation();
    UserService us = new UserService();

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ComboBox<String> comboboxGender;

    ObservableList<String> genderList = FXCollections.observableArrayList("Female", "Male");

    @FXML
    private ComboBox<String> comboboxCountry;

    ObservableList<String> counrtyList = FXCollections.observableArrayList("Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica",
            "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
            "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam",
            "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China",
            "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire",
            "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador",
            "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana",
            "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam",
            "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland",
            "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea",
            "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg",
            "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania",
            "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia",
            "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands",
            "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion",
            "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe",
            "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa",
            "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic",
            "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan",
            "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan",
            "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe");

    @FXML
    private Button btnSignup;

    @FXML
    public void ajouteruser(ActionEvent event) {
        

        if (validateName() & validateEmaill() & validatePassword() & validateFields()) {

             String code =  serviceMail.generateRandomString();
              System.out.println(code);
              String to = txtEmail.getText();
                String subject = "Confirmation d'inscription";
                String message =  "Bienvenu "+txtUserName.getText()+" From "+comboboxCountry.getValue() +" dans notre application voici votre code de confirmation "+ code + "\n  Veillez saisir votre code pour confirmer votre inscription" ;
                String usermail = "rafaa.lakhdhar@esprit.tn";
                String passmail = "191SMT4905";
                 Mailing.send(to,subject, message, usermail, passmail);
            
            
            
            String username = txtUserName.getText();
            String email = txtEmail.getText();
            String password = txtPassword.getText();
            String gender = comboboxGender.getValue();
            String country = comboboxCountry.getValue();
            String statu = "Offline";
            int enable=1;
            if (verifconfirMail(code)==true){
            try {
                Connection cnx2 = MyConnection.getInstance().getCnx();
                
                String requete = "insert into user (nom,email,password,sexe,pays,status,enabled) values('" + username
                        + "','" + email + "','" + SHA.encrypt(password) + "','"
                        + gender + "','" + country +"','" + statu + "','" + enable + "')";
                Statement st = cnx2.createStatement();
                st.executeUpdate(requete);
                clearFields();
                System.out.println("Personne ajouter");

                Alert alertSuccess = new Alert(AlertType.INFORMATION);
                alertSuccess.setTitle("Signup Successfully");
                alertSuccess.setHeaderText("Signup Successfully");
                alertSuccess.setContentText("Welcome to Our Tunisian Got Talent\nplease back and login");
                alertSuccess.showAndWait();
                
                 try {

            Stage stage = new Stage();
            Parent parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(parent);
                                      stage.setTitle("Tunisain Got Talent");

            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
                    ServiceNotification.showNotif("Bienvenu", "Bienvenu dans Tunisian Got Talent ");

                 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
                
                
                

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }}
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboboxGender.setItems(genderList);
        comboboxCountry.setItems(counrtyList);
        btnSignup.setDefaultButton(true);
    }

    /**
     *
     * @param event
     */
    @FXML
    public void btnBackAction(ActionEvent event) {
        try {

            Stage stage = new Stage();
            Parent parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(parent);
                                      stage.setTitle("Tunisain Got Talent");

            stage.setScene(scene);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    public void mapadresseAction(ActionEvent event) {
        try {

            Stage stage = new Stage();
            Parent parent = FXMLLoader.load(getClass().getResource("Map.fxml"));
            Scene scene = new Scene(parent);
                                      stage.setTitle("Talent Adresse");

            stage.setScene(scene);
            stage.show();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
        public boolean verifconfirMail(String code)
    {
        TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("Confirmez votre inscription");
                dialog.setHeaderText("Un mail vous a été envoyer où vous trouvez le code");
                dialog.setContentText("Entrez votre code de confirmation:");
                Optional<String> result = dialog.showAndWait();
                if (result.get().equals(code)){
                    
                        if (result.get().equals(code))
                        {
                            return true;
                        }
                }
                else
                {
                    return verifconfirMail(code);
                }
                return false;
    }

    private boolean validateName() {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(txtUserName.getText());
        if (m.find() && m.group().equals(txtUserName.getText())) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Erreur Name");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid First Name");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validateEmaill() {
        if (us.searchByEmail(txtEmail.getText())==true)
        {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("duplicate");
            alert.setHeaderText(null);
            alert.setContentText("Email deja utilisé");
            ServiceNotification.showNotif("Alert", "Taper un autre Email ");
                        alert.showAndWait();
                        

            
        }
        if (us.searchByEmail(txtEmail.getText())==false)
        {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(txtEmail.getText());
        if (m.find() && m.group().equals(txtEmail.getText())) {
            return true;
          } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Erreur Email");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Valid Email");
            alert.showAndWait();
}
            return false;
        }
        return false;
    }

    private boolean validatePassword() {
        Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{5,15})");
        Matcher m = p.matcher(txtPassword.getText());
        if (m.matches()) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Erreur Password");
            alert.setHeaderText(null);
            alert.setContentText("Password must contain at least one(Digit, Lowercase, UpperCase and Special Character) and length must be between 5 -15");
            alert.showAndWait();

            return false;
        }
    }

    public void clearFields() {
        txtEmail.clear();
        txtPassword.clear();
        txtUserName.clear();

        comboboxCountry.setValue(null);
        comboboxGender.setValue(null);

    }

    private boolean validateFields() {
        if (txtPassword.getText().isEmpty() || txtEmail.getText().isEmpty() || txtUserName.getText().isEmpty()) {

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Erreur Fields");
            alert.setHeaderText(null);
            alert.setContentText(" Fields empty");
            alert.showAndWait();

            return false;
        }
        if (comboboxCountry.getValue() == null & comboboxGender.getValue() == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("VErreur Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please Selct The sexe / pays");
            alert.showAndWait();

            return false;
        } else {
        }

        return true;
    }
    
    
 @FXML
    private void signInFb(ActionEvent event) {
         String domain = "http://localhost";
        String appId = "270212743978426";
        String appSecret = "a763c3ee6d23d55f098465f876295c21";

        String authUrl = "https://www.facebook.com/dialog/oauth?\n"
                + "                    client_id=270212743978426\n"
                + "                    &redirect_uri=https://www.facebook.com/connect/login_success.html \n"
                + "                    &client_secret=9f4784603794d68b12fc2999e77745b1&response_type=token&scope=email,user_hometown,public_profile";

        System.setProperty("webdriver.chrome.driver", "D:/aaa/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get(authUrl);

        System.out.println(driver.getCurrentUrl());
        String accessToken;

        boolean b = true;
        while (b) {
            if (!driver.getCurrentUrl().contains("facebook.com")) {

                String url = driver.getCurrentUrl();
                accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$1");
                System.out.println("test");
                driver.quit();
                b = false;
                FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.LATEST);
                String fields = "name,first_name,last_name,email,address,picture";
                User user = fbClient.fetchObject("me", User.class, Parameter.with("fields", fields));
                System.out.println(user.toString());
                System.out.println(user.getNom());
                System.out.println(user.toString());

                UserService us = new UserService();
            //    if (us.searchUserByEmailOnly(user.getEmail()) != null) {
             //       User u = us.searchUserByEmailOnly(user.getEmail().toLowerCase());
                    
                    
            //    }

            }

        }

    }
}
