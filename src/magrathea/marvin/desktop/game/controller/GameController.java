package magrathea.marvin.desktop.game.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.imageio.ImageIO;
import magrathea.marvin.desktop.game.model.Game;
import magrathea.marvin.desktop.game.service.GameService;
import net.coobird.thumbnailator.Thumbnails;

/**
 * FXML Controller class
 *
 * @author boscalent
 */
public class GameController implements Initializable {

    @FXML
    private AnchorPane gameAnchorPane;

    @FXML
    private TableView<Game> gameTable;
    @FXML
    private TableColumn<Game, String> gameId, gameName;

    @FXML
    private TextField gameNameField, gameImageField;

    @FXML
    private ImageView gameImageView;

    @FXML
    private TextArea gameDescriptionField;

    @FXML
    private GridPane form;

    @FXML
    private Button cancel, OK, loadImageButton;

    private Image image;
    private Game game;
    private GameService service = null;
    private List<TextInputControl> gameTextFields;

    private enum STATE {
        READ, NEW, EDIT, DELETE
    }
    STATE state;

    public GameController() {
        this.service = new GameService();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    }

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

    // TODO: Same with events from cancel/OK button for every state
    private void setInterface() {
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
                OK.setVisible(true);
                loadImageButton.setDisable(false);
                break;
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
                gameImageView.setImage( SwingFXUtils.toFXImage(bufferedImage, null));
                gameImageField.setText(file.getName());
            } catch (IOException ex) {
                //
            }

        }
    }
}