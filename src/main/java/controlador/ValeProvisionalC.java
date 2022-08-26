package controlador;

import dao.Conexion;
import dao.PersonaImpl;
import org.primefaces.PrimeFaces;
import dao.ValeProvisionalImpl;
import java.io.File;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.List;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.ValeProvisional;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;

//Reportes con parámetros import

import servicios.Reporte;
import modelo.Personal;
import lombok.Data;

@Data

//Notación CDI
@Named(value = "ValeProvisionalC")
//@Dependent
@SessionScoped
public class ValeProvisionalC implements Serializable {

    private ValeProvisional provisional;
    private ValeProvisionalImpl dao;
    private List<ValeProvisional> listadoValeProvisional;
    private int tipo = 1;
    private ExcelOptions excelOpt;
    private PDFOptions pdfOpt;

    public ValeProvisionalC() {
        provisional = new ValeProvisional();
        dao = new ValeProvisionalImpl();
        customizationOptions();
    }

    public void registrar() throws Exception {
        try {
//            provisional.setNomper(dao.obtenerNomper(provisional.getNomper()));
            dao.registrar(provisional);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Registrado con Exito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en RegistrarC " + e.getMessage());
        }
    }

    public void modificar() throws Exception {
        try {
            dao.modificar(provisional);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Modificado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en modificarC " + e.getMessage());
        }
    }

    public void eliminar() throws Exception {
        try {
            dao.eliminar(provisional);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "OK", "Eliminado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en eliminarC " + e.getMessage());
        }
    }

    public void DeleteEstado() throws Exception {
        try {
            dao.DeleteEstado(provisional);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "OK", "Eliminado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en eliminarEstadoC " + e.getMessage());
        }
    }

    public void limpiar() {
        provisional = new ValeProvisional();
    }

    public void listar() {
        try {
            listadoValeProvisional = dao.listarTodos(tipo);
        } catch (Exception e) {
            System.out.println("Error en listarC " + e.getMessage());
        }
    }

    public void reporteValeProvisiona() throws Exception {
        Reporte report = new Reporte();
        try {
            Map<String, Object> parameters = new HashMap();
            report.exportarPDFGlobal(parameters, "ValeO.jasper", "ListadoValeProvisional(08-10-2021.pdf");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "PDF GENERADO", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR AL GENERAR PDF", null));
            System.out.println("Error en ValeProvisionalC/reporteValeProvisional : " + e.getMessage());
        }
    }

    public void reporteValeProvisional() throws Exception {
        try {
            Reporte reporte = new Reporte();
            FacesContext facescontext = FacesContext.getCurrentInstance();
            ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
            String root = servletcontext.getRealPath("reportes/ValeO.jasper");
            reporte.ReportePdf(root);
            FacesContext.getCurrentInstance().responseComplete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CORRECTO", "REPORTE GENERADO"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "AL GENERAR REPORTE"));
            System.out.println("Error en reporteValeProvisionalC " + e.getMessage());
        }
    }

    public void reporteVale() throws Exception {
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
            System.out.println("Error en reporteValeProvisionalC " + e.getMessage());
        }
    }

    public void reporteIdVal(ValeProvisional provisional) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        try {
            Reporte reporte = new Reporte();
            FacesContext facescontext = FacesContext.getCurrentInstance();
            ServletContext servletcontext = (ServletContext) facescontext.getExternalContext().getContext();
            String root = servletcontext.getRealPath("/reportes/ValeO.jasper");
            String id = String.valueOf(provisional.getIdval());
            reporte.reporteIdVal(root, id);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            System.out.println("Error en reporteIdVal ValeProvicionalC " + e.getMessage());
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
