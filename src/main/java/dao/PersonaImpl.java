package dao;

//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import modelo.Personal;


public class PersonaImpl extends Conexion implements ICRUD<Personal> {

    @Override
    public void registrar(Personal per) throws Exception {

        String sql = "insert into PERSONAL (NOMPER,APEPER,DNIPER,CELPER,EMAPER, CODUBI, DOMPER, SEXPER,CARPER, ESTPER) values (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, per.getNombre());
            ps.setString(2, per.getApellido());
            ps.setString(3, per.getDni());
            ps.setString(4, per.getCelular());
            ps.setString(5, per.getEmail());
            ps.setString(6, per.getCodubi());
            ps.setString(7, per.getDomper());
            ps.setString(8, per.getSexo());
            ps.setString(9, per.getCargo());
            ps.setString(10, "A");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error al Ingresar Persona Dao " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }
    

    public void registerPWD(Personal per) throws Exception {
        String sql = "insert into USUARIO (USUUSU,PWDUSU,LEVUSU,ESTUSU) values (?,?,?,?)";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, per.getDni());
            ps.setString(2, per.getPwdper());
            ps.setInt(3, 2);
            ps.setString(4, "A");
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en registerPWD" + e.getMessage());
        } finally {
            this.cerrar();
        }
    }


    @Override
    public void modificar(Personal per) throws Exception {
        String sql = "update PERSONAL set NOMPER=?, APEPER=?,DNIPER=?,CELPER=?,EMAPER=?, CODUBI=?,DOMPER=?,SEXPER=?,CARPER=?, ESTPER=? where IDPER=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, per.getNombre());
            ps.setString(2, per.getApellido());
            ps.setString(3, per.getDni());
            ps.setString(4, per.getCelular());
            ps.setString(5, per.getEmail());
            ps.setString(6, per.getCodubi());
            ps.setString(7, per.getDomper());
            ps.setString(8, per.getSexo());
            ps.setString(9, per.getCargo());
            ps.setString(10, "A");
            ps.setInt(11, per.getIdper());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error al Modificar PersonaImpl: " + e.getMessage());
        }
    }
 
    
    
    @Override
    public void eliminar(Personal per) throws Exception {
        String sql = "delete from PERSONAL where IDPER=?";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setInt(1, per.getIdper());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en eliminarImpl" + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    public void DeleteEstado(Personal per) throws Exception {
        String sql = "update Personal set ESTPER=? where IDPER=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, "I");
            ps.setInt(2, per.getIdper());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en EliminarEstadoImpl " + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    public List listarTodos(int tipo) throws Exception {
        List<Personal> listadoPer = null;
        Personal pers;
        String sql = "";
        switch (tipo) {
            case 1:
                sql = "SELECT * FROM vPersonal WHERE ESTPER='A'";
                break;
            case 2:
                sql = "SELECT * FROM vPersonal WHERE ESTPER='I'";
                break;
            case 3:
                sql = "SELECT * FROM vPersonal";
                break;
        }
        try {
            listadoPer = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                pers = new Personal();
                pers.setIdper(rs.getInt("IDPER"));
                pers.setNombre(rs.getString("NOMPER"));
                pers.setApellido(rs.getString("APEPER"));
                pers.setDni(rs.getString("DNIPER"));
                pers.setCelular(rs.getString("CELPER"));
                pers.setEmail(rs.getString("EMAPER"));
                pers.setCodubi(rs.getString("CODUBI"));
                pers.setDisubi(rs.getString("DISUBI"));
                pers.setProubi(rs.getString("PROUBI"));
                pers.setDepubi(rs.getString("DEPUBI"));
                pers.setDomper(rs.getString("DOMPER"));
                pers.setSexo(rs.getString("SEXPER"));
                pers.setCargo(rs.getString("CARPER"));
                listadoPer.add(pers);
            }
            rs.close();
            st.close();

        } catch (Exception e) {
            System.out.println("Error en listarTodosImpl:" + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listadoPer;
    }

    public boolean existe(Personal personal, List<Personal> listaPersonal) {
        for (Personal per : listaPersonal) {
            if (personal.getDni().equals(per.getDni())) {
                return true;
            }
        }
        return false;
    }

    public List<Personal> listarTodos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
