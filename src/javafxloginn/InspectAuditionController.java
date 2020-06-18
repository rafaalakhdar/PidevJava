// 
// Decompiled by Procyon v0.5.36
// 

package javafxloginn;

import java.io.IOException;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import java.util.ResourceBundle;
import java.net.URL;
import java.sql.SQLException;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import services.UserService;
import services.AuditionService;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import entities.Audition;
import javafx.fxml.Initializable;

public class InspectAuditionController implements Initializable
{
    private Audition selectedAudition;
    @FXML
    private ImageView auditionImage;
    @FXML
    private Label auditionName;
    @FXML
    private Button btnAccept;
    @FXML
    private Button btnDecline;
    @FXML
    private MediaView auditionVideo;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnVideo;
    private MediaPlayer mediaplayer;
    @FXML
    private Button btnBack;
    @FXML
    private Label userName;
    
    public void initData(final Audition audition) throws SQLException {
        (this.selectedAudition = audition).setChecked(true);
        final AuditionService as = new AuditionService();
        as.modifierAudition(this.selectedAudition);
        this.auditionName.setText(audition.getNom());
        this.userName.setText(new UserService().findById(this.selectedAudition.getUserFk().getId()).getNom());
        final String Vurl = "file:/C:/wamp64/www/PIDEV/web/uploads/files/" + audition.getVideo();
        final Media media = new Media(Vurl);
        this.mediaplayer = new MediaPlayer(media);
        this.auditionVideo.setFitHeight(230.0);
        this.auditionVideo.setFitWidth(450.0);
        this.auditionVideo.setMediaPlayer(this.mediaplayer);
        final Image image = new Image("file:/C:/wamp64/www/PIDEV/web/uploads/files/" + audition.getImage(), 200.0, 170.0, false, false);
        this.auditionImage.setImage(image);
    }
    
    public void initialize(final URL url, final ResourceBundle rb) {
    }
    
    @FXML
    private void on_Click_btnPlay(final ActionEvent event) {
        if (this.mediaplayer.getStatus() == MediaPlayer.Status.PLAYING) {
            this.mediaplayer.pause();
            this.mediaplayer.play();
        }
        this.mediaplayer.play();
    }
    
    @FXML
    private void on_Click_btnPause(final ActionEvent event) {
        this.mediaplayer.pause();
    }
    
    @FXML
    private void on_Click_btnBack(final ActionEvent event) throws IOException {
        final Parent tvp = (Parent)FXMLLoader.load(this.getClass().getResource("GestionAudition.fxml"));
        final Scene tvs = new Scene(tvp);
        final Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tvs);
        window.show();
    }
    
    @FXML
    private void on_Click_btnAccept(final ActionEvent event) throws SQLException {
        this.selectedAudition.setQualified(true);
        final AuditionService as = new AuditionService();
        as.modifierAudition(this.selectedAudition);
    }
    
    @FXML
    private void on_Click_btnDecline(final ActionEvent event) throws SQLException {
        this.selectedAudition.setQualified(false);
        final AuditionService as = new AuditionService();
        as.modifierAudition(this.selectedAudition);
    }
}
