package servicios;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "Nombres")
public class NombreApellido implements Validator {

    private static final String NOMBRES_PATTERN = "^([A-Z]{1}[a-z]+[ ]*){1,2}$";

    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object value) throws ValidatorException {
        Pattern pattern = Pattern.compile(NOMBRES_PATTERN);
        String nombres = ((String) value).trim();
        if (nombres.isEmpty()) {
                
        } else {
            Matcher matcher = pattern.matcher(nombres);
            if (!matcher.matches()) {
                FacesMessage msg = new FacesMessage("Nombres inválidos ejem: Jhianpol Maximiliano");
                throw new ValidatorException(msg);
            }

        }
    }

}

