package magrathea.marvin.desktop.game.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import magrathea.marvin.desktop.app.utils.Crud;
import magrathea.marvin.desktop.app.utils.CrudState;
import magrathea.marvin.desktop.game.model.Game;
import magrathea.marvin.desktop.game.service.GameService;
import net.coobird.thumbnailator.Thumbnails;

/**
 *
 * @author Arnau, Iván
 */
public class GameController extends Crud {
    // TableView
    @FXML private TableView table_list_subsection;
    
    // CRUD BUTTONS
    @FXML private Button loadImageButton, cancelButton, createButton;
    
    
    @FXML private TextField nameField, imageField, searchField;
    @FXML private TextArea descriptionArea;
@FXML private ImageView gameImageView;
    

    // Load special vars of concrete
    private GameService service = null;
    private ObservableList<Game> items;
    private FilteredList<? extends Object> itemsFilter;

    private Image image;

    // Constructors //
    public GameController() {
        this.service = new GameService();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.table_list_subsection = table_list_subsection;
        super.initialize(url, resources);

        setListenerToSearchField();
        refreshTable();

        //---------------------------------------------------------------------
        // SPECIAL Stuff for Game
        // ENUMS - !! Not present
        // CHECK FORM
        // NOT Implemented, Check for correct URI in Server or wild URL in image field
        // Fallback Image
        image = new Image("/magrathea/marvin/desktop/game/view/notGameImage.png");

        state = CrudState.READ;
        setInterface();
    }

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
        Game game = (Game) item;
        nameField.setText(game.getName());
        descriptionArea.setText(game.getDescription());
        imageField.setText(game.getImage());
        if (game.getImage() != null || !game.getImage().equals("")) {
            gameImageView.setImage(new Image(game.getImage(), true));
        } else {
            gameImageView.setImage(image);
        }
    }

    public void loadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Game Image");

        // Extension Filter
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                BufferedImage bufferedImage = Thumbnails.of(file).size(200, 300).keepAspectRatio(true).asBufferedImage();
                gameImageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                imageField.setText(file.getName());
            } catch (IOException ex) {
                //
            }

        }
    }

    @Override
    protected Game fromFormToItem() {
        Game game = new Game();

        game.setName(nameField.getText());
        game.setDescription(descriptionArea.getText());
        game.setImage(imageField.getText());

        return game;
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

        // filter predicate
        table_list_subsection.setItems(itemsFilter);
        table_list_subsection.getSelectionModel().selectFirst();

    }
}
