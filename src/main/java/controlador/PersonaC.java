package controlador;

import dao.PersonaImpl;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import modelo.Personal;
import org.primefaces.PrimeFaces;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;
import servicios.Reporte;
import lombok.Data;
import static org.codehaus.groovy.ast.tools.GeneralUtils.args;
import servicios.EmailS;
import servicios.ReniecS;
import servicios.SmsS;

@Data
//import servicios.Reporte;

//Notación CDI
@Named(value = "personaC")
//@Dependent
@SessionScoped
public class PersonaC implements Serializable {

    private Personal per;
    private PersonaImpl dao;
    private List<Personal> listadoPer;
    private int tipo = 1;
    private ExcelOptions excelOpt;
    private PDFOptions pdfOpt;
    private boolean enable = true;

    public PersonaC() {
        per = new Personal();
        dao = new PersonaImpl();
        listadoPer = new ArrayList<>();

    }

//    public void buscarDNI() throws Exception {
//        try {
//            ReniecS.buscarDni(per);
//             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, " OK", "DNI  encontrado"));
//        } catch (NullPointerException e) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, " ERROR", "El DNI no existe o el servidor a fallado"));
//            enable = false;
//        }
//    }
    public void buscarDNI() {
        try {
            ReniecS.buscarDni(per);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Busqueda", "DNI no encontrado"));
            System.out.println("error en Busqueda DNI" + e.getMessage());
        }
    }

    public void registrar() throws Exception {
        try {
            if (!dao.existe(per, listadoPer)) {
                per.setNombre(caseMayuscula(per.getNombre()));
                per.setApellido(caseMayuscula(per.getApellido()));
                per.setDomper(caseMayuscula(per.getDomper()));
                per.setEmail(camelMinuscula(per.getEmail()));
                dao.registrar(per);
                dao.registerPWD(per);
//                SmsS.enviarSms(per);
                EmailS.enviarContraseña(per);
                PrimeFaces.current().ajax().update("form");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Registrado con ÉXITO"));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Correo enviado con ÉXITO"));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Mensaje de texto enviado con ÉXITO"));
                limpiar();
                listar();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, " ERROR", "El DNI ya existe"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Falta Completar Datos"));
            System.out.println("Error en RegistrarC " + e.getMessage());

        }

        per = new Personal();
        listar();
    }

    public void modificar() throws Exception {
        try {
            per.setNombre(caseMayuscula(per.getNombre()));
            per.setApellido(caseMayuscula(per.getApellido()));
//            per.setDomper(caseMayuscula(per.getDomper()));
            per.setEmail(camelMinuscula(per.getEmail()));
            dao.modificar(per);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Modificado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en modificarC " + e.getMessage());
        }
    }

    public void eliminar() throws Exception {
        try {
            dao.eliminar(per);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "OK", "Eliminado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en eliminarC " + e.getMessage());
        }
    }

    public void DeleteEstado() throws Exception {
        try {
            dao.DeleteEstado(per);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "OK", "Eliminado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en eliminarEstadoC " + e.getMessage());
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
        per = new Personal();
    }

    public void listar() {
        try {
            listadoPer = dao.listarTodos(tipo);
        } catch (Exception e) {
            System.out.println("Error en listarC " + e.getMessage());
        }
    }

    public void reportePersona() throws Exception {
        Reporte report = new Reporte();
        try {
            Map<String, Object> parameters = new HashMap();
            report.exportarPDFGlobal(parameters, "Person.jasper", "ListadoPersonal(20-09-2021.pdf");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "PDF GENERADO", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR AL GENERAR PDF", null));
            System.out.println("Error en PersonaC/reportePersona : " + e.getMessage());
        }
    }
    public void reportePersonal() throws Exception {
        try {
            Reporte reporte = new Reporte();
            FacesContext facescontext = FacesContext.getCurrentInstance();
            ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
            String root = servletcontext.getRealPath("reportes/Monitor.jasper");
            reporte.ReportePdf(root);
            FacesContext.getCurrentInstance().responseComplete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "REPORTE GENERADO"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "AL GENERAR REPORTE"));
            System.out.println("Error en reportePersonal PersonaC " + e.getMessage());
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
