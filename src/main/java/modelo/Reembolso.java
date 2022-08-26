package modelo;

import java.util.Date;
import lombok.Data;
import java.util.GregorianCalendar;

@Data

public class Reembolso {

    private Integer IDREE;
    private Integer IDPER;
    private String NOMPER;
    private String DNIPER;
    private String MOTREE;
    private Date FECREE = GregorianCalendar.getInstance().getTime();
    private String  ARERRE;
    private String DESCEN;
    private String CODCEN;
    private String ARECEN;
    private Integer IDLIQ;
    private String FORREE;
    private String PAGREE;
    private String NAHREE;
    private String NCUREE;
    private Double SALREE;
    private String ESTREE;
    
    private Personal personal = new Personal();
    private Liquidacion liq = new Liquidacion();



    public Reembolso() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}