// 
// Decompiled by Procyon v0.5.36
// 

package javafxloginn;

import javafx.beans.value.ObservableValue;
import java.io.IOException;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.util.Optional;
import java.sql.SQLException;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Collection;
import services.AuditionService;
import services.CategorieService;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.collections.FXCollections;
import java.util.List;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.chart.PieChart;
import entities.User;
import entities.Categorie;
import java.sql.Date;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import entities.Audition;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class GestionAuditionController implements Initializable
{
    @FXML
    private Button afficheAuditionBtn;
    @FXML
    private Button statBtn;
    @FXML
    private TextField rechercheTextField;
    @FXML
    private Button inspectBtn;
    @FXML
    private Button supprimeBtn;
    public ObservableList<Audition> list;
    @FXML
    private TableView<Audition> listeAudition;
    @FXML
    private TableColumn<Audition, Integer> idAudition;
    @FXML
    private TableColumn<Audition, String> nomAudition;
    @FXML
    private TableColumn<Audition, Date> dateAudition;
    @FXML
    private TableColumn<Audition, Categorie> catAudition;
    @FXML
    private TableColumn<Audition, User> userAudition;
    @FXML
    private PieChart pieChartAudition;
    @FXML
    private Pane paneStat;
    @FXML
    private Pane paneAffiche;
    @FXML
    private RadioButton checkRadio;
    @FXML
    private RadioButton declineRadio;
    @FXML
    private Button refreshBtn;
    List<Categorie> listeCategorie;
    @FXML
    private Label countAll;
    @FXML
    private Label pourcentageC;
    @FXML
    private Label countUnchecked;
    
    public GestionAuditionController() {
        this.list = FXCollections.observableArrayList();
    }
    
    public void initialize(final URL url, final ResourceBundle rb) {
        this.listeAuditions();
        int countAllAudition = new AuditionService().countAllAudition();
        countAll.setText(countAllAudition+"");
        int countUncheck = new AuditionService().countAllUncheckedAudition();
        float pUnchecked = (100*((float)countUncheck/countAllAudition));
        this.countUnchecked.setText("("+countUncheck+")");
        pourcentageC.setText(pUnchecked+"%");
        this.listeCategorie = new ArrayList<Categorie>();
        this.listeCategorie = new CategorieService().afficherCategorie();
        for (int i = 0; i < this.listeCategorie.size(); ++i) {
            PieChart.Data slice1 = null;
            slice1 = new PieChart.Data(this.listeCategorie.get(i).getNom(), (double)new AuditionService().countAudition(this.listeCategorie.get(i)));
            this.pieChartAudition.getData().add(slice1);
        }
    }
    
    public void listeAuditions() {
        this.list = (ObservableList<Audition>)FXCollections.observableArrayList((Collection)new AuditionService().afficherTout());
        this.listeAudition.setEditable(true);
        this.idAudition.setCellValueFactory((Callback)new PropertyValueFactory("id"));
        this.nomAudition.setCellValueFactory((Callback)new PropertyValueFactory("nom"));
        this.dateAudition.setCellValueFactory((Callback)new PropertyValueFactory("date"));
        this.catAudition.setCellValueFactory((Callback)new PropertyValueFactory("categorieFk"));
        this.userAudition.setCellValueFactory((Callback)new PropertyValueFactory("userFk"));
        this.listeAudition.setItems((ObservableList)this.list);
        final ObservableList data = this.listeAudition.getItems();
        this.rechercheTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && newValue.length() < oldValue.length()) {
                this.listeAudition.setItems(data);
            }
            final String value = newValue.toLowerCase();
            final ObservableList<Audition> subentries = FXCollections.observableArrayList();
            final long count = this.listeAudition.getColumns().stream().count();
            for (int i = 0; i < this.listeAudition.getItems().size(); ++i) {
                for (int j = 0; j < count; ++j) {
                    final String entry = "" + ((TableColumn)this.listeAudition.getColumns().get(j)).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(this.listeAudition.getItems().get(i));
                        break;
                    }
                }
            }
            this.listeAudition.setItems((ObservableList)subentries);
        });
    }
    
    public void listeAuditionsUnchecked() {
        this.list = (ObservableList<Audition>)FXCollections.observableArrayList((Collection)new AuditionService().afficherUnchecked());
        this.listeAudition.setEditable(true);
        this.idAudition.setCellValueFactory((Callback)new PropertyValueFactory("id"));
        this.nomAudition.setCellValueFactory((Callback)new PropertyValueFactory("nom"));
        this.dateAudition.setCellValueFactory((Callback)new PropertyValueFactory("date"));
        this.catAudition.setCellValueFactory((Callback)new PropertyValueFactory("categorieFk"));
        this.userAudition.setCellValueFactory((Callback)new PropertyValueFactory("userFk"));
        this.listeAudition.setItems((ObservableList)this.list);
        final ObservableList data = this.listeAudition.getItems();
        this.rechercheTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && newValue.length() < oldValue.length()) {
                this.listeAudition.setItems(data);
            }
            final String value = newValue.toLowerCase();
            final ObservableList<Audition> subentries = FXCollections.observableArrayList();
            final long count = this.listeAudition.getColumns().stream().count();
            for (int i = 0; i < this.listeAudition.getItems().size(); ++i) {
                for (int j = 0; j < count; ++j) {
                    final String entry = "" + ((TableColumn)this.listeAudition.getColumns().get(j)).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(this.listeAudition.getItems().get(i));
                        break;
                    }
                }
            }
            this.listeAudition.setItems((ObservableList)subentries);
        });
    }
    
    public void listeAuditionsUnqualified() {
        this.list = (ObservableList<Audition>)FXCollections.observableArrayList((Collection)new AuditionService().afficherUnqualified());
        this.listeAudition.setEditable(true);
        this.idAudition.setCellValueFactory((Callback)new PropertyValueFactory("id"));
        this.nomAudition.setCellValueFactory((Callback)new PropertyValueFactory("nom"));
        this.dateAudition.setCellValueFactory((Callback)new PropertyValueFactory("date"));
        this.catAudition.setCellValueFactory((Callback)new PropertyValueFactory("categorieFk"));
        this.userAudition.setCellValueFactory((Callback)new PropertyValueFactory("userFk"));
        this.listeAudition.setItems((ObservableList)this.list);
        final ObservableList data = this.listeAudition.getItems();
        this.rechercheTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && newValue.length() < oldValue.length()) {
                this.listeAudition.setItems(data);
            }
            final String value = newValue.toLowerCase();
            final ObservableList<Audition> subentries = FXCollections.observableArrayList();
            final long count = this.listeAudition.getColumns().stream().count();
            for (int i = 0; i < this.listeAudition.getItems().size(); ++i) {
                for (int j = 0; j < count; ++j) {
                    final String entry = "" + ((TableColumn)this.listeAudition.getColumns().get(j)).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(this.listeAudition.getItems().get(i));
                        break;
                    }
                }
            }
            this.listeAudition.setItems((ObservableList)subentries);
        });
    }
    
    public void listeAuditionsUncheckedUnqualified() {
        this.list = (ObservableList<Audition>)FXCollections.observableArrayList((Collection)new AuditionService().afficherUncheckedUnqualified());
        this.listeAudition.setEditable(true);
        this.idAudition.setCellValueFactory((Callback)new PropertyValueFactory("id"));
        this.nomAudition.setCellValueFactory((Callback)new PropertyValueFactory("nom"));
        this.dateAudition.setCellValueFactory((Callback)new PropertyValueFactory("date"));
        this.catAudition.setCellValueFactory((Callback)new PropertyValueFactory("categorieFk"));
        this.userAudition.setCellValueFactory((Callback)new PropertyValueFactory("userFk"));
        this.listeAudition.setItems((ObservableList)this.list);
        final ObservableList data = this.listeAudition.getItems();
        this.rechercheTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && newValue.length() < oldValue.length()) {
                this.listeAudition.setItems(data);
            }
            final String value = newValue.toLowerCase();
            final ObservableList<Audition> subentries = FXCollections.observableArrayList();
            final long count = this.listeAudition.getColumns().stream().count();
            for (int i = 0; i < this.listeAudition.getItems().size(); ++i) {
                for (int j = 0; j < count; ++j) {
                    final String entry = "" + ((TableColumn)this.listeAudition.getColumns().get(j)).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(this.listeAudition.getItems().get(i));
                        break;
                    }
                }
            }
            this.listeAudition.setItems((ObservableList)subentries);
        });
    }
    
    @FXML
    private void handleBtnAction(final ActionEvent event) throws SQLException {
        if (event.getSource().equals(this.supprimeBtn)) {
            try {
                final Audition cat = (Audition)this.listeAudition.getSelectionModel().getSelectedItem();
                final AuditionService pc = new AuditionService();
                final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText((String)null);
                alert.setContentText("Are you sure to delete?");
                final Optional<ButtonType> action = (Optional<ButtonType>)alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    pc.supprimerAudition(cat);
                }
            }
            catch (SQLException e1) {
                System.err.println(e1);
                final Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setHeaderText((String)null);
                alert2.setContentText("erreur");
                alert2.showAndWait();
            }
            this.clearFields();
        }
        else if (event.getSource().equals(this.afficheAuditionBtn)) {
            this.paneAffiche.toFront();
            this.paneStat.toBack();
        }
        else if (event.getSource().equals(this.statBtn)) {
            this.paneAffiche.toBack();
            this.paneStat.toFront();
        }
        else if (event.getSource().equals(this.refreshBtn)) {
            this.listeCategorie = new CategorieService().afficherCategorie();
            if (this.declineRadio.isSelected()) {
                this.listeAuditionsUnqualified();
                List<Categorie> listeCategorie = new ArrayList<Categorie>();
                listeCategorie = new CategorieService().afficherCategorie();
            }
            else if (this.checkRadio.isSelected()) {
                this.listeAuditionsUnchecked();
                List<Categorie> listeCategorie = new ArrayList<Categorie>();
                listeCategorie = new CategorieService().afficherCategorie();
            }
            else if (this.checkRadio.isSelected() && this.declineRadio.isSelected()) {
                this.listeAuditionsUncheckedUnqualified();
                List<Categorie> listeCategorie = new ArrayList<Categorie>();
                listeCategorie = new CategorieService().afficherCategorie();
            }
            else {
                this.listeAuditions();
                List<Categorie> listeCategorie = new ArrayList<Categorie>();
                listeCategorie = new CategorieService().afficherCategorie();
            }
        }
    }
    
    private void clearFields() {
        this.listeAuditions();
    }
    
    @FXML
    private void on_Click_inspectBtn(final ActionEvent event) throws IOException, SQLException {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("inspectAudition.fxml"));
        final Parent tableViewParent = (Parent)loader.load();
        final Scene tableViewScene = new Scene(tableViewParent);
        final InspectAuditionController controller = (InspectAuditionController)loader.getController();
        controller.initData((Audition)this.listeAudition.getSelectionModel().getSelectedItem());
        final Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
    
    private void and(final boolean selected) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
