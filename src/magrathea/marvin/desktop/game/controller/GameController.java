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

    //@FXML private AnchorPane gameAnchorPane;
    //@FXML private TableView<Game> gameTable;
    //@FXML private TableColumn<Game, String> gameId, gameName;
    //@FXML private TextField gameNameField, gameImageField;
    //@FXML private ImageView gameImageView;
    //@FXML private GridPane form;
    // ------------  NEW ----------------------- //
    // Load special FXML of concrete
    @FXML private TableView table_list_subsection;
    @FXML private TextField nameField, imageField, searchField;
    @FXML private TextArea descriptionArea;

    @FXML private ImageView gameImageView;
    @FXML private Button loadImageButton, cancelButton, createButton;

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

    /*
        gameTextFields = new ArrayList<>();
        game = null;
        image = new Image("/magrathea/marvin/desktop/game/view/notGameImage.png");
        
        // Add all instances of TextInputControl in a List for iterate later
        for (Node node : form.getChildren()) {
            if (node instanceof TextField) {
                gameTextFields.add((TextField) node);
            } else if (node instanceof TextArea) {
                gameTextFields.add((TextArea) node);
            }
        }
        gameTextFields.add(gameImageField);

        // COLUMNS
        gameId.setCellValueFactory(new PropertyValueFactory<>("idGame"));
        gameName.setCellValueFactory(new PropertyValueFactory<>("name"));

        // TABLE MODEL
        ObservableList<Game> games = FXCollections.observableArrayList(service.getAll());
        gameTable.setEditable(true);
        gameTable.setItems(games);
        gameTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);    // no bar   

        gameTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    game = newValue;
                    fillReadValues();
                });
        gameTable.getSelectionModel().selectFirst(); // First Selected, not void
     */
 /*
public void fillReadValues() {
        state = STATE.READ;

        gameNameField.setText(game.getName());
        gameDescriptionField.setText(game.getDescription());
        gameImageField.setText(game.getImage());
        if (game.getImage() != null || !game.getImage().equals("")) {
            gameImageView.setImage(new Image(game.getImage(), true));
        } else {
            gameImageView.setImage(image);
        }

        setInterface();
    }

    public void newGame() {
        // Do stuff
        Game newGame = new Game();

        // Set Interface to READ:VIEW
        state = STATE.NEW;
        setInterface();
    }
    
    private void saveNewGame(){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Save Game");
        alert.setHeaderText("Do stuff for save the game");
        alert.setContentText("Find a way for save the image of the game");
        alert.showAndWait();
    }

    // TODO: Same with events from cancel/OK button for every state
    protected void setInterface() {
        switch (state) {
            case READ:
                // Set Interface to READ:VIEW
                for (TextInputControl tf : gameTextFields) {
                    tf.setEditable(false);
                    tf.setMouseTransparent(true);
                    tf.setFocusTraversable(false);
                }
                cancel.setVisible(false);
                OK.setVisible(false);
                loadImageButton.setDisable(true);
                break;
            case NEW:
                for (TextInputControl tf : gameTextFields) {
                    tf.toString();
                    tf.setText("");
                    tf.setEditable(true);
                    tf.setMouseTransparent(false);
                    tf.setFocusTraversable(true);
                }
                gameImageField.setText("");         // ¿?
                gameImageView.setImage(image);
                cancel.setVisible(true);
                OK.setText("Save");
                OK.setOnAction((ActionEvent e) -> {saveNewGame();});    // lamfda exp (equals inner class)
                OK.setVisible(true);
                loadImageButton.setDisable(false);
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
        descriptionArea.setFocusTraversable(true);
    }

    @Override
    protected void setNew() {

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
