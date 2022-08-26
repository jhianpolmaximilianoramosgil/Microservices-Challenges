package dao;

//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.Liquidacion;
import modelo.ValeProvisional;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;

public class LiquidacionImpl extends Conexion implements ICRUD<Liquidacion> {


    private DateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

    public static Date stringToFecha(String fecha) throws ParseException {
        return fecha != null ? new SimpleDateFormat("dd-MM-yyyy").parse(fecha) : null;
    }

    @Override
    public void registrar(Liquidacion liq) throws Exception {
        String sql = "insert into LIQUIDACION (IDVAL,MOTLIQ,FECLIQ,CODCEN,FORLIQ,GASLIQ, SALLIQ,ESTLIQ) values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, liq.getProvisional().getIdval());
            ps.setString(2, liq.getMOTLIQ());
            ps.setString(3, formato.format(liq.getFECLIQ()));
            ps.setString(4, liq.getCODCEN());
            ps.setString(5, liq.getFORLIQ());
            ps.setDouble(6, liq.getGASLIQ());
            ps.setDouble(7, liq.getSALLIQ());
            ps.setString(8, "A");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error al Registrar la LiqduidaciónImpl  " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    public int obtenerUltimoId() {
        try {
            PreparedStatement ps1 = this.conectar().prepareStatement("SELECT MAX(L.IDLIQ) as IDLIQ FROM LIQUIDACION L");
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                return rs.getInt("IDLIQ");
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en obtenerUltimoIdImpl" + e.getMessage());
        }
        return -1;
    }

    @Override
    public void modificar(Liquidacion liq) throws Exception {
        String sql = "update LIQUIDACION set IDVAL=?, MOTLIQ=?, FECLIQ=?,CODCEN=?,FORLIQ=?,GASLIQ=?,SALLIQ=?, ESTLIQ=? where IDLIQ=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, liq.getProvisional().getIdval());
            ps.setString(2, liq.getMOTLIQ());
            ps.setString(3, formato.format(liq.getFECLIQ()));
            ps.setString(4, liq.getCODCEN());
            ps.setString(5, liq.getFORLIQ());
            ps.setDouble(6, liq.getGASLIQ());
            ps.setDouble(7, liq.getSALLIQ());
            ps.setString(8, "A");
            ps.setInt(9, liq.getIDLIQ());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error al Modificar LiquidacionImpl: " + e.getMessage());
        }
    }


    public void DeleteEstado(Liquidacion liq) throws Exception {
        String sql = "update LIQUIDACION set ESTLIQ=? where IDLIQ=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, "I");
            ps.setInt(2, liq.getIDLIQ());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en DeleteEstadoImpl " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    public List listarTodos(int tipo) throws Exception {
        List<Liquidacion> listadoLiq = null;
        Liquidacion liq;
        String sql = "";

        switch (tipo) {
            case 1:
                sql = "SELECT * FROM vLIQUIDACION  WHERE ESTLIQ='A'";
                break;
            case 2:
                sql = "SELECT * FROM vLIQUIDACION  WHERE ESTLIQ='I'";
                break;
            case 3:
                sql = "SELECT * FROM vLIQUIDACION";
                break;
        }
        try {
            listadoLiq = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                liq = new Liquidacion();
                liq.setIDLIQ(rs.getInt("IDLIQ"));
                liq.setNOMPER(rs.getString("NOMPER"));
                liq.setAPEPER(rs.getString("APEPER"));
                liq.setDNIPER(rs.getString("DNIPER"));
                liq.setFECVAL(rs.getDate("FECVAL"));
                liq.setIDVAL(rs.getInt("IDVAL"));
                liq.setMOTLIQ(rs.getString("MOTLIQ"));
                liq.setFECLIQ(rs.getDate("FECLIQ"));
                liq.setDESCEN(rs.getString("DESCEN"));
                liq.setCODCEN(rs.getString("CODCEN"));
                liq.setARECEN(rs.getString("ARECEN"));
                liq.setFORLIQ(rs.getString("FORLIQ"));
                liq.setImpval(rs.getDouble("IMPVAL"));
                liq.setGASLIQ(rs.getDouble("GASLIQ"));
                liq.setSALLIQ(rs.getDouble("SALLIQ"));
                listadoLiq.add(liq);
            }
            rs.close();
            st.close();

        } catch (Exception e) {
            System.out.println("Error en listarTodosImpl:" + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listadoLiq;
    }

    public boolean existe(Liquidacion liq, List<Liquidacion> listaLiq) {
        for (Liquidacion liqui : listaLiq) {
            if (liqui.getDNIPER().equals(liqui.getDNIPER())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void eliminar(Liquidacion obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Liquidacion> listarTodos() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
