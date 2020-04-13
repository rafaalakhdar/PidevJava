/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;


import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.*;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import entities.Etablissement;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ListEtablissement;

/**
 * FXML Controller class
 *
 * @author Rafaa
 */
public class Map2Controller implements Initializable, MapComponentInitializedListener {
 

     @FXML
    private GoogleMapView mapView;
    
    @FXML
    private TextField addressTextField;
    
    private GoogleMap map;
    
    private GeocodingService geocodingService;
    private ObservableList<Etablissement> data;

    private StringProperty address = new SimpleStringProperty();
    
   
  
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
        address.bind(addressTextField.textProperty());
    }    
    
    
     @Override
    public void mapInitialized() {
        geocodingService = new GeocodingService();
        MapOptions mapOptions = new MapOptions();
        
        mapOptions.center(new LatLong(36.80040, 10.18662))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(8);

        map = mapView.createMap(mapOptions);
        
        ListEtablissement LDS = new ListEtablissement();
        data = LDS.ListEtab();
                for (Etablissement etab : data)
         {
            
            MarkerOptions markerOptionss = new MarkerOptions();
 
            markerOptionss.position(new LatLong(etab.getLatitude(),etab.getLongitude()))
                    .visible(Boolean.TRUE).label(" TGT : " +etab.getName());
            //System.out.println(""+etab.getName());
            Marker markers = new Marker(markerOptionss);
            map.addMarker(markers);
         }
        
    }
    
    
    @FXML
    public void addressTextFieldAction(ActionEvent event) {
        geocodingService.geocode(address.get(), (GeocodingResult[] results, GeocoderStatus status) -> {
            
            LatLong latLong = null;
            
            if( status == GeocoderStatus.ZERO_RESULTS) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No matching address found");
                alert.show();
                return;
            } else if( results.length > 1 ) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Multiple results found, showing the first one.");
                alert.show();
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            } else {
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            }
            
            map.setCenter(latLong);

        });
    }
    
}
