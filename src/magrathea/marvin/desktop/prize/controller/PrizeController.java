package magrathea.marvin.desktop.prize.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import magrathea.marvin.desktop.prize.model.Prize;
import magrathea.marvin.desktop.prize.service.PrizeService;
import net.coobird.thumbnailator.Thumbnails;

/**
 * FXML Controller class
 *
 * @author boscalent
 */
public class PrizeController implements Initializable {

    //@FXML
    //private AnchorPane prizeAnchorPane;

    @FXML
    private TableView<Prize> prizeTable;
    @FXML
    private TableColumn<Prize, String> prizeId, prizeName;

    @FXML
    private TextField prizeNameField, prizeImageField;

    @FXML
    private ImageView prizeImageView;

    @FXML
    private TextArea prizeDescriptionField;

    @FXML
    private GridPane form;

    @FXML
    private Button cancel, OK, loadImageButton;

    private Image image;
    private Prize prize;
    private PrizeService service = null;
    private List<TextInputControl> prizeTextFields;

    private enum STATE {
        READ, NEW, EDIT, DELETE
    }
    STATE state;

    public PrizeController() {
        this.service = new PrizeService();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prizeTextFields = new ArrayList<>();
        prize = null;
        //image = new Image("/magrathea/marvin/desktop/prize/view/notPrizeImage.png");
        
        // Add all instances of TextInputControl in a List for iterate later
        for (Node node : form.getChildren()) {
            if (node instanceof TextField) {
                prizeTextFields.add((TextField) node);
            } else if (node instanceof TextArea) {
                prizeTextFields.add((TextArea) node);
            }
        }
        prizeTextFields.add(prizeImageField);

        // COLUMNS
        prizeId.setCellValueFactory(new PropertyValueFactory<>("idPrize"));
        prizeName.setCellValueFactory(new PropertyValueFactory<>("name"));

        // TABLE MODEL
        ObservableList<Prize> prizes = FXCollections.observableArrayList(service.getAll());
        prizeTable.setEditable(true);
        prizeTable.setItems(prizes);
        prizeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);    // no bar   

        prizeTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    prize = newValue;
                    fillReadValues();
                });
        prizeTable.getSelectionModel().selectFirst(); // First Selected, not void*/
    }

    public void fillReadValues() {
        state = STATE.READ;

        prizeNameField.setText(prize.getName());
        prizeDescriptionField.setText(prize.getDescription());
        prizeImageField.setText(prize.getImage());
        if (prize.getImage() != null || !prize.getImage().equals("")) {
            prizeImageView.setImage(new Image(prize.getImage(), true));
        } else {
            prizeImageView.setImage(image);
        }

        setInterface();
    }

    public void newPrize() {
        // Do stuff
        Prize newPrize = new Prize();

        // Set Interface to READ:VIEW
        state = STATE.NEW;
        setInterface();
    }
    
    private void saveNewPrize(){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Save Prize");
        alert.setHeaderText("Do stuff for save the prize");
        alert.setContentText("Find a way for save the image of the prize");
        alert.showAndWait();
    }

    // TODO: Same with events from cancel/OK button for every state
    private void setInterface() {
        switch (state) {
            case READ:
                // Set Interface to READ:VIEW
                for (TextInputControl tf : prizeTextFields) {
                    tf.setEditable(false);
                    tf.setMouseTransparent(true);
                    tf.setFocusTraversable(false);
                }
                cancel.setVisible(false);
                OK.setVisible(false);
                loadImageButton.setDisable(true);
                break;
            case NEW:
                for (TextInputControl tf : prizeTextFields) {
                    tf.toString();
                    tf.setText("");
                    tf.setEditable(true);
                    tf.setMouseTransparent(false);
                    tf.setFocusTraversable(true);
                }
                prizeImageField.setText("");         // ¿?
                prizeImageView.setImage(image);
                cancel.setVisible(true);
                OK.setText("Save");
                OK.setOnAction((ActionEvent e) -> {saveNewPrize();});    // lamfda exp (equals inner class)
                OK.setVisible(true);
                loadImageButton.setDisable(false);
                break;
        }
    }

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
                prizeImageView.setImage( SwingFXUtils.toFXImage(bufferedImage, null));
                prizeImageField.setText(file.getName());
            } catch (IOException ex) {
                //
            }

        }
    }
}