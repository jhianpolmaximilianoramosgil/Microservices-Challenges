package controlador;

import dao.CentroCostoDao;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Data;
import modelo.CentroCosto;

@Data

//Notación CDI
@Named(value = "centrocostoC")
//@Dependent
@SessionScoped
public class CentroCostoC implements Serializable {

    private CentroCosto centrocosto;
    private CentroCostoDao dao;
    private List<CentroCosto> listadoCentroCosto;
 

    public CentroCostoC() {
        centrocosto = new CentroCosto();
        dao = new CentroCostoDao();
    }

    public void listar() {
        try {
            listadoCentroCosto = dao.listarTodos();
        } catch (Exception e) {
            System.out.println("Error en listar CentrCostoC " + e.getMessage());
        }
    }

@PostConstruct
    public void construir() {
        listar();
    }
}
