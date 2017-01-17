package magrathea.marvin.desktop.prize.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import magrathea.marvin.desktop.app.service.StyleService;
import magrathea.marvin.desktop.app.utils.Crud;
import magrathea.marvin.desktop.app.utils.CrudState;
import magrathea.marvin.desktop.prize.model.Prize;
import magrathea.marvin.desktop.prize.service.PrizeService;
import net.coobird.thumbnailator.Thumbnails;

/**
 * FXML Controller class
 *
 * @author boscalent
 */
public class PrizeController extends Crud {

    // TableView
    @FXML private TableView table_list_subsection;

    // CRUD BUTTONS
    @FXML private Button loadImageButton, cancelButton, createButton;

    // SPECIAL NODES
    @FXML private TextField prizeNameField, prizeImageField, searchField;
    @FXML private ImageView prizeImageView;
    @FXML private TextArea prizeDescriptionField, prizeDateField;
    @FXML private TableColumn prizeState;

    // stuff for raddio buttons
    @FXML private ToggleGroup prizeType;
    @FXML private RadioButton all;  // default

    // Load special vars of concrete
    private PrizeService service = null;
    private ObservableList<Prize> items;
    private FilteredList<? extends Object> itemsFilter;

    private Image image;
    private String prizeTypeFilter;

    // Colored Cells
    private static String LOST;
    private static String EXPIRED;

    // Constructors //
    public PrizeController() {
        this.service = new PrizeService();
        LOST = resources.getString("prize_state_LOST");
        EXPIRED = resources.getString("prize_state_EXPIRED");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.table_list_subsection = table_list_subsection;
        super.initialize(location, resources);

        setListenerToSearchField();
        refreshTable();

        // SPECIAL Stuff for Prize  ////////////////////////////////////////////
        image = new Image("/magrathea/marvin/desktop/game/view/notGameImage.png");
        prizeTypeFilter = ((RadioButton) prizeType.getSelectedToggle()).getId();
        //coloredColum();

        // End SPECIAL /////////////////////////////////////////////////////////
        state = CrudState.READ;
        setInterface();
    }

    @FXML
    public void loadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Prize Image");

        // Extension Filter
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                BufferedImage bufferedImage = Thumbnails.of(file).size(200, 300).keepAspectRatio(true).asBufferedImage();
                prizeImageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                prizeImageField.setText(file.getName());
            } catch (IOException ex) {
                //
            }
        }
    }

    @Override
    protected void fromItemToForm(Object object) {
        Prize item = (Prize) object;
        prizeNameField.setText(item.getName());
        prizeDescriptionField.setText(item.getDescription());
        prizeImageField.setText(item.getImage());

        if (item.getImage() != null && !item.getImage().equals("")) {
            prizeImageView.setImage(new Image(item.getImage(), true));
        } else {
            prizeImageView.setImage(StyleService.getInstance().getImageNull());
            prizeImageField.setText("-");
        }
        //prizeDateField.setText(item.getDate());
        prizeDateField.setText(item.getPrizeState(true));

    }

    @Override
    protected Object fromFormToItem() {
        return null;
    }

    @Override
    protected void setRead() {
        createButton.setVisible(false);
        loadImageButton.setVisible(false);
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
        loadImageButton.setVisible(true);
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
        
        coloredColum();

    }

    @Override
    protected void setListenerToSearchField() {
        // Listener for radio Buttons
        prizeType.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (prizeType.getSelectedToggle() != null) {
                    prizeTypeFilter = ((RadioButton) newValue).getId();
                    itemsFilter.setPredicate(item -> {
                        // Special filters for Prize
                        if (prizeTypeFilter.equals("all")) {
                            /* Do nothing */
                        } else if (!((Prize) item).getTypePrice().equals(prizeTypeFilter)) {
                            return false;
                        }
                        return true; // Does not match.
                    });

                }
            }
        });

        // Listener for search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            itemsFilter.setPredicate(item -> {
                // This is a shortcut, bat don't work bad with RadioButtons Filter
                // And return all data in search = "" ignoring the radio filter
                //If filter text is empty, display all data.
                //if (newValue == null || newValue.isEmpty()) {
                //    return true;
                //}

                // Special filters for Prize
                if (prizeTypeFilter.equals("all")) {
                    /* Do nothing */
                } else if (!((Prize) item).getTypePrice().equals(prizeTypeFilter)) {
                    return false;
                }

                // Filters
                String lowerCaseFilter = newValue.toLowerCase();
                if (((Prize) item).getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (((Prize) item).getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (((Prize) item).getPrizeState().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // Does not match.
            });
        });
    }

    // Dont' work with filters & sort
    private void coloredColum() {
        // Custom rendering of the table cell.
        prizeState.setCellFactory(column -> {
            return new TableCell<Prize, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    table_list_subsection.refresh();
                    if (item != null) {
                        if (item.equals(LOST) || item.equals(EXPIRED)) {
                            // Need to create one icon for cell to avoid render problems
                            Text exclamation = FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.EXCLAMATION_CIRCLE);
                            exclamation.getStyleClass().add("exclamation-icon");
                            setTextFill(Color.RED);
                            setGraphic(exclamation);
                        } 
                        setText(item);                        
                    }
                }
            };
        });
    }
}
