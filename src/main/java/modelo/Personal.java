package modelo;

import java.util.UUID;
import lombok.Data;

@Data

public class Personal {
    String pwd1 = UUID.randomUUID().toString().toUpperCase().substring(2, 6);
    String pwd2 = UUID.randomUUID().toString().toLowerCase().substring(2, 6);
    String pwd3 = "/-";
    String password = pwd1 + pwd2 + pwd3;

    private Integer idper;
    private String nombre;
    private String apellido;
    private String dni;
    private String celular;
    private String email;
    private String codubi;
    private String proubi;
    private String depubi;
    private String disubi;
    private String domper;
    private String sexo;
    private String cargo;
    private String estper;
    
    private Ubigeo ubigeo = new Ubigeo();
    private String pwdper = password;

    public Personal() {

    }


}
