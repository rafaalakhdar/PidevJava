// 
// Decompiled by Procyon v0.5.36
// 

package javafxloginn;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import javafx.event.ActionEvent;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import javafx.beans.value.ObservableValue;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import javafx.fxml.Initializable;

public class Map2Controller implements Initializable, MapComponentInitializedListener
{
    @FXML
    private GoogleMapView mapView;
    @FXML
    private TextField addressTextField;
    private GoogleMap map;
    private GeocodingService geocodingService;
    private StringProperty address;
    
    public Map2Controller() {
        this.address = (StringProperty)new SimpleStringProperty();
    }
    
    public void initialize(final URL url, final ResourceBundle rb) {
        this.mapView.addMapInializedListener((MapComponentInitializedListener)this);
        this.address.bind((ObservableValue)this.addressTextField.textProperty());
    }
    
    public void mapInitialized() {
        this.geocodingService = new GeocodingService();
        final MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(47.6097, -122.3331)).mapType(MapTypeIdEnum.ROADMAP).overviewMapControl(false).panControl(false).rotateControl(false).scaleControl(false).streetViewControl(false).zoomControl(false).zoom(12);
        this.map = this.mapView.createMap(mapOptions);
    }
    
    @FXML
    public void addressTextFieldAction(final ActionEvent event) {
        this.geocodingService.geocode((String)this.address.get(), (results, status) -> {
            LatLong latLong = null;
            if (status == GeocoderStatus.ZERO_RESULTS) {
                final Alert alert = new Alert(Alert.AlertType.ERROR, "No matching address found", new ButtonType[0]);
                alert.show();
                return;
            }
            if (results.length > 1) {
                final Alert alert = new Alert(Alert.AlertType.WARNING, "Multiple results found, showing the first one.", new ButtonType[0]);
                alert.show();
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            }
            else {
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            }
            this.map.setCenter(latLong);
        });
    }
}
