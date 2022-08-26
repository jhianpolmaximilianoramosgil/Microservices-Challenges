package dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.CentroCosto;

public class CentroCostoDao extends Conexion  {
    
    public List listarTodos() throws Exception {
        List<CentroCosto> listadoCen = null;
        CentroCosto centrocosto;
        String sql = "select * from CENTROCOSTO";
        try {
            listadoCen = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                centrocosto = new CentroCosto();
                centrocosto.setCODCEN(rs.getString("CODCEN"));
                centrocosto.setDESCEN(rs.getString("DESCEN"));
                centrocosto.setARECEN(rs.getString("ARECEN"));
                listadoCen.add(centrocosto);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en listarTodos CentroCostoDao:" + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listadoCen;
    }
}
