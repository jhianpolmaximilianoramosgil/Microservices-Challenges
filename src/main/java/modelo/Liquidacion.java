package modelo;

import java.util.Date;
import java.util.GregorianCalendar;
import lombok.Data;

@Data

public class Liquidacion {
    
    private Integer IDLIQ;
    private Integer IDPER;
    private String NOMPER;
    private String APEPER;
    private String DNIPER;
    private Integer IDVAL;
    private Date FECVAL;
    private Double impval;
    private String MOTLIQ;
    private Date FECLIQ = GregorianCalendar.getInstance().getTime();
    private String DESCEN;
    private String CODCEN;
    private String ARECEN;
    private String FORLIQ;
    private String IMPVAL;
    private Double GASLIQ;
    private Double SALLIQ;
    private String ESTLIQ;
    
    private Personal personal = new Personal();
    private ValeProvisional provisional = new ValeProvisional();
    private CentroCosto centrocosto = new CentroCosto();

   
}      

