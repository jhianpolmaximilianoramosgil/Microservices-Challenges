package dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DashboardImpl extends Conexion  {
    
    public List<Number> dashboardPersonal() throws Exception {
        this.conectar();
        List<Number> lista = new ArrayList();
        try {
            String sql = "SELECT COUNT(CASE ESTPER WHEN 'A' THEN 'A' END) AS ACTIVO , COUNT(CASE ESTPER WHEN 'I' THEN 'I' END) AS INACTIVO FROM PERSONAL";
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                System.out.println("Existen datos");
                lista.add(rs.getInt("ACTIVO"));
                lista.add(rs.getInt("INACTIVO"));
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en dashboardPersonalD " + e.getMessage());
        } finally {
            this.cerrar();
        }
        return lista;
    }
    
    public List<Number> dashboardCargo() throws Exception {
        this.conectar();
        List<Number> lista = new ArrayList<>();
        try {
            String sql = "SELECT COUNT(CASE CARPER WHEN 'Monitor' THEN 'Monitor' END) AS MONITORES, COUNT(CASE CARPER WHEN 'Jefe de Área' THEN 'Jefe de Área' END) AS JEFEAREA, COUNT(CASE CARPER WHEN 'Tesorero' THEN 'Tesorero' END) AS TESORERO  FROM PERSONAL";
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                System.out.println("Existe la cantidad deL Personal");
                 lista.add(rs.getInt("MONITORES"));
                lista.add(rs.getInt("TESORERO"));
                lista.add(rs.getInt("JEFEAREA"));
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en el dashboardCargoD "+ e.getMessage());
        } finally {
            this.cerrar();
        }
        return lista;
    } 
    
}
