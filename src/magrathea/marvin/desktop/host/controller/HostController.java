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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    private TableColumn<Host, String> hostId, hostName, hostPhone;

    @FXML
    private TextField hostNameField, hostPhoneField, hostAddressField, hostEmailField,
            hostLatitudeField, hostLongitudeField, hostPublicMemoField, hostPrivateMemoField;

    @FXML
    private GridPane form;

    @FXML
    private Button cancel, OK;

    private Host host;
    private HostService service = null;
    private List<TextField> hostTextFields;
    private GoogleMap map;
    private MarkerOptions markerOptions;
    private Marker marker;

    private enum STATE {
        READ, NEW, EDIT, DELETE
    }
    STATE state;
    //private InfoWindowOptions infoWindows;
    //private InfoWindow hostInfoWindow;

    public HostController() {
        this.service = new HostService();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hostTextFields = new ArrayList<>();
        host = null;
        // Add all instances of TextFiels in a List for iterate later
        for (Node node : form.getChildren()) {
            if (node instanceof TextField) {
                hostTextFields.add((TextField) node);
            }
        }

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
                    host = newValue;
                    fillReadValues();
                });

        loadMap();
    }

    public void fillReadValues() {
        state = STATE.READ;
        
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

        setInterface();
    }

    public void newHost() {
        // Do stuff
        Host newHost = new Host();

        // Set Interface to READ:VIEW
        state = STATE.NEW;
        setInterface();
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
     *
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

    // TODO: Same with events from cancel/OK button for every state
    private void setInterface() {
        switch (state) {
            case READ:
                // Set Interface to READ:VIEW
                for (TextField tf : hostTextFields) {
                    tf.setEditable(false);
                    tf.setMouseTransparent(true);
                    tf.setFocusTraversable(false);
                }
                cancel.setVisible(false);
                OK.setVisible(false);
                break;
            case NEW:
                for (TextField tf : hostTextFields) {
                    tf.setText("");
                    tf.setEditable(true);
                    tf.setMouseTransparent(false);
                    tf.setFocusTraversable(true);
                }
                cancel.setVisible(true);
                OK.setVisible(true);
                break;
        }
    }
}
