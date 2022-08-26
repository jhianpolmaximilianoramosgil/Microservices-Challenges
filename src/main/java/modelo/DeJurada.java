package modelo;

import java.util.Date;
import java.util.GregorianCalendar;
import lombok.Data;

@Data
public class DeJurada {

    private int id;
    private Double importe;
    private String proyecto;
    private String CODCEN;
    private String DESCEN;
    private String ARECEN;
    private Integer IDPER;
    private String nomper;
    private String NOMPER;
    private String APEPER;
    private String CONDEJ;
    private String dni;
    private String CODUBI;
    private String PROUBI;
    private String DEPUBI;
    private String DISUBI;
    private String domicilio;
    private Double RECDEJ;
    private Date fecha = GregorianCalendar.getInstance().getTime();
    private String estado;

    private CentroCosto centrocosto = new CentroCosto();
    private Personal personal = new Personal();
    private Ubigeo ubigeo = new Ubigeo();
}
