package servicios;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "vCelular")
public class VCelular implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String numero = o.toString().trim();
        if (numero.length() != 0 && numero.length() < 10) {
            String plantilla = "^9\\d\\d\\d\\d\\d\\d\\d\\d$"; 
            boolean val = Pattern.matches(plantilla, numero);
            if (!val) {
                throw new ValidatorException(new FacesMessage("Formato inválido 9########"));

            }
        }
    }

}
