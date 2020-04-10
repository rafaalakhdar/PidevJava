/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxloginn;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import entities.Etablissement;
import entities.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.ListEtablissement;

public class MapController implements Initializable {

    @FXML
    private TextField latitudeLabel;

    @FXML
    private TextField longitudeLabel;
    @FXML
    private AnchorPane searchAP;
    private ObservableList<Etablissement> data;
    private String key;
    

    public AnchorPane getSearchAP() {
        return searchAP;
    }

    public void setSearchAP(AnchorPane searchAP) {
        this.searchAP = searchAP;
    }

    public TextField getAddress() {
        return address;
    }

    public void setAddress(TextField address) {
        this.address = address;
    }

    @FXML
    private GoogleMapView googleMapView ;
     //protected GoogleMapView mapView = new GoogleMapView("de-DE", "AIzaSyA0fIjA3jrfnuxAdhkrEoSlFhenvUOULH8");

    private GoogleMap map;

    public GoogleMap getMap() {
        return map;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }
 

    private DecimalFormat formatter = new DecimalFormat("###.00000");
    private GeocodingService geocodingService;
    @FXML
    private TextField address;
    @FXML
    private AnchorPane clear;
    @FXML
    private JFXButton save;
    private JFXTextField adresseX;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        googleMapView.addMapInializedListener(() -> {
            try {
                configureMap();
                
                
            } catch (IOException ex) {
                Logger.getLogger(MapController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
        public void setKey(String key) {
        this.key = key;
    }

    protected void configureMap() throws IOException {

        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(36.80040, 10.18662))
                .mapType(MapTypeIdEnum.ROADMAP)
                .zoom(8);
        map = googleMapView.createMap(mapOptions, false);
        MarkerOptions markerOptions = new MarkerOptions();
        map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {
        try {
            LatLong latLong = event.getLatLong();
            latitudeLabel.setText(formatter.format(latLong.getLatitude()));
            longitudeLabel.setText(formatter.format(latLong.getLongitude()));

            markerOptions.position(latLong)
                    .visible(Boolean.TRUE)
                    .title("My Marker");

            Marker marker = new Marker(markerOptions);
            map.clearMarkers();
            map.addMarker(marker);
            ///
            URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng="+latitudeLabel.getText().replace(',','.')+","+longitudeLabel.getText().replace(',', '.')+"&sensor=true&key=AIzaSyA0fIjA3jrfnuxAdhkrEoSlFhenvUOULH8");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String formattedAddress = "";
            
            try {
                InputStream in = url.openStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String result, line = reader.readLine();
                result = line;
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                
                JSONParser parser = new JSONParser();
                JSONObject rsp = (JSONObject) parser.parse(result);
                
                if (rsp.containsKey("results")) {
                    JSONArray matches = (JSONArray) rsp.get("results");
                    JSONObject data = (JSONObject) matches.get(0); //TODO: check if idx=0 exists
                    formattedAddress = (String) data.get("formatted_address");
                }
                
                adresseX.setText("");
            } catch (ParseException ex) {
                Logger.getLogger(MapController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                urlConnection.disconnect();
                System.out.println("Teeeeeeeessssssssssssst"+formattedAddress);
                address.setText(formattedAddress);
                
            }
            ///
            
        }   catch (MalformedURLException ex) {
                Logger.getLogger(MapController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MapController.class.getName()).log(Level.SEVERE, null, ex);
            }
            ///

        });
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
//         clear.setOnMouseClicked((MouseEvent event) -> {
//             
//         map.clearMarkers();
//         });
         

  
    }

    
    
    @FXML
    private void search(ActionEvent event) throws IOException {

        geocodingService = new GeocodingService();
        geocodingService.geocode(address.getText(), (GeocodingResult[] results, GeocoderStatus status) -> {

            LatLong latLong = null;

            if (status == GeocoderStatus.ZERO_RESULTS) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Pas de resultat");
                alert.show();
                return;
            } else if (results.length > 1) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Multiple results found, showing the first one.");
                alert.show();
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            } else {
                latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(), results[0].getGeometry().getLocation().getLongitude());
            }
            latitudeLabel.setText(formatter.format(latLong.getLatitude()));
            longitudeLabel.setText(formatter.format(latLong.getLongitude()));
            map.setZoom(15);
            MarkerOptions markerOptions = new MarkerOptions();

            markerOptions.position(latLong)
                    .visible(Boolean.TRUE);

            Marker marker = new Marker(markerOptions);

            map.addMarker(marker);

            map.setCenter(latLong);

        });

    }

    @FXML
    private void addposition(ActionEvent event) throws IOException {
    }

    public TextField getLatitudeLabel() {
        return latitudeLabel;
    }

    public TextField getLongitudeLabel() {
        return longitudeLabel;
    }

    public JFXButton getSave() {
        return save;
    }

    public void setSave(JFXButton save) {
        this.save = save;
    }
    public void listRestaurant() throws IOException
    {
      
    }
 
   
}
