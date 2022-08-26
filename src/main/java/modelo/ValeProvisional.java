package modelo;

import java.util.Date;
import lombok.Data;
import java.util.GregorianCalendar;

@Data

public class ValeProvisional {

    private Integer idval;
    private Double impval;
    private Date fecval = GregorianCalendar.getInstance().getTime();
    private String codcen;
    private String descen;
    private String arecen;
    private String proval;
    private String actval;
    private Integer idper;
    private String nomper;
    private String apeper;
    private String estval;
    private CentroCosto centrocosto = new CentroCosto();
    private Personal personal = new Personal();

}
