package magrathea.marvin.desktop.host.controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import magrathea.marvin.desktop.app.utils.Crud;
import magrathea.marvin.desktop.app.utils.CrudState;
import magrathea.marvin.desktop.app.utils.MessageHelper;
import magrathea.marvin.desktop.host.model.Host;
import magrathea.marvin.desktop.host.service.HostService;

/**
 * FXML Controller class
 *
 * @author boscalent
 */
public class HostController extends Crud implements MapComponentInitializedListener {

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
    private volatile boolean map_is_loaded;
    private GoogleMap map;
    private MapOptions mapOptions;
    private MarkerOptions markerOptions;
    private Marker marker;
    private InfoWindowOptions infoWindows;
    private InfoWindow hostInfoWindow;

    // Constructors //
    static {

    }

    public HostController() {
        this.service = new HostService();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.table_list_subsection = table_list_subsection;
        super.initialize(location, resources);

        // SPECIAL Stuff for Host
        loadMap();

        setListenerToSearchField();
        refreshTable();

        state = CrudState.READ;
        setInterface();
    }

    @Override
    protected void setListenerToSearchField() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            itemsFilter.setPredicate(item -> {
                // If filter text is empty, display all data.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                // Filters                
                if (((Host) item).getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches name.
                } else if (((Host) item).getAddress().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Address.
                }
                return false; // Does not match.
            });
        });
    }

    @Override
    protected void fromItemToForm(Object item) {
        Host host = (Host) item;

        //hostNameField, hostPhoneField, hostAddressField, hostEmailField,
        //    hostLatitudeField, hostLongitudeField, hostPublicMemoField, hostPrivateMemoField;
        hostNameField.setText(host.getName());
        hostPhoneField.setText(host.getPhone());
        hostEmailField.setText(host.getEmail());
        hostLatitudeField.setText(String.valueOf(host.getLatitude()));
        hostLongitudeField.setText(String.valueOf(host.getLongitude()));
        hostAddressField.setText(host.getAddress());
        hostPrivateMemoField.setText("TO DO");
        hostPublicMemoField.setText("TO DO");

        // Or in first attempt the map is not loader and throw an exception
        if (map_is_loaded) {
            mapView.setCenter(host.getLatitude(), host.getLongitude());
            addMarkerToMap(host);
        }

    }

    public void onSendMail(ActionEvent event) {
        if (table_list_subsection.getSelectionModel().getSelectedItem() != null) {
            if (((Host) table_list_subsection.getSelectionModel().getSelectedItem()).getEmail() != null) {
                String mail = ((Host) table_list_subsection.getSelectionModel().getSelectedItem()).getEmail();
                String name = ((Host) table_list_subsection.getSelectionModel().getSelectedItem()).getName();
                MessageHelper.showFakeEmailSender(null, null, name, mail);
                //System.out.println("mailto:" + mail);
            } else {
                System.err.println("ERROR: NULL mail");
            }
        } else {
            System.err.println("ERROR: NO SELECT ITEM");
        }
    }

    @Override
    protected Object fromFormToItem() {
        // TO DO
        return null;
    }

    @Override
    protected void setRead() {
        createButton.setVisible(false);
        cancelButton.setVisible(false);
        // Set Interface to READ:VIEW
        for (TextInputControl tf : textFields) {
            tf.setEditable(false);
            tf.setMouseTransparent(true);
            tf.setFocusTraversable(false);
        }
    }

    @Override
    protected void setNew() {
        createButton.setVisible(false); // TO FIX 
        cancelButton.setVisible(true);
        // Set Interface to READ:VIEW
        for (TextInputControl tf : textFields) {
            tf.setText("");
            tf.setEditable(true);
            tf.setMouseTransparent(false);
            tf.setFocusTraversable(true);
        }
    }

    @Override
    protected void setEdit() {

    }

    @Override
    protected void refreshTable() {
        /*
         * Very expensive query to server, OK for this project.
         * SearchField & String is a trick for remember filter on refreshTable
         */
        String search = searchField.getText();
        searchField.setText("");

        items = FXCollections.observableArrayList(service.getAll());
        // Wrap all data in FilteredList
        itemsFilter = new FilteredList<>(items, p -> true);

        searchField.setText(search);
        
        // Hack for sort columns in Filtered List
        // http://stackoverflow.com/questions/17958337/javafx-tableview-with-filteredlist-jdk-8-does-not-sort-by-column
        SortedList<? extends Object> sortableData = new SortedList<>(itemsFilter);
        sortableData.comparatorProperty().bind(table_list_subsection.comparatorProperty());

        // filter predicate
        table_list_subsection.setItems(sortableData);
        table_list_subsection.getSelectionModel().selectFirst();
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
        mapOptions = new MapOptions();

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
        map_is_loaded = true;     // Control not load first time by fromItemToForm
        Host host = (Host) table_list_subsection.getSelectionModel().getSelectedItem();
        mapView.setCenter(host.getLatitude(), host.getLongitude());
        addMarkerToMap(host);
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

        // !!Not implemented, Need big height
        infoWindows = new InfoWindowOptions();
        infoWindows.content(host.getName());
        hostInfoWindow = new InfoWindow(infoWindows);
        hostInfoWindow.open(map, marker);
    }

    @Override
    protected void setState(Object object) {
    }

}
