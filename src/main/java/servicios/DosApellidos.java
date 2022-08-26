package servicios;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "Apellidos")
public class DosApellidos implements Validator {

    private static final String APELLIDOS_PATTERN = "^([A-Z]{1}[a-z]+[ ]*){1,2}$";

    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object value) throws ValidatorException {
        Pattern pattern = Pattern.compile(APELLIDOS_PATTERN);
        String apellidos = ((String) value).trim();
        if (apellidos.isEmpty()) {
                
        } else {
            Matcher matcher = pattern.matcher(apellidos);
            if (!matcher.matches()) {
                FacesMessage msg = new FacesMessage("Apellidos inválidos ejem: Ramos Gil");
                throw new ValidatorException(msg);
            }

        }
    }

}

