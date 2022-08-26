package controlador;

import dao.UsuarioD;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import lombok.Data;
import modelo.Usuario;
import servicios.EmailS;
import servicios.EncriptarPWD;

@Data
@Named(value = "usuarioC")
@SessionScoped
public class UsuarioC implements Serializable {

    Usuario usuario;
    UsuarioD dao;
    private int captcha = 0;
    private int intentos = 0;
    private boolean bloquear = false;
    private String PWDUSU;

    //Representa a una clase
    //Objetos
    public UsuarioC() {

        usuario = new Usuario();
        dao = new UsuarioD();
    }

    public void login() throws Exception {
        try {
//            dao.login(usuario);
            dao.login(usuario, PWDUSU);
        } catch (Exception e) {
            System.out.println("Error en login_C " + e.getMessage());
        }
    }

    public void loginNivel() throws Exception {
        try {
            dao.loginNivel(usuario);
        } catch (Exception e) {
            System.out.println("Error en loginNivel_C" + e.getMessage());
        }
    }

    public void loginRol() throws Exception {
        try {
            dao.loginRol(usuario);
        } catch (Exception e) {
            System.out.println("Error en loginRol_C" + e.getMessage());
        }
    }

    public void refactorPWD() throws Exception {

        try {
            String email = usuario.getEmail();
            EmailS.refactorPWD(email);
        } catch (Exception e) {
            System.out.println("Error en refactorPWD_C" + e.getMessage());
        }
    }

    public void acceso() throws Exception {
        try {
//            usuario = dao.login(usuario);
            PWDUSU = EncriptarPWD.encriptar(usuario.getPWDUSU());
            usuario = dao.login(usuario, PWDUSU);
            this.login();
            if (dao.logueo == false) {
                intentos++;
                if (intentos == 1) {
                    setIntentos(1);
                    setCaptcha(0);
                    System.out.println("intentos igual " + intentos);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "LLEVAS 1 INTENTO FALLIDO", "Usuario Y/o Contraseña fallida"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "LE QUEDAN 2 INTENTOS", ""));
                }
                if (intentos == 2) {
                    setIntentos(2);
                    setCaptcha(1);
//                    bloquear = true;
                    System.out.println("intentos igual " + intentos);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "LLEVAS 2 INTENTO FALLIDO", "Usuario Y/o Contraseña fallida"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "LE QUEDA 1 INTENTO", ""));
                }
                if (intentos == 3) {
                    System.out.println("intentos igual " + intentos);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "LLEVAS 3 INTENTO FALLIDO", "Usuario Y/o Contraseña fallida"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "BLOQUEANDO SOFTWARE POR SEGURIDAD", ""));
                    setIntentos(3);
                    bloquear = true;
                    if (bloquear) {
                        delayTiempo();
                    }
                    if (intentos == 3) {
                        setIntentos(0);
                        setCaptcha(0);
//                        bloquear = true;
                    }
                }
            } else {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("objetoUsuario", usuario);
                this.loginRol();
                if (dao.rol != null) {
                    switch (dao.rol) {
                        case "Monitor":
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡BIENVENIDO!", "Ingreso Exitoso"));
                            FacesContext.getCurrentInstance().getExternalContext().redirect("/Sisregvg_ODAO/faces/vistaMonitor/ValeProvisional.xhtml");
                            break;
                        case "Tesorero":
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡BIENVENIDO!", "Ingreso Exitoso"));
                            FacesContext.getCurrentInstance().getExternalContext().redirect("/Sisregvg_ODAO/faces/vistaTesorero/ValeProvisional.xhtml");
                            break;
                        case "Jefe de Área":
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡BIENVENIDO!", "Ingreso Exitoso"));
                            FacesContext.getCurrentInstance().getExternalContext().redirect("/Sisregvg_ODAO/faces/vistas/Dashboard.xhtml");
                            break;

                        default:
                            break;
                    }
                    String usu = usuario.getUSUUSU();
                    EmailS.sendNotification(usu);
                    EmailS.sendIngresoUsuario(usu);
                }
            }
        } catch (Exception e) {
            System.out.println("Error en Acceso_C " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void delayTiempo() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Error en delayTiempoC " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Obtener el objeto de la sesión activa
    public static Usuario obtenerObjetoSesion() {
        return (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("objetoUsuario");
    }

    // Si la sesión no está iniciada no permitirá entrar a otra vista de la aplicación
    public void seguridadSesion() throws IOException {
        if (obtenerObjetoSesion() == null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Sisregvg_ODAO/faces/login_1.xhtml");
        }
    }

    // Cerrar y limpiar la sesión y direccionar al xhtml inicial del proyecto
    public void cerrarSesion() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Sisregvg_ODAO/faces/login_1.xhtml");
    }

    // Si la sesión está activa se redirecciona a la vista principal
    public void seguridadLogin() throws IOException {
        Usuario us = obtenerObjetoSesion();
        if (us != null) {
            FacesContext.getCurrentInstance().getExternalContext().
                    redirect("/Sisregvg_ODAO/faces/login_1.xhtml");
        }
    }

}
