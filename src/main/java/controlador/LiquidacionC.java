package controlador;

import dao.LiquidacionImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import modelo.Liquidacion;
import org.primefaces.PrimeFaces;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;
import servicios.Reporte;
import lombok.Data;
import modelo.ValeProvisional;

@Data
//import servicios.Reporte;

//Notación CDI
@Named(value = "LiquidacionC")
//@Dependent
@SessionScoped
public class LiquidacionC implements Serializable {

    private Liquidacion liq;
    private LiquidacionImpl dao;
    private List<Liquidacion> listadoLiq;
    private int tipo = 1;
    private ExcelOptions excelOpt;
    private PDFOptions pdfOpt;
    private ValeProvisional provisional;

    public LiquidacionC() {
        liq = new Liquidacion();
        dao = new LiquidacionImpl();
        listadoLiq = new ArrayList<>();
        provisional = new ValeProvisional();
    }

    public void registrar() throws Exception {
        try {
            if (!dao.existe(liq, listadoLiq)) {
                dao.registrar(liq);
                PrimeFaces.current().ajax().update("form");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Registrado con ÉXITO"));
                limpiar();
                listar();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, " ERROR", "El DNI ya existe"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Falta Completar Datos"));
            System.out.println("Error en Registrar LiquidacionC " + e.getMessage());

        }

        liq = new Liquidacion();
        listar();
    }

    public void modificar() throws Exception {
        try {
            dao.modificar(liq);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Modificado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en modificar LiquidacionC " + e.getMessage());
        }
    }

    public void DeleteEstado() throws Exception {
        try {
            dao.DeleteEstado(liq);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "OK", "Eliminado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en DeleteEstado LiquidacionC " + e.getMessage());
        }
    }

    
      public void reporteLiquidación() throws Exception {
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
            System.out.println("Error en reporteLiquidaciónC " + e.getMessage());
        }
    } 
    
    
    
    
    
    
    
    
    

    public void limpiar() {
        liq = new Liquidacion();
    }

    public void listar() {
        try {
            listadoLiq = dao.listarTodos(tipo);
        } catch (Exception e) {
            System.out.println("Error en listar LiquidacionC " + e.getMessage());
        }
    }

    public void reportePersona() throws Exception {
        Reporte report = new Reporte();
        try {
            Map<String, Object> parameters = new HashMap();
            report.exportarPDFGlobal(parameters, "Li.jasper", "ListadoLiquidación(20-09-2021.pdf");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "PDF GENERADO", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR AL GENERAR PDF", null));
            System.out.println("Error en LiquidacionC/reportePersona : " + e.getMessage());
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
