package magrathea.marvin.desktop.app.utils;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import magrathea.marvin.desktop.app.utils.Crud;
import magrathea.marvin.desktop.app.utils.CrudState;
import magrathea.marvin.desktop.game.model.Game;
import magrathea.marvin.desktop.host.model.Host;
import magrathea.marvin.desktop.host.service.HostService;

/**
 * FXML Controller class
 *
 * @author boscalent
 */
public class Template_Controller extends Crud implements MapComponentInitializedListener {

    // TableView
    @FXML private TableView table_list_subsection;

    // CRUD BUTTONS
    @FXML private Button createButton, cancelButton;

    // SPECIAL NODES
    @FXML private TextField searchField, hostNameField, hostPhoneField, hostAddressField, hostEmailField,
            hostLatitudeField, hostLongitudeField, hostPublicMemoField, hostPrivateMemoField;
    @FXML private GoogleMapView mapView;

    private HostService service = null;
    private ObservableList<Host> items;
    private FilteredList<? extends Object> itemsFilter;

    // Wrapper for JS Google Map Lib
    private GoogleMap map;
    private MarkerOptions markerOptions;
    private Marker marker;
    //private InfoWindowOptions infoWindows;
    //private InfoWindow hostInfoWindow;

    // Constructors //
    public Template_Controller() {
        this.service = new HostService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.table_list_subsection = table_list_subsection;
        super.initialize(location, resources);

        setListenerToSearchField();
        refreshTable();

        state = CrudState.READ;
        setInterface();

        // SPECIAL Stuff for Host
        loadMap();

        /*
        mapView.setCenter(host.getLatitude(), host.getLongitude());
        addMarkerToMap(host);
         */
    }

    /*
    public void fillReadValues() {
        state = STATE.READ;

        hostNameField.setText(host.getName());
        hostPhoneField.setText(host.getPhone());
        hostAddressField.setText(host.getAddress());
        hostEmailField.setText(host.getEmail());
        hostLatitudeField.setText(String.valueOf(host.getLatitude()));
        hostLongitudeField.setText(String.valueOf(host.getLongitude()));
        hostPublicMemoField.setText("TODO://");
        hostPrivateMemoField.setText("TODO://");



        setInterface();
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
    
     */

    private void setListenerToSearchField() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            itemsFilter.setPredicate(item -> {
                // If filter text is empty, display all data.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                // Filters                
                return ((Game) item).getName().toLowerCase().contains(lowerCaseFilter);
            });
        });
    }

    @Override
    protected void fromItemToForm(Object item) {
        
    }

    @Override
    protected Object fromFormToItem() {
        return null;
    }

    @Override
    protected void setRead() {

    }

    @Override
    protected void setNew() {

    }

    @Override
    protected void setEdit() {

    }

    @Override
    protected void refreshTable() {

    }

    //////////////////////////////////////////////////////////////////////
    //          MAP STUFF FOR HOST                                      //
    //////////////////////////////////////////////////////////////////////
    private void loadMap() {
        mapView.addMapInializedListener(this);
        mapView.autosize();
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
        table_list_subsection.getSelectionModel().selectFirst();
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

        /* // !!Not implemented, Need big height
        infoWindows = new InfoWindowOptions();
        infoWindows.content(host.getName());
        hostInfoWindow = new InfoWindow(infoWindows);
        hostInfoWindow.open(map, marker);
         */
    }

}
