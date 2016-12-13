/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.user.view.fields;



import javafx.scene.Node;
import javafx.scene.control.Control;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;


/**
 *
 * @author tricoman
 */
public class PasswordTextField extends CustomPasswordField {
    
    public PasswordTextField() {
        this.setPromptText("Enter user's desired password");

        ValidationSupport support = new ValidationSupport();

        Validator<String> validator = new Validator<String>() {
            @Override
            public ValidationResult apply(Control control, String value) {
                boolean condition = value != null ? !value
                        .matches("((?=.*\\d)(?=.*[a-zA-Z]).{6,20})"): value == null;

                return ValidationResult.fromMessageIf(control, "not a number", 
                        Severity.ERROR, condition);
            }

        };
        StyleClassValidationDecoration decorator = new StyleClassValidationDecoration();
        support.setValidationDecorator(decorator);
        support.registerValidator(this, true, validator);
    }
    
}
