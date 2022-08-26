package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Usuario;

public class UsuarioD extends Conexion {

public static Boolean logueo = false;
    public static int nivel = 0;
      public static String rol = "";

    public Usuario login(Usuario usuario, String PWDUSU) throws Exception {
        Usuario user = new Usuario();
        String sql = "select USUUSU, PWDUSU, LEVUSU from USUARIO where USUUSU=? and PWDUSU=? ";
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ps.setString(1, usuario.getUSUUSU());
//            ps.setString(2, usuario.getPWDUSU());
            ps.setString(2, PWDUSU);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setUSUUSU(rs.getString("USUUSU"));
                user.setPWDUSU(rs.getString("PWDUSU"));
                user.setLEVUSU(rs.getInt("LEVUSU"));
                logueo = true;
            } else {
                logueo = false;
            }
            ps.close();
            rs.close();
            return user;
        } catch (Exception e) {
            System.out.println("Errorr en login_D " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public int loginNivel(Usuario usuario) throws Exception {
        String sql = "select USUUSU, PWDUSU, LEVUSU from USUARIO where USUUSU=? and PWDUSU=? ";
        try {
            PreparedStatement ps = (PreparedStatement) this.conectar().prepareStatement(sql);
            ps.setString(1, usuario.getUSUUSU());
            ps.setString(2, usuario.getPWDUSU());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nivel = rs.getInt("LEVUSU");
                logueo = true;
            } else {
                logueo = false;
            }
            ps.close();
            rs.close();
            return nivel;
        } catch (Exception e) {
            System.out.println("Errorr en loginNivel_D " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

  public String loginRol(Usuario usuario) throws Exception {
        String sql = "select USUUSU, PWDUSU, CARPER from vRoLUsuario where USUUSU=? and PWDUSU=? ";
        try {
            PreparedStatement ps = (PreparedStatement) this.conectar().prepareStatement(sql);
            ps.setString(1, usuario.getUSUUSU());
            ps.setString(2, usuario.getPWDUSU());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rol = rs.getString("CARPER");
                logueo = true;
            } else {
                logueo = false;
            }
            ps.close();
            rs.close();
            return rol;
        } catch (Exception e) {
            System.out.println("Errorr en loginRol_D " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }    
  
}