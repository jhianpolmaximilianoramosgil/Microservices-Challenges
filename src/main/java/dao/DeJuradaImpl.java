package dao;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.util.List;
import modelo.DeJurada;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import modelo.Personal;

public class DeJuradaImpl extends Conexion implements ICRUD<DeJurada> {

    DateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public void registrar(DeJurada declaracion) throws Exception {
        String sql = "insert into DEJURADA (IMPDEJ,PRODEJ,CODCEN,IDPER,RECDEJ, CONDEJ,FECDEJ,ESTDEJ) values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setDouble(1, declaracion.getImporte());
            ps.setString(2, declaracion.getProyecto());
            ps.setString(3, declaracion.getCODCEN());
            ps.setInt(4, declaracion.getIDPER());
            ps.setDouble(5, declaracion.getRECDEJ());
            ps.setString(6, declaracion.getCONDEJ());
            ps.setString(7, formato.format(declaracion.getFecha()));
            ps.setString(8, "A");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error al Ingresar DeJuradaImpl " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void modificar(DeJurada declaracion) throws Exception {
        String sql = "update DEJURADA set IMPDEJ=?, PRODEJ=?,CODCEN=?,IDPER=?,RECDEJ=?, CONDEJ=?,FECDEJ=?, ESTDEJ=? where IDDEJ=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setDouble(1, declaracion.getImporte());
            ps.setString(2, declaracion.getProyecto());
            ps.setString(3, declaracion.getCODCEN());
            ps.setInt(4, declaracion.getIDPER());
            ps.setDouble(5, declaracion.getRECDEJ());
            ps.setString(6, declaracion.getCONDEJ());
            ps.setString(7, formato.format(declaracion.getFecha()));
            ps.setString(8, "A");
            ps.setInt(9, declaracion.getId());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error al Modificar DeJuradaImpl: " + e.getMessage());
        }
    }

    
        
    @Override
    public void eliminar(DeJurada declaracion) throws Exception {
        String sql = "delete from DEJURADA where IDDEJ=?";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, declaracion.getId());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en eliminar DejuradaImpl" + e.getMessage());
        } finally {
            this.cerrar();
        }
    }
    
    public void DeleteEstado(DeJurada declaracion) throws Exception {
        String sql = "update DEJURADA set ESTDEJ=? where IDDEJ=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, "I");
            ps.setInt(2, declaracion.getId());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en DeleteEstadoImpl " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }


    public List listarTodos(int tipo) throws Exception {
        List<DeJurada> listadoDec = null;
        DeJurada declaracion;
        String sql = "";
        switch (tipo) {
            case 1:
                sql = "SELECT * FROM vDEJURADA WHERE ESTDEJ='A'";
                break;
            case 2:
                sql = "SELECT * FROM vDEJURADA WHERE ESTDEJ='I'";
                break;
            case 3:
                sql = "SELECT * FROM vDEJURADA";
                break;
        }
        try {
            listadoDec = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                declaracion = new DeJurada();
                declaracion.setId(rs.getInt("IDDEJ"));
                declaracion.setImporte(rs.getDouble("IMPDEJ"));
                declaracion.setProyecto(rs.getString("PRODEJ"));
                declaracion.setDESCEN(rs.getString("DESCEN"));
                declaracion.setCODCEN(rs.getString("CODCEN"));
                declaracion.setARECEN(rs.getString("ARECEN"));
                declaracion.setNomper(rs.getString("NOMPER"));
                declaracion.setAPEPER(rs.getString("APEPER"));
                declaracion.setDni(rs.getString("DNIPER"));
                declaracion.setDISUBI(rs.getString("DISUBI"));
                declaracion.setPROUBI(rs.getString("PROUBI"));
                declaracion.setDEPUBI(rs.getString("DEPUBI"));
                declaracion.setDomicilio(rs.getString("DOMPER"));
                declaracion.setRECDEJ(rs.getDouble("RECDEJ"));
                declaracion.setCONDEJ(rs.getString("CONDEJ"));
                declaracion.setFecha(rs.getDate("FECDEJ"));
                listadoDec.add(declaracion);
            }
            rs.close();
            st.close();

        } catch (Exception e) {
            System.out.println("Error en listarTodos DeJuradaImpl :" + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listadoDec;
    }



    public List<DeJurada> listarTodos() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



 
}
