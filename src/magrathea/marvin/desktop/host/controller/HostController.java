/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.host.controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import magrathea.marvin.desktop.host.model.Host;
import magrathea.marvin.desktop.host.service.HostService;

/**
 * FXML Controller class
 *
 * @author boscalent
 */
public class HostController implements Initializable, MapComponentInitializedListener {

    @FXML
    private AnchorPane hostAnchorPane;
    @FXML
    private GoogleMapView mapView;
    @FXML
    private TableView<Host> hostTable;
    @FXML
    private TableColumn<Host, String> hostId;
    @FXML
    private TableColumn<Host, String> hostName;
    @FXML
    private TableColumn<Host, String> hostPhone;

    @FXML
    private TextField hostNameField;
    @FXML
    private TextField hostPhoneField;
    @FXML
    private TextField hostAddressField;
    @FXML
    private TextField hostEmailField;
    @FXML
    private TextField hostLatitudeField;
    @FXML
    private TextField hostLongitudeField;
    @FXML
    private TextField hostPublicMemoField;
    @FXML
    private TextField hostPrivateMemoField;

    private HostService service = null;
    private TextField[] hostTextFields = new TextField[8];
    private GoogleMap map;
    private MarkerOptions markerOptions;
    private Marker marker;
    private InfoWindowOptions infoWindows;
    private InfoWindow hostInfoWindow;

    public HostController() {
        this.service = new HostService();
        hostTextFields[0] = hostNameField;
        hostTextFields[1] = hostPhoneField;
        hostTextFields[2] = hostAddressField;
        hostTextFields[3] = hostEmailField;
        hostTextFields[4] = hostLatitudeField;
        hostTextFields[5] = hostLongitudeField;
        hostTextFields[5] = hostPublicMemoField;
        hostTextFields[5] = hostPrivateMemoField;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // COLUMNS
        hostId.setCellValueFactory(new PropertyValueFactory<>("id"));
        hostName.setCellValueFactory(new PropertyValueFactory<>("name"));
        hostPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // TABLE MODEL
        ObservableList<Host> hosts = FXCollections.observableArrayList(service.getAll());
        hostTable.setEditable(true);
        hostTable.setItems(hosts);
        hostTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);    // no bar   

        hostTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    fillReadValues(newValue);
                });

        loadMap();
    }

    public void fillReadValues(Host host) {
        // Prepare fields
        //for (TextField tf : hostTextFields){
        //    tf.editableProperty().set(false);
        //}
        hostNameField.setText(host.getName());
        hostPhoneField.setText(host.getPhone());
        hostAddressField.setText(host.getAddress());
        hostEmailField.setText(host.geteMail());
        hostLatitudeField.setText(String.valueOf(host.getLatitude()));
        hostLongitudeField.setText(String.valueOf(host.getLongitude()));
        hostPublicMemoField.setText("TODO://");
        hostPrivateMemoField.setText("TODO://");

        mapView.setCenter(host.getLatitude(), host.getLongitude());
        addMarkerToMap(host);

    }

    private void loadMap() {
        mapView.addMapInializedListener(this);
        mapView.autosize();
        //hostAnchorPane.getChildren().add(mapView);

    }

    @Override
    public void mapInitialized() {
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(41.390205, 2.154007))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(15);

        map = mapView.createMap(mapOptions);
        
        // Wait to map initialize for change to first loc
        hostTable.getSelectionModel().selectFirst();
    }

    /**
     * Show a marker for the host
     * @param host 
     */
    public void addMarkerToMap(Host host) {
        //Add a marker to the map
        markerOptions = new MarkerOptions();

        markerOptions.position(new LatLong(host.getLatitude(), host.getLongitude()))
                .visible(Boolean.TRUE)
                .title(host.getName());

        marker = new Marker(markerOptions);

        map.addMarker(marker);
        
        /* // Need big height
        infoWindows = new InfoWindowOptions();
        infoWindows.content(host.getName());
        hostInfoWindow = new InfoWindow(infoWindows);
        hostInfoWindow.open(map, marker);
        */
    }
}
