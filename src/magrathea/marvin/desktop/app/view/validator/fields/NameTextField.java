/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.app.view.validator.fields;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.EmailValidator;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;

/**
 *
 * @author tricoman
 */
public class NameTextField extends TextField {
    
    public NameTextField() {
        this.setPromptText("Insert your email address");
        
        ValidationSupport support = new ValidationSupport();

        Validator<String> validator = new Validator<String>() {
            @Override
            public ValidationResult apply(Control control, String value) {
                
                boolean condition = value != null ? !value
                        .matches("^[\\p{L}\\p{M}' \\.\\-]+$") : value == null;

                return ValidationResult.fromMessageIf(control, "not a number", Severity.ERROR, condition);
            }

        };
        StyleClassValidationDecoration decorator = new StyleClassValidationDecoration();
        support.setValidationDecorator(decorator);
        support.registerValidator(this, true, validator);
    }
}