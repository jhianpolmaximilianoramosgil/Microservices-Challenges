package controlador;

import dao.UbigeoDao;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Data;
import modelo.Ubigeo;

@Data

//Notación CDI
@Named(value = "ubigeoC")
//@Dependent
@SessionScoped
public class UbigeoC implements Serializable {

    private Ubigeo ubigeo;
    private UbigeoDao dao;
    private List<Ubigeo> listadoUbigeo;
 

    public UbigeoC() {
        ubigeo = new Ubigeo();
        dao = new UbigeoDao();
        listadoUbigeo = new ArrayList<>();
        
        
    }

    public void listar() {
        try {
            listadoUbigeo = dao.listarTodos();
        } catch (Exception e) {
            System.out.println("Error en listar ubigeoC " + e.getMessage());
        }
    }

@PostConstruct
    public void construir() {
        listar();
    }
}
