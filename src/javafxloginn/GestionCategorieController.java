// 
// Decompiled by Procyon v0.5.36
// 

package javafxloginn;

import javafx.beans.value.ObservableValue;
import java.util.Optional;
import java.sql.SQLException;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.Collection;
import services.CategorieService;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import java.util.List;
import entities.keyValuePair;
import javafx.scene.control.ComboBox;
import entities.User;
import java.sql.Date;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import entities.Categorie;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.fxml.Initializable;

public class GestionCategorieController implements Initializable
{
    private Circle exitGestionCategorie;
    @FXML
    private Button btnAjoutCat;
    @FXML
    private Button bntAffichCat;
    @FXML
    private TextField nomCategorie;
    @FXML
    private Button btnAjouterCategorie;
    @FXML
    private Pane paneAfficheCategorie;
    @FXML
    private Pane paneAjoutCategorie;
    public ObservableList<Categorie> list;
    @FXML
    private TableView<Categorie> listeCategorie;
    @FXML
    private TableColumn<Categorie, Integer> idCat;
    @FXML
    private TableColumn<Categorie, String> nomCat;
    @FXML
    private TableColumn<Categorie, Date> dateCat;
    @FXML
    private TableColumn<Categorie, User> idRespensable;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<keyValuePair> respensableCat;
    List<keyValuePair> pair;
    ObservableList<keyValuePair> juryList;
    @FXML
    private Button btnSupprimerCategorie;
    @FXML
    private Label actionLabel;
    @FXML
    private Button modifierCat;
    private int add;
    
    public void setAdd(final int add) {
        this.add = add;
    }
    
    public int getAdd() {
        return this.add;
    }
    
    public GestionCategorieController() {
        this.list = FXCollections.observableArrayList();
        this.pair = new CategorieService().getUserNomId();
        this.juryList = (ObservableList<keyValuePair>)FXCollections.observableArrayList((Collection)this.pair);
        this.add = 0;
        this.respensableCat = (ComboBox<keyValuePair>)new ComboBox();
    }
    
    public void initialize(final URL url, final ResourceBundle rb) {
        this.respensableCat.getItems().addAll((Collection)this.pair);
        this.listeCategories();
    }
    
    public void listeCategories() {
        this.list = (ObservableList<Categorie>)FXCollections.observableArrayList((Collection)new CategorieService().afficherCategorie());
        this.listeCategorie.setEditable(true);
        this.idCat.setCellValueFactory((Callback)new PropertyValueFactory("id"));
        this.nomCat.setCellValueFactory((Callback)new PropertyValueFactory("nom"));
        this.dateCat.setCellValueFactory((Callback)new PropertyValueFactory("date"));
        this.idRespensable.setCellValueFactory((Callback)new PropertyValueFactory("userId"));
        this.listeCategorie.setItems((ObservableList)this.list);
        final ObservableList data = this.listeCategorie.getItems();
        this.searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && newValue.length() < oldValue.length()) {
                this.listeCategorie.setItems(data);
            }
            final String value = newValue.toLowerCase();
            final ObservableList<Categorie> subentries = FXCollections.observableArrayList();
            final long count = this.listeCategorie.getColumns().stream().count();
            for (int i = 0; i < this.listeCategorie.getItems().size(); ++i) {
                for (int j = 0; j < count; ++j) {
                    final String entry = "" + ((TableColumn)this.listeCategorie.getColumns().get(j)).getCellData(i);
                    if (entry.toLowerCase().contains(value)) {
                        subentries.add(this.listeCategorie.getItems().get(i));
                        break;
                    }
                }
            }
            this.listeCategorie.setItems((ObservableList)subentries);
        });
    }
    
    private void handleMouseEvent(final MouseEvent event) {
        if (event.getSource() == this.exitGestionCategorie) {
            System.exit(0);
        }
    }
    
    private int userId(final String x) {
        for (int i = 0; i < this.pair.size(); ++i) {
            if (x == null) {
                if (this.pair.get(i).toString() == null) {
                    return i;
                }
            }
            else if (x.equals(this.pair.get(i).toString())) {
                return i;
            }
        }
        return 0;
    }
    
    @FXML
    private void handleButtonAction(final ActionEvent event) throws SQLException {
        if (event.getSource().equals(this.btnAjoutCat)) {
            this.clearFields();
            this.setAdd(0);
            this.actionLabel.setText("Ajouter une nouvelle catégorie");
            this.btnAjouterCategorie.setText("Ajouter");
            this.paneAjoutCategorie.toFront();
            this.paneAfficheCategorie.toBack();
        }
        else if (event.getSource().equals(this.bntAffichCat)) {
            this.paneAfficheCategorie.toFront();
            this.paneAjoutCategorie.toBack();
        }
        else if (event.getSource().equals(this.btnAjouterCategorie)) {
            if (this.getAdd() == 0) {
                int nameExist = 0;
                final List<String> names = new CategorieService().getCategorieNames();
                final String nom = this.nomCategorie.getText();
                for (int i = 0; i < names.size(); ++i) {
                    if (nom == null) {
                        if (names.get(i) != null) {
                            continue;
                        }
                    }
                    else if (!nom.equals(names.get(i))) {
                        continue;
                    }
                    nameExist = 1;
                }
                if (nameExist == 1) {
                    final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Catégorie éxistante");
                    alert.setHeaderText("Cat\u00e9gorie d\u00e9j\u00e0 cr\u00e9e");
                    alert.setContentText("Veuillez saizir un autre nom pour la nouvelle cat\u00e9gorie.");
                    alert.showAndWait();
                }
                else {
                    final int id = ((keyValuePair)this.respensableCat.getValue()).getKey();
                    final Categorie c = new Categorie(nom, id);
                    final CategorieService pc = new CategorieService();
                    pc.ajouterCategorie(c);
                    this.clearFields();
                    this.listeCategorie.refresh();
                    final Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                    alertSuccess.setTitle("Cat\u00e9gorie Ajout\u00e9e");
                    alertSuccess.setHeaderText("Cat\u00e9gorie Ajout\u00e9e");
                    alertSuccess.setContentText("Vous avez ajout\u00e9 une nouvelle cat\u00e9gorie.");
                    alertSuccess.showAndWait();
                }
            }
            else if (this.getAdd() == 1) {
                final Categorie cat = (Categorie)this.listeCategorie.getSelectionModel().getSelectedItem();
                final String nom2 = this.nomCategorie.getText();
                final int id2 = ((keyValuePair)this.respensableCat.getValue()).getKey();
                final Categorie c2 = new Categorie(nom2, id2);
                final CategorieService pc2 = new CategorieService();
                pc2.modifierCategorie(c2, cat.getId());
                final Alert alertSuccess2 = new Alert(Alert.AlertType.INFORMATION);
                alertSuccess2.setTitle("Cat\u00e9gorie Modifi\u00e9e");
                alertSuccess2.setHeaderText("Cat\u00e9gorie Modifi\u00e9e");
                alertSuccess2.setContentText("Vous avez modifi\u00e9 la cat\u00e9gorie.");
                alertSuccess2.showAndWait();
                this.clearFields();
                this.listeCategorie.refresh();
                this.paneAfficheCategorie.toFront();
                this.paneAjoutCategorie.toBack();
            }
        }
        else if (event.getSource().equals(this.btnSupprimerCategorie)) {
            try {
                final Categorie cat = (Categorie)this.listeCategorie.getSelectionModel().getSelectedItem();
                final CategorieService pc3 = new CategorieService();
                final Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Confirmation Dialog");
                alert2.setHeaderText((String)null);
                alert2.setContentText("Are you sure to delete?");
                final Optional<ButtonType> action = (Optional<ButtonType>)alert2.showAndWait();
                if (action.get() == ButtonType.OK) {
                    pc3.supprimerCategorie(cat);
                }
            }
            catch (SQLException e1) {
                System.err.println(e1);
                final Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setHeaderText((String)null);
                alert3.setContentText("erreur");
                alert3.showAndWait();
            }
            this.clearFields();
        }
        else if (event.getSource().equals(this.modifierCat)) {
            this.setAdd(1);
            final Categorie cat = (Categorie)this.listeCategorie.getSelectionModel().getSelectedItem();
            this.nomCategorie.setText(cat.getNom());
            this.respensableCat.setValue(null);
            this.actionLabel.setText("Modifier la cat\u00e9gorie");
            this.btnAjouterCategorie.setText("Modifier");
            this.paneAjoutCategorie.toFront();
            this.paneAfficheCategorie.toBack();
        }
    }
    
    private void clearFields() {
        this.nomCategorie.clear();
        this.respensableCat.setValue(null);
        this.listeCategories();
    }
}
