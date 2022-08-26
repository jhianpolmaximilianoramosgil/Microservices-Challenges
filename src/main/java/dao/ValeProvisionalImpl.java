package dao;

import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import modelo.ValeProvisional;

public class ValeProvisionalImpl extends Conexion implements ICRUD<ValeProvisional> {

    DateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public void registrar(ValeProvisional provisional) throws Exception {
        String sql = "insert into ValeProvisional(IMPVAL,FECVAL, CODCEN, PROVAL, ACTVAL, IDPER, ESTVAL) values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setDouble(1, provisional.getImpval());
            ps.setString(2, formato.format(provisional.getFecval()));
            ps.setString(3, provisional.getCodcen());
            ps.setString(4, provisional.getProval());
            ps.setString(5, provisional.getActval());
            ps.setInt(6, provisional.getPersonal().getIdper());
            ps.setString(7, "A");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error al Registrar ValeProvisionalImpl " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void modificar(ValeProvisional provisional) throws Exception {
        String sql = "update ValeProvisional set IMPVAL=?, FECVAL=?, CODCEN=?,PROVAL=?,ACTVAL=?,IDPER=?,ESTVAL=? where IDVAL=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setDouble(1, provisional.getImpval());
            ps.setString(2, formato.format(provisional.getFecval()));
            ps.setString(3, provisional.getCodcen());
            ps.setString(4, provisional.getProval());
            ps.setString(5, provisional.getActval());
             ps.setInt(6, provisional.getPersonal().getIdper());
            ps.setString(7, "A");
            ps.setInt(8, provisional.getIdval());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error al Modificar ValeProvisionalImpl: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(ValeProvisional provisional) throws Exception {
        String sql = "delete from vValeProvisional1 where IDVAL=?";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, provisional.getIdval());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en eliminarImpl" + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    public void DeleteEstado(ValeProvisional provisional) throws Exception {
        String sql = "update vValeProvisional1 set ESTVAL=? where IDVAL=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, "I");
            ps.setInt(2, provisional.getIdval());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en EliminarEstadoD " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }


    public List listarTodos(int tipo) throws Exception {
        List<ValeProvisional> listadoVal = null;
        ValeProvisional provisional;
        String sql = "";
        
        switch (tipo) {
            case 1:
                sql = "SELECT * FROM vValeProvisional WHERE ESTVAL='A'";
                break;
            case 2:
                sql = "SELECT * FROM vValeProvisional WHERE ESTVAL='I'";
                break;
            case 3:
                sql = "SELECT * FROM vValeProvisional";
                break;
        }   
        try {
            listadoVal = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                provisional = new ValeProvisional();
                provisional.setIdval(rs.getInt("IDVAL"));
                provisional.setImpval(rs.getDouble("IMPVAL"));
                provisional.setFecval(rs.getDate("FECVAL"));
                provisional.setDescen(rs.getString("DESCEN"));
                provisional.setCodcen(rs.getString("CODCEN"));
                provisional.setArecen(rs.getString("ARECEN"));
                provisional.setProval(rs.getString("PROVAL"));
                provisional.setActval(rs.getString("ACTVAL"));
                provisional.setNomper(rs.getString("NOMPER"));
                provisional.setApeper(rs.getString("APEPER"));           
                listadoVal.add(provisional);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en listarTodosImpl:" + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listadoVal;
    }



    @Override
    public List<ValeProvisional> listarTodos() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
