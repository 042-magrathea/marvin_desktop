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
public class DataTextField extends TextField {
    
    private String inputRegex = "";
    
    
    public DataTextField() {
        
        ValidationSupport support = new ValidationSupport();

        Validator<String> validator = new Validator<String>() {
            @Override
            public ValidationResult apply(Control control, String value) {
                boolean condition = value != null ? !value
                        .matches(inputRegex) : value == null;

                return ValidationResult.fromMessageIf(control, "input error", Severity.ERROR, condition);
            }

        };
        StyleClassValidationDecoration decorator = new StyleClassValidationDecoration();
        support.setValidationDecorator(decorator);
        support.registerValidator(this, true, validator);
    }

    /**
     * @return the inputRegex
     */
    public String getInputRegex() {return inputRegex;}

    /**
     * @param inputRegex the inputRegex to set
     */
    public void setInputRegex(String inputRegex) {this.inputRegex = inputRegex;}  
}
