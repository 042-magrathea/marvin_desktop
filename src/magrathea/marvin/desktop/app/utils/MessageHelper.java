/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.app.utils;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import magrathea.marvin.desktop.app.service.LoginService;

/**
 *
 * @author boscalent
 */
public class MessageHelper {
    public static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:"
            + "\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c"
            + "\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b"
            + "\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
            + "\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4]"
            + "[0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?"
            + "[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e"
            + "-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e"
            + "-\\x7f])+)\\])";
    public static final String PHONE_REGEX = "\\d{9}";
    public static final String NAME_REGEX = "^[\\p{L}\\p{M}' \\.\\-]+$";
    public static final String PASS_REGEX = "((?=.*\\d)(?=.*[a-zA-Z]).{6,20})";
    public static final String DEFAULT_PASS = "marvin42";
    
    private final static String TOKENIZER = "\\. "; // \\ for scape special . || [. ]
    private static final ResourceBundle resources;
    
    static {
        resources = LoginService.getInstance().getBundle();
    }
    
    
    private MessageHelper(){}
    
    public static void showErrorAlert(String actionRelated, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(resources.getString("message_error_title"));
        alert.setHeaderText(actionRelated + resources.getString("message_error_failed"));
        // Linux FIX to show multiline
        String[] messageMultiLine = message.split(TOKENIZER);
        if ( messageMultiLine.length == 1){
            alert.setContentText(message);
        } else {
            VBox vBox = new VBox();
            for ( String s : messageMultiLine ){
                Label l = new Label(s);
                vBox.getChildren().add(l);
            }
             alert.getDialogPane().setContent(vBox);  
        }

        alert.showAndWait();
    }

    public static void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(resources.getString("message_success_title"));
        alert.setHeaderText(null);
        
        // Linux FIX to show multiline
        String[] messageMultiLine = message.split(TOKENIZER);
        if ( messageMultiLine.length == 1){
            alert.setContentText(message);
        } else {
            VBox vBox = new VBox();
            for ( String s : messageMultiLine ){
                Label l = new Label(s);
                vBox.getChildren().add(l);
            }
             alert.getDialogPane().setContent(vBox);  
        }
        alert.showAndWait();
    }
    
    /**
     * 
     * @param title  (optional)
     * @param header (optional)
     * @param to     (mandatory)
     * @param email
     */
    public static void showFakeEmailSender(String title, String header, String to, String email){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        
        alert.setTitle( title == null ? resources.getString("email_title_default") + " " + to : title);
        alert.setHeaderText( header == null ? resources.getString("email_header_default") : header);
        FontAwesomeIconView mailIcon = new FontAwesomeIconView();
        mailIcon.getStyleClass().add("mailIcon");       // Add the style
        alert.setGraphic(mailIcon);
        
        VBox vBox = new VBox();
        
        Label label = new Label(resources.getString("email_label_to")  + to + " - " + email);
        //Separator separator = new Separator(Orientation.HORIZONTAL);
        TextArea area = new TextArea();
        area.setAccessibleText(resources.getString("email_message_prompt_text"));
        
        vBox.getChildren().add(label);
        //vBox.getChildren().add(separator);
        vBox.getChildren().add(area);
        
        // add the Vbox with content and the StyleSheet for load icon and styles
        alert.getDialogPane().setContent(vBox);
        alert.getDialogPane().getStylesheets().add(LoginService.getInstance().getCSS());
        alert.showAndWait();
    }
}