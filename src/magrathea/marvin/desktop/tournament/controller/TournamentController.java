package magrathea.marvin.desktop.tournament.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import magrathea.marvin.desktop.app.service.LoginService;
import magrathea.marvin.desktop.app.service.StyleService;
import magrathea.marvin.desktop.app.utils.Crud;
import magrathea.marvin.desktop.app.utils.CrudState;
import magrathea.marvin.desktop.tournament.model.Tournament;
import magrathea.marvin.desktop.tournament.model.TournamentStateType;
import magrathea.marvin.desktop.tournament.service.TournamentService;

/**
 * FXML Controller class
 *
 * @author boscalent
 */
public class TournamentController extends Crud implements RunningTournament {

    // TableView
    @FXML private TableView table_list_subsection;

    // CRUD BUTTONS
    @FXML private Button cancelButton, createButton, s1, s2, s3;

    // SPECIAL NODES
    @FXML
    private TextField tournamentNameField;
    @FXML private TextArea publicDescMemo, privateDescMemo, tornamentStateMemo;
    @FXML private ImageView tournamentImage;
    @FXML private TableColumn tournamentState;
    @FXML private HBox dynamic_buttons_form;

    // Load special vars of concrete
    private TournamentService service = null;
    private ObservableList<Tournament> items;
    private FilteredList<? extends Object> itemsFilter;
    private ObservableList<Node> buttons; 

    private Image image;
    private String tournamentTypeFilter;

    // Colored cells
    private TournamentStateType stateType;
    private static String CANCELLED, INTERRUPTED;

    // Constructors //
    public TournamentController() {
        service = new TournamentService();
        CANCELLED = resources.getString("tournament_state_CANCELLED");
        INTERRUPTED = resources.getString("tournament_state_INTERRUPTED");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.table_list_subsection = table_list_subsection;
        super.initialize(location, resources);

        setListenerToSearchField();
        refreshTable();

        // SPECIAL Stuff for Tournament ////////////////////////////////////////
        image = new Image("/magrathea/marvin/desktop/game/view/notGameImage.png");
        tournamentTypeFilter = "";
        
        // End SPECIAL /////////////////////////////////////////////////////////

        state = CrudState.READ;
        setInterface();
    }

    public void fill(Tournament tournament) {
        /*
        List<Prize> prizes = tournament.getPrizes();
        if (prizes != null & !prizes.isEmpty()) {
            listPrizes.getItems().clear();
            listPrizes.getItems().addAll(prizes);
        } else {
            listPrizes.getItems().clear();
        }

        List<User> users = tournament.getUsers();
        boolean hasUsers = users != null & !users.isEmpty();
        if (hasUsers) {
            listUsers.getItems().clear();
            listUsers.getItems().addAll(users);
        } else {
            listUsers.getItems().clear();
        }

        checkButton(hasUsers);
         */
    }

    public void checkButton(boolean hasUsers) {
        /*
        if (hasUsers) {
            runButton.setDisable(false);
            tournamentInfo.setText("Good Luck!");
        } else {
            runButton.setDisable(true);
            tournamentInfo.setText("No users in Tournament. Can't run.");
        }
         */
    }

    /**
     * GET a FAKE result of Tournament Only for prototype ( Local data, no con
     * to Service Server )
     */
    public void runTournament() {
        Tournament tournament = (Tournament) table_list_subsection.getSelectionModel().getSelectedItem();

        try {
            //URL fakeResultURL = getClass()
            //        .getResource("/magrathea/marvin/desktop/tournament/view/tournamentFakeResult.fxml");
            //AnchorPane fakeResultPane = FXMLLoader.load(fakeResultURL);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass()
                    .getResource("/magrathea/marvin/desktop/tournament/view/tournamentFakeResult.fxml"));

            Window parentWindows = LoginService.getRoot().getScene().getWindow();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Results of " + tournament.getName() + " tournament");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(parentWindows);

            // FIXTO to auxiliar method for center in parent stage
            dialogStage.setX(
                    ((parentWindows.getWidth() - 600) / 2) + parentWindows.getX());
            dialogStage.setY(
                    ((parentWindows.getHeight() - 400) / 2) + parentWindows.getY());
            dialogStage.setResizable(false);

            //AnchorPane fakeResultPane = (AnchorPane) loader.load();
            Scene scene = new Scene((AnchorPane) loader.load());
            dialogStage.setScene(scene);

            // Set controller
            TournamentFakeResultController controller = loader.<TournamentFakeResultController>getController();
            controller.setTournament(tournament);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void fromItemToForm(Object object) {
        Tournament item = (Tournament) object;
        tournamentNameField.setText(item.getName());
        publicDescMemo.setText(item.getPublicDes());
        privateDescMemo.setText(item.getPrivateDes());

        if (item.getImage() != null && !item.getImage().equals("")) {
            tournamentImage.setImage(new Image(item.getImage(), true));
        } else {
            tournamentImage.setImage(StyleService.getInstance().getImageNull());
        }

    }

    @Override
    protected Object fromFormToItem() {
        return null;
    }

    @Override
    protected void setRead() {
        // Set Interface to READ:VIEW
        for (TextInputControl tf : textFields) {
            tf.setEditable(false);
            tf.setMouseTransparent(true);
            tf.setFocusTraversable(false);
        }
        setState();
    }

    @Override
    protected void setNew() {

    }

    @Override
    protected void setEdit() {

    }

    private void setState() {
        Object object = table_list_subsection.getSelectionModel().getSelectedItem();
        setState(object);
    }

    @Override
    protected void setState(Object object) {
        TournamentStateType stateTypeItem = ((Tournament) object).getTournamentState();
        // need to update b (Managed = true) o NullException for buttons in array Managed=false
        buttons = dynamic_buttons_form.getChildren();
        for (Node b : buttons) {
            if (b instanceof Button) {
                b.setVisible(false);
                b.setManaged(false);
            }
        }

        switch (stateTypeItem.name()) {
            case "CREATED":     
                setUpButtonTourState(s1, "tournament_action_publish", this::onPublish ); break;
            case "PUBLISHED":   
                setUpButtonTourState(s1, "tournament_action_cancel", this::onCancelPublished );                
                setUpButtonTourState(s2, "tournament_action_close", this::onClose ); break;
            case "CLOSED":      
                setUpButtonTourState(s1, "tournament_action_begin", this::onBegin );
                setUpButtonTourState(s2, "tournament_action_cancel", this::onCancelClose); break;
            case "BEGINNED":
                setUpButtonTourState(s1, "tournament_action_finished", this::onFinished );
                setUpButtonTourState(s2, "tournament_action_interrup", this::onInterrup );
                setUpButtonTourState(s3, "tournament_action_running", this::onRunning );break;    
            case "FINISHED":
                setUpButtonTourState(s1, "tournament_action_results", this::onPublishResults );break;
            case "CANCELLED":
                // ⊕ Mutex Is restarted from CLOSED state or is restarted from PUBLISHED State; not from both States.
                setUpButtonTourState(s1, "tournament_action_restart_published", this::onRestartPublish );
                setUpButtonTourState(s1, "tournament_action_restart_closed", this::onRestartClose ); break;
            case "INTERRUPTED":
                setUpButtonTourState(s1, "tournament_action_cancel", this::onCancelInterrupted );
                setUpButtonTourState(s1, "tournament_action_restart_beginned", this::onRestartBeginned ); break;
        }
    }

    private void setUpButtonTourState(Button button, String i18n, EventHandler<ActionEvent> e) {
        button.setManaged(true);
        button.setVisible(true);
        button.setText(resources.getString(i18n));
        button.setOnAction(e);
    }

    @Override
    protected void refreshTable() {
        String search = searchField.getText();
        searchField.setText("");

        items = FXCollections.observableArrayList(service.getAll());
        // Wrap all data in FilteredList
        itemsFilter = new FilteredList<>(items, p -> true);

        searchField.setText(search);

        SortedList<? extends Object> sortableData = new SortedList<>(itemsFilter);
        sortableData.comparatorProperty().bind(table_list_subsection.comparatorProperty());

        // filter predicate
        table_list_subsection.setItems(sortableData);
        table_list_subsection.getSelectionModel().selectFirst();

        //coloredColum();
    }

    @Override
    protected void setListenerToSearchField() {

    }

    public void onAction() {
    }

    @Override
    public void onPublish(ActionEvent event) {
        // state/guard/entry/exit
        System.out.println("PUBLISH");
    }

    @Override
    public void onCancelPublished(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public void onClose(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public void onBegin(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public void onCancelClose(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public void onFinished(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public void onInterrup(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public void onRunning(ActionEvent event) {
        runTournament();
    }

    @Override
    public void onPublishResults(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public void onRestartPublish(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public void onRestartClose(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public void onCancelInterrupted(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public void onRestartBeginned(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }



    /**
     * TOURNAMENT STATE METHODS *
     */
}
