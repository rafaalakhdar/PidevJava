// 
// Decompiled by Procyon v0.5.36
// 

package javafxloginn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import java.net.URISyntaxException;
import javafx.scene.media.Media;
import java.util.ResourceBundle;
import java.net.URL;
import entities.User;
import java.io.IOException;
import javafx.scene.control.Alert;
import services.ServiceNotification;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.scene.Node;
import org.controlsfx.control.Notifications;
import utilitez.SHA;
import javafx.event.ActionEvent;
import javafx.scene.media.MediaPlayer;
import services.UserService;
import java.sql.ResultSet;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.media.MediaView;
import javafx.scene.layout.Region;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;

public class FXMLDocumentController implements Initializable
{
    @FXML
    private Label status;
    @FXML
    private TextField user;
    @FXML
    private TextField pass;
    @FXML
    private Hyperlink linkCreateAccount;
    @FXML
    private Region region;
    @FXML
    private MediaView mediaView;
    @FXML
    private ProgressIndicator indicator;
    @FXML
    private Label connectingLabel;
    private static int loggedId;
    private static String loggedMail;
    ResultSet rs;
    private final String adminpass = "0c02cad8b24ed02448abbbd71ee51cb0166e641b3e557353829fd517803a8f5c2f8d6a8ce15aa280ec1b2b1f8f915a607ab0bdadfab3116ee580a50a4ec30850";
    UserService us;
    String email;
    String pwd;
    public static MediaPlayer mediaPlayer;
    
    public FXMLDocumentController() {
        this.rs = null;
        this.us = new UserService();
    }
    
    public static int getLoggedId() {
        return FXMLDocumentController.loggedId;
    }
    
    public static String getLoggedMail() {
        return FXMLDocumentController.loggedMail;
    }
    
    void setMaill(final String email) {
        this.user.setText(email);
        this.user.setEditable(false);
    }
    
    public void Login(final ActionEvent event) throws Exception {
        final UserService us = new UserService();
        this.indicator.setVisible(true);
        if (this.validateFields() & this.validateEmaill()) {
            this.email = this.user.getText();
            this.pwd = SHA.encrypt(this.pass.getText());
            if (us.ckeckenabled(this.email) == 0) {
                final Notifications n = Notifications.create().title("Erreur").text("Compte d\u00e9sactiv\u00e9\n use another compte").graphic((Node)null).position(Pos.TOP_CENTER).hideAfter(Duration.seconds(5.0));
                n.showWarning();
                this.user.clear();
                this.pass.clear();
                this.indicator.setVisible(false);
            }
            else {
                final boolean test = us.connect(this.email, this.pwd);
                try {
                    if (test) {
                        this.indicator.setVisible(false);
                        if (this.pwd.equals("0c02cad8b24ed02448abbbd71ee51cb0166e641b3e557353829fd517803a8f5c2f8d6a8ce15aa280ec1b2b1f8f915a607ab0bdadfab3116ee580a50a4ec30850")) {
                            final User e1 = us.readByUsername(this.user.getText());
                            FXMLDocumentController.loggedId = e1.getId();
                            FXMLDocumentController.loggedMail = e1.getEmail();
                            this.status.setText("login success");
                            final Stage stage = new Stage();
                            final FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("TGT.fxml"));
                            fxmlLoader.getNamespace().put("labelText", this.user.getText());
                            final Parent root = (Parent)fxmlLoader.load();
                            stage.setTitle("ADMIN Tunisain Got Talent");
                            final Scene scene = new Scene(root, 1260.0, 550.0);
                            stage.setScene(scene);
                            stage.show();
                            ((Node)event.getSource()).getScene().getWindow().hide();
                            ServiceNotification.showNotif("Bienvenu Admin", " Tunisian Got Talent ");
                        }
                        this.status.setText("login success");
                        final Stage stage2 = new Stage();
                        final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("menubar.fxml"));
                        final Parent root2 = (Parent)loader.load();
                        stage2.setTitle("Tunisain Got Talent");
                        final Scene scene2 = new Scene(root2, 1280.0, 720.0);
                        final MenubarController mbc = (MenubarController)loader.getController();
                        mbc.setMail(this.email);
                        stage2.setScene(scene2);
                        stage2.show();
                        ((Node)event.getSource()).getScene().getWindow().hide();
                        ServiceNotification.showNotif("Welcome ", "Bienvenu dans Tunisian Got Talent ");
                    }
                    else {
                        this.indicator.setVisible(true);
                        this.status.setText("login failed");
                        final Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("login failed");
                        alert.setHeaderText((String)null);
                        alert.setContentText("login ou mot de pass invalide");
                        alert.showAndWait();
                        this.indicator.setVisible(false);
                    }
                    this.user.clear();
                    this.pass.clear();
                }
                catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    
    public void initialize(final URL location, final ResourceBundle resources) {
        Media media = null;
        try {
            media = new Media(this.getClass().getResource("/videos/d.mp4").toURI().toString());
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        (FXMLDocumentController.mediaPlayer = new MediaPlayer(media)).play();
        FXMLDocumentController.mediaPlayer.setCycleCount(-1);
        this.mediaView.setMediaPlayer(FXMLDocumentController.mediaPlayer);
        FXMLDocumentController.mediaPlayer.setOnError(() -> System.out.println("Media error:" + FXMLDocumentController.mediaPlayer.getError().toString()));
        this.region.setStyle("-fx-background-color:rgb(0,0,0,0.7);");
        this.region.visibleProperty().bind((ObservableValue)this.indicator.visibleProperty());
        this.connectingLabel.setStyle("-fx-text-fill:white; -fx-font-size:17px; -fx-font-weight:bold;");
        this.connectingLabel.visibleProperty().bind((ObservableValue)this.indicator.visibleProperty());
    }
    
    @FXML
    private void linkCreatAccountAction(final ActionEvent event) {
        try {
            final Stage stage = new Stage();
            final Parent parent = (Parent)FXMLLoader.load(this.getClass().getResource("SignupScence.fxml"));
            stage.setTitle("Noveau Compte");
            final Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
            ((Node)event.getSource()).getScene().getWindow().hide();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private boolean validateFields() {
        if (this.user.getText().isEmpty() || this.pass.getText().isEmpty()) {
            final Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur Fields");
            alert.setHeaderText((String)null);
            alert.setContentText(" Fields empty");
            alert.showAndWait();
            this.indicator.setVisible(false);
            return false;
        }
        return true;
    }
    
    private boolean validateEmaill() {
        final Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        final Matcher m = p.matcher(this.user.getText());
        if (m.find() && m.group().equals(this.user.getText())) {
            return true;
        }
        final Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur Email");
        alert.setHeaderText((String)null);
        alert.setContentText("invalide Email");
        alert.showAndWait();
        this.indicator.setVisible(false);
        return false;
    }
    
    @FXML
    private void switchMdo(final ActionEvent event) {
        try {
            final Stage stage = new Stage();
            final Parent parent = (Parent)FXMLLoader.load(this.getClass().getResource("changerpass.fxml"));
            stage.setTitle("Noveau Compte");
            final Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
            ((Node)event.getSource()).getScene().getWindow().hide();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
