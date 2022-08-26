package servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import dao.Conexion;
import java.util.regex.Pattern;

/**
 * @author chema
 * Validaddor de registros únicos de DNI.
 */
@FacesValidator
public class ValidDNI extends Conexion implements Validator {
	public static boolean DNIDuplicado(String perdni) {
        try {
            PreparedStatement ps1 = Conexion.conectar().prepareStatement("SELECT DNIPER FROM PERSONAL WHERE DNIPER = '" +perdni+ "'");
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                return true;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error en dni repetido" + e.getMessage());
        }
        return false;
    }
	
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String numero = o.toString().trim();
        if (numero.length() != 0 && numero.length() < 8) {
            String plantilla = "^\\d\\d\\d\\d\\d\\d\\d\\d$";
            boolean val = Pattern.matches(plantilla, numero);
            if (!val) {
                throw new ValidatorException(new FacesMessage("Formato inválido ######## carácteres"));
			}
		}
		String perdni = (String) o;
		if(DNIDuplicado(perdni) == true) {
			throw new ValidatorException(new FacesMessage("Este DNI ya existe"));
		}
	}

}
