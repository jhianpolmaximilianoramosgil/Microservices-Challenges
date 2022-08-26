package controlador;

import dao.ReembolsoImpl;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Data;
import modelo.Liquidacion;
import modelo.Reembolso;
import modelo.ValeProvisional;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
@Data
//Notación CDI
@Named(value = "ReembolsoC")
//@Dependent
@SessionScoped
public class ReembolsoC implements Serializable {

    private Reembolso reembolso;
    private ReembolsoImpl dao;
    private List<Reembolso> listadoReembolso;
     private int tipo = 1;
    private ExcelOptions excelOpt;
    private PDFOptions pdfOpt;
    private Liquidacion liq;

    public ReembolsoC() {
        reembolso = new Reembolso();
        dao = new ReembolsoImpl();
        liq = new Liquidacion();
    }

    public void registrar() throws Exception {
        try {
//            reembolso.setNomper(dao.obtenerNomper(reembolso.getNomper()));
            dao.registrar(reembolso);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Registrado con Exito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en RegistrarC " + e.getMessage());
        }
    }

    public void modificar() throws Exception {
        try {
            dao.modificar(reembolso);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Modificado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en modificarC " + e.getMessage());
        }
    }

    public void eliminar() throws Exception {
        try {
            dao.eliminar(reembolso);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "OK", "Eliminado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en eliminarC " + e.getMessage());
        }
    }


    public void DeleteEstado() throws Exception {
        try {
            dao.DeleteEstado(reembolso);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "OK", "Eliminado con éxito"));
            limpiar();
            listar();
        } catch (Exception e) {
            System.out.println("Error en eliminarEstadoC " + e.getMessage());
        }
    }

    public void limpiar() {
        reembolso = new Reembolso();
    }

    public void listar() {
        try {
            listadoReembolso = dao.listarTodos(tipo);
        } catch (Exception e) {
            System.out.println("Error en listarC " + e.getMessage());
        }
    }

   
        @PostConstruct
    public void construir() {
        listar();
    }
}
