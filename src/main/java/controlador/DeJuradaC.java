package controlador;

import dao.DeJuradaImpl;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Data;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import modelo.DeJurada;
import modelo.ValeProvisional;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;
import servicios.Reporte;

@Data
//import servicios.Reporte;

//Notación CDI
@Named(value = "DeJuradaC")
//@Dependent
@SessionScoped
public class DeJuradaC implements Serializable {

    private DeJurada declaracion;
    private DeJuradaImpl dao;
    private List<DeJurada> listadoDec;
    private int tipo = 1;
    private ExcelOptions excelOpt;
    private PDFOptions pdfOpt;
    private boolean enable = true;

    public DeJuradaC() {
        declaracion = new DeJurada();
        dao = new DeJuradaImpl();
    }

    public void registrar() throws Exception {
        try {
            dao.registrar(declaracion);
            declaracion.setPROUBI(caseMayuscula(declaracion.getPROUBI()));
            declaracion.setNomper(caseMayuscula(declaracion.getNomper()));
            declaracion.setAPEPER(caseMayuscula(declaracion.getAPEPER()));
            declaracion.setDISUBI(caseMayuscula(declaracion.getDISUBI()));
            declaracion.setPROUBI(caseMayuscula(declaracion.getPROUBI()));
            declaracion.setDEPUBI(caseMayuscula(declaracion.getDEPUBI()));
            declaracion.setDomicilio(camelMinuscula(declaracion.getDomicilio()));
            declaracion.setCONDEJ(camelMinuscula(declaracion.getCONDEJ()));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Registrado con Exito"));
            limpiar();
            listar();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Error al registrar"));
            System.out.println("Error en Registrar DeJuradaC " + e.getMessage());
        }
    }

    public void modificar() throws Exception {
        try {
            dao.modificar(declaracion);
            declaracion.setPROUBI(caseMayuscula(declaracion.getPROUBI()));
            declaracion.setNomper(caseMayuscula(declaracion.getNomper()));
            declaracion.setAPEPER(caseMayuscula(declaracion.getAPEPER()));
            declaracion.setDISUBI(caseMayuscula(declaracion.getDISUBI()));
            declaracion.setPROUBI(caseMayuscula(declaracion.getPROUBI()));
            declaracion.setDEPUBI(caseMayuscula(declaracion.getDEPUBI()));
            declaracion.setDomicilio(camelMinuscula(declaracion.getDomicilio()));
            declaracion.setCONDEJ(camelMinuscula(declaracion.getCONDEJ()));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Modificado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en modificar DeJuradaC " + e.getMessage());
        }
    }
    
    
      public void reporteDeJurada() throws Exception {
        try {
            Reporte reporte = new Reporte();
            FacesContext facescontext = FacesContext.getCurrentInstance();
            ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
            String root = servletcontext.getRealPath("reportes/ValeProviO.jasper");
            reporte.ReportePdf(root);
            FacesContext.getCurrentInstance().responseComplete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "REPORTE GENERADO"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "AL GENERAR REPORTE"));
            System.out.println("Error en reporteDeJuradaC " + e.getMessage());
        }
    } 

    public void DeleteEstado() throws Exception {
        try {
            dao.DeleteEstado(declaracion);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "OK", "Eliminado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en DeleteEstado DejuradaC " + e.getMessage());
        }
    }

    public String caseMayuscula(String camelcase) {
        char ch[] = camelcase.toCharArray();
        for (int i = 0; i < camelcase.length(); i++) {
            if (i == 0 && ch[i] != ' ' || ch[i] != ' ' && ch[i - 1] == ' ') {  // Si se encuentra el primer carácter de una palabra
                if (ch[i] >= 'a' && ch[i] <= 'z') {      // Si está en minúsculas
                    ch[i] = (char) (ch[i] - 'a' + 'A');  // Convertir en mayúsculas
                }
            } // Si aparte del primer carácter cualquiera está en mayúsculas
            else if (ch[i] >= 'A' && ch[i] <= 'Z') {     // Convertir en minúsculas
                ch[i] = (char) (ch[i] + 'a' - 'A');
            }
        }
        String st = new String(ch);
        camelcase = st;
        return camelcase;
    }

    public String camelMinuscula(String camelcase) {
        char ch[] = camelcase.toCharArray();
        for (int i = 0; i < camelcase.length(); i++) {
            if (i == 0 && ch[i] != ' ' || ch[i] != ' ' && ch[i - 1] == ' ') {  // Si se encuentra el primer carácter de una palabra
                if (ch[i] >= 'A' && ch[i] <= 'Z') {      // Si está en mayúsculas
                    ch[i] = (char) (ch[i] - 'A' + 'a');  // Convertir en minúsculas
                }
            } // Si aparte del primer carácter cualquiera está en mayúsculas
            else if (ch[i] >= 'A' && ch[i] <= 'Z') {     // Convertir en minúsculas
                ch[i] = (char) (ch[i] + 'a' - 'A');
            }
        }
        String st = new String(ch);
        camelcase = st;
        return camelcase;
    }

    public void limpiar() {
        declaracion = new DeJurada();
    }

    public void listar() {
        try {
            listadoDec = dao.listarTodos(tipo);
        } catch (Exception e) {
            System.out.println("Error en listar DeJuradaC " + e.getMessage());
        }
    }
    
       public void reporteIdDej(DeJurada declaracion) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        try {
            Reporte reporte = new Reporte();
            FacesContext facescontext = FacesContext.getCurrentInstance();
            ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
            String root = servletcontext.getRealPath("/reportes/Jurada.jasper");
            String id = String.valueOf(declaracion.getId());
            reporte.reporteIdDej(root, id);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            System.out.println("Error en reporteIdDej DeJuradaC " + e.getMessage());
        }
    }

    public void customizationOptions() {
        excelOpt = new ExcelOptions();
        excelOpt.setFacetBgColor("#19C7FF");
        excelOpt.setFacetFontSize("10");
        excelOpt.setFacetFontColor("#FFFFFF");
        excelOpt.setFacetFontStyle("BOLD");
        excelOpt.setCellFontColor("#000000");
        excelOpt.setCellFontSize("8");
        excelOpt.setFontName("Verdana");

        pdfOpt = new PDFOptions();
        pdfOpt.setFacetBgColor("#19C7FF");
        pdfOpt.setFacetFontColor("#000000");
        pdfOpt.setFacetFontStyle("BOLD");
        pdfOpt.setCellFontSize("12");
        pdfOpt.setFontName("Courier");
        pdfOpt.setOrientation(PDFOrientationType.LANDSCAPE);
    }

    @PostConstruct
    public void construir() {
        listar();
    }
}
