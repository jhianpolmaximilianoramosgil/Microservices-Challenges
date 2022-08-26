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
import modelo.Liquidacion;
import modelo.Reembolso;

public class ReembolsoImpl extends Conexion implements ICRUD<Reembolso> {

    DateFormat formato = new SimpleDateFormat("dd-MM-yyyy");


    @Override
    public void registrar(Reembolso reembolso) throws Exception {
        String sql = "insert into REEMBOLSO (IDLIQ, MOTREE,FECREE, CODCEN, FORREE,PAGREE,NAHREE,NCUREE, SALREE, ESTREE) values (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, reembolso.getLiq().getIDLIQ());
            ps.setString(2, reembolso.getMOTREE());
            ps.setString(3, formato.format(reembolso.getFECREE()));
            ps.setString(4, reembolso.getCODCEN());
            ps.setString(5, reembolso.getFORREE());
             ps.setString(6, reembolso.getPAGREE());
            ps.setString(7, reembolso.getNAHREE());
            ps.setString(8, reembolso.getNCUREE());
            ps.setDouble(9, reembolso.getSALREE());
            ps.setString(10, "A");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error al Registrar ReembolsoImpl " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void modificar(Reembolso reembolso) throws Exception {
        String sql = "update REEMBOLSO set IDLIQ=?, MOTREE=?, FECREE=?,CODCEN=?,FORREE=?,PAGREE=?,NAHREE=?,NCUREE=?,SALREE=?,ESTREE=? where IDREE=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, reembolso.getLiq().getIDLIQ());
            ps.setString(2, reembolso.getMOTREE());
            ps.setString(3, formato.format(reembolso.getFECREE()));
            ps.setString(4, reembolso.getCODCEN());
            ps.setString(5, reembolso.getFORREE());
             ps.setString(6, reembolso.getPAGREE());
            ps.setString(7, reembolso.getNAHREE());
            ps.setString(8, reembolso.getNCUREE());
            ps.setDouble(9, reembolso.getSALREE());
            ps.setString(10, "A");
            ps.setInt(11, reembolso.getIDREE());
            ps.executeUpdate();
            ps.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error al Modificar ReembolsoImpl: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Reembolso reembolso) throws Exception {
        String sql = "delete from REEMBOLSO where IDREE=?";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, reembolso.getIDREE());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en eliminarImpl" + e.getMessage());
        } finally {
            this.cerrar();
        }
    }
    
    
    public void DeleteEstado(Reembolso reembolso) throws Exception {
        String sql = "update REEMBOLSO set ESTREE=? where IDREE=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, "I");
            ps.setInt(2, reembolso.getIDREE());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en DeleteEstado ReembolsoImpl " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }


    public List listarTodos(int tipo) throws Exception {
        List<Reembolso> listadoRee = null;
        Reembolso reembolso;
        String sql = "";
        switch (tipo) {
            case 1:
                sql = "SELECT * FROM vREEMBOLSO WHERE ESTREE='A'";
                break;
            case 2:
                sql = "SELECT * FROM vREEMBOLSO WHERE ESTREE='I'";
                break;
            case 3:
                sql = "SELECT * FROM vREEMBOLSO";
                break;
        }
        try {
            listadoRee = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                reembolso = new Reembolso();
                reembolso.setIDREE(rs.getInt("IDREE"));
                reembolso.setNOMPER(rs.getString("NOMPER"));
                reembolso.setDNIPER(rs.getString("DNIPER"));
                reembolso.setMOTREE(rs.getString("MOTREE"));
                reembolso.setFECREE(rs.getDate("FECREE"));
                reembolso.setDESCEN(rs.getString("DESCEN"));
                reembolso.setCODCEN(rs.getString("CODCEN"));
                reembolso.setARECEN(rs.getString("ARECEN"));
                reembolso.setIDLIQ(rs.getInt("IDLIQ"));
                reembolso.setFORREE(rs.getString("FORREE"));
                reembolso.setPAGREE(rs.getString("PAGREE"));
                reembolso.setNAHREE(rs.getString("NAHREE"));
                reembolso.setNCUREE(rs.getString("NCUREE"));
                reembolso.setSALREE(rs.getDouble("SALREE"));
                listadoRee.add(reembolso);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println("Error en listarTodosImpl:" + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listadoRee;
    }

    @Override
    public List<Reembolso> listarTodos() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 

}
