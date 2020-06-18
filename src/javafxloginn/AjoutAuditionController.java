// 
// Decompiled by Procyon v0.5.36
// 

package javafxloginn;

import java.io.IOException;
import services.AuditionService;
import entities.User;
import entities.Categorie;
import entities.Audition;
import java.io.File;
import javafx.stage.Window;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.Collection;
import javafx.collections.FXCollections;
import services.CategorieService;
import javafx.scene.control.Button;
import javafx.collections.ObservableList;
import java.util.List;
import entities.keyValuePair;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;

public class AjoutAuditionController implements Initializable
{
    @FXML
    private TextField nomAudition;
    @FXML
    private TextArea descAudition;
    @FXML
    private ChoiceBox<keyValuePair> categorieAudition;
    List<keyValuePair> pair;
    ObservableList<keyValuePair> juryList;
    @FXML
    private Button btnAjoutAudition;
    @FXML
    private Button btnImage;
    @FXML
    private Button btnVideo;
    private String img;
    private String vod;
    
    public AjoutAuditionController() {
        this.pair = new CategorieService().getCategorieNomId();
        this.juryList = (ObservableList<keyValuePair>)FXCollections.observableArrayList((Collection)this.pair);
    }
    
    public void initialize(final URL url, final ResourceBundle rb) {
        this.categorieAudition.getItems().addAll((Collection)this.pair);
    }
    
    @FXML
    private void getImageAction(final ActionEvent event) {
        if (event.getSource().equals(this.btnImage)) {
            final FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter[] { new FileChooser.ExtensionFilter("JPG", new String[] { "*.jpg" }), new FileChooser.ExtensionFilter("PNG", new String[] { "*.png" }) });
            final File selectedImage = fc.showOpenDialog((Window)null);
            if (selectedImage != null) {
                this.img = selectedImage.getAbsolutePath();
            }
        }
    }
    
    @FXML
    private void getVideoAction(final ActionEvent event) {
        if (event.getSource().equals(this.btnVideo)) {
            final FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter[] { new FileChooser.ExtensionFilter("mp4", new String[] { "*.mp4" }) });
            final File selectedVideo = fc.showOpenDialog((Window)null);
            if (selectedVideo != null) {
                this.vod = selectedVideo.getAbsolutePath();
            }
        }
    }
    
    @FXML
    private void handleButtonAction(final ActionEvent event) throws IOException {
        if (event.getSource().equals(this.btnAjoutAudition)) {
            final String nom = this.nomAudition.getText();
            final String description = this.descAudition.getText();
            final String Image = this.img;
            final String Video = this.vod;
            final int id = ((keyValuePair)this.categorieAudition.getValue()).getKey();
            final Audition aud = new Audition();
            aud.setNom(nom);
            aud.setDescription(description);
            aud.setImage(Image);
            aud.setVideo(Video);
            aud.setCategorieFk(new Categorie(id));
            aud.setUserFk(new User(FXMLDocumentController.getLoggedId()));
            final AuditionService as = new AuditionService();
            as.ajouterAudition(aud);
        }
    }
}
