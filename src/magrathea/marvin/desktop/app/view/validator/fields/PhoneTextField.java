/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.app.view.validator.fields;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;

/**
 *
 * @author tricoman
 */
public class PhoneTextField extends TextField {
    
    public PhoneTextField() {
        
        this.setPromptText("Insert user's phone number");
        ValidationSupport support = new ValidationSupport();

        Validator<String> validator = new Validator<String>() {
            @Override
            public ValidationResult apply(Control control, String value) {
                boolean condition = value != null ? !value
                        .matches("((?=.*\\d).{9})") : value == null;

                return ValidationResult.fromMessageIf(control, "not a number", Severity.ERROR, condition);
            }

        };
        StyleClassValidationDecoration decorator = new StyleClassValidationDecoration();
        support.setValidationDecorator(decorator);
        support.registerValidator(this, true, validator);
       
    }
    
    @Override
    public void replaceText(int i, int i1, String string) {
        if(string.matches("[0-9]") || string.isEmpty()) {
            super.replaceText(i, i1, string);
        } 
    }
}
