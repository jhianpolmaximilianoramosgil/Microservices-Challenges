package dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Ubigeo;

public class UbigeoDao extends Conexion  {
    
    public List listarTodos() throws Exception {
        List<Ubigeo> listadoUbi = null;
        Ubigeo ubigeo;
        String sql = "select * from UBIGEO";
        try {
            listadoUbi = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ubigeo = new Ubigeo();
                ubigeo.setCODUBI(rs.getString("CODUBI"));
                ubigeo.setPROUBI(rs.getString("PROUBI"));
                ubigeo.setDEPUBI(rs.getString("DEPUBI"));
                ubigeo.setDISUBI(rs.getString("DISUBI"));
                listadoUbi.add(ubigeo);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en listarTodos UbigeoDao:" + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listadoUbi;
    }
}
