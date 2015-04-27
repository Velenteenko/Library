/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.onlinelib.web.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author velenteenko
 */
@FacesValidator("loginValidator")
public class LoginValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        ResourceBundle bundle = ResourceBundle.getBundle("ua.onlinelib.web.nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        try {

            String newValue = value.toString();

            if (newValue.length() < 5) {
                throw new IllegalArgumentException(bundle.getString("login_length_error"));
            }
            
            if(!Character.isLetter(newValue.charAt(0))){
                throw new IllegalArgumentException(bundle.getString("first_letter_error"));
            }
            
            if(getNamedValues().contains(newValue)){
                throw new IllegalArgumentException(bundle.getString("named_values_error"));
            }
 
        } catch (IllegalArgumentException ex) {
            FacesMessage message = new FacesMessage(ex.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

//            FacesMessage message = new FacesMessage(bundle.getString("login_length_error"));
//            message.setSeverity(FacesMessage.SEVERITY_ERROR);
//            throw new ValidatorException(message);
//        }
//    }
    }
    
    private List<String> getNamedValues(){
        List<String> list = new ArrayList<String>();
        list.add("username");
        list.add("login");
        return list;
    }
}
