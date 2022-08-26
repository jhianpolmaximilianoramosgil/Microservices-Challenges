package servicios;

import static dao.Conexion.conectar;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;
import modelo.Personal;
import modelo.Usuario;

public class EmailS {
    
    public static void enviarContraseña(Personal per) throws Exception {
        //        Usuario  que emite o envia el correo al receptor
        String remitente = "jhianpol.ramos@gmail.com";
        //        Contraseña de el usuario emisor
        String clave = "Ramos2021...";
        //        Destinatario que va variar según ingreso de Email en la vista
        String destinatario = per.getEmail();
        //        Mensaje del correo del emisor
        String asunto = "BIENVINIEDO A SISREGVG, ENVIO DE CONTRASEÑA";
//        String contraseña = "ValleGrande2021";
        //        Envio del cuerpo del mensaje
//        String cuerpo = "Hola monitor su contraseña de ingreso a la plataforma SISREGVG es @Personal2021@";

        String cuerpo = "BUEN DÍA " + per.getNombre() + " " + per.getApellido() + "\n"
                + "\n Su usuario es: " + per.getDni() + "\n y su contraseña es: " + per.getPwdper() + "\n"
                + "\n Puedes iniciar sesión aqui: http://localhost:8080/Sisregvg_ODAO/faces/login_1.xhtml" + "\n"
                + "\n Se agradece por usar la plataforma SISREGVG";

//        //        Configuración para enviar el correo
//        Properties props = new Properties();
//        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.user", remitente);
//        props.put("mail.smtp.clave", clave);
//        Session session = Session.getDefaultInstance(props);
//        MimeMessage message = new MimeMessage(session);
        try {
            EmailS.configurateEmail(remitente, clave, destinatario, asunto, cuerpo);
//            message.setFrom(new InternetAddress(remitente));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
//            message.setSubject(asunto);
//            BodyPart texto = new MimeBodyPart();
//            texto.setText(cuerpo);
//
//            //PARA ENVIAR ARCHIVOS ADJUNTOS EN EL CORREO
////            String url = "D:\\ADOBE ILUSTRATOR\\logotipo.png";
////            BodyPart adjunto = new MimeBodyPart();
////            adjunto.setDataHandler(new DataHandler(new FileDataSource(url)));
////            adjunto.setFileName("Archivo.jpg");
//            MimeMultipart multiParte = new MimeMultipart();
////            multiParte.addBodyPart(adjunto);
//            multiParte.addBodyPart(texto);
//            message.setContent(multiParte);
//
//            Transport transport = session.getTransport("smtp");
//            transport.connect("smtp.gmail.com", remitente, clave);
//            transport.sendMessage(message, message.getAllRecipients());
//            transport.close();
        } catch (MessagingException ex) {
            ex.printStackTrace();
            System.out.println("Error en EmailS: " + ex.getMessage());
        }
    }

//(String usu
    public static void sendNotification(String usu) throws UnknownHostException, Exception {
        Usuario usuario = null;
        String sql = "SELECT\n"
                + "U.USUUSU AS USUUSU,\n"
                + "INITCAP(P.NOMPER) AS NOMPER,\n"
                + "INITCAP(P.APEPER) AS APEPER,\n"
                + "P.EMAPER AS EMAPER\n"
                + "FROM \n"
                + "USUARIO U\n"
                + "INNER JOIN PERSONAL P ON\n"
                + "U.IDPER = P.IDPER\n"
                + "WHERE \n"
                + "U.USUUSU = '" + usu + "'";
        Statement st = conectar().createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            usuario = new Usuario();
            usuario.setUSUUSU(rs.getString("USUUSU"));
            usuario.setNombre(rs.getString("NOMPER"));
            usuario.setApellido(rs.getString("APEPER"));
            usuario.setEmail(rs.getString("EMAPER"));
        }
        rs.close();
        st.close();

        // El correo gmail de envio
        String remitente = "jhianpol.ramos@gmail.com";
        String clave = "Ramos2021...";

        //Destinatario segun el usuario en el login
        String destinatario = usuario.getEmail();
        //IP, fecha y hora
        String ip = InetAddress.getLocalHost().getHostAddress();
        String fecha = new SimpleDateFormat("dd/MMMM/yyyy").format(Calendar.getInstance().getTime());
        String hora = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        //Asunto, cuerpo y la notificacion de inicio de sesion
        String asunto = "Nuevo inicio de sesión detectado de la cuenta SISREGVG";
        String cuerpo = "BUEN DÍA " + usuario.getNombre() + " " + usuario.getApellido() + "\n"
                + "Recientemente se ha iniciado sesión en la cuenta SISREGVG: " + usuario.getUSUUSU() + "\n"
                + "A continuación se muestran algunos detalles que pueden ayudar a garantizar la seguridad: " + "\n"
                + "\n País o región: Perú" + "\n"
                + "\n IP: " + ip + "\n"
                + "\n Fecha: " + fecha + "\n"
                + "\n Hora: " + hora + "\n"
                + "\n Si has sido tú, puedes descartar tranquilamente este correo electrónico." + "\n"
                + "\n Gracias, atentamente el equipo de cuentas de SISREGVG.";
        try {
            EmailS.configurateEmail(remitente, clave, destinatario, asunto, cuerpo);
        } catch (MessagingException ex) {
            System.out.println("Error en enviarNotificacion_S " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public static void sendIngresoUsuario(String usu) throws UnknownHostException, Exception {
        Usuario usuario = null;
        String sql = "SELECT\n"
                + "U.USUUSU AS USUUSU,\n"
                + "INITCAP(P.NOMPER) AS NOMPER,\n"
                + "INITCAP(P.APEPER) AS APEPER,\n"
                + "P.EMAPER AS EMAPER\n"
                + "FROM \n"
                + "USUARIO U\n"
                + "INNER JOIN PERSONAL P ON\n"
                + "U.IDPER = P.IDPER\n"
                + "WHERE \n"
                + "U.USUUSU = '" + usu + "'";
        Statement st = conectar().createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            usuario = new Usuario();
            usuario.setUSUUSU(rs.getString("USUUSU"));
            usuario.setNombre(rs.getString("NOMPER"));
            usuario.setApellido(rs.getString("APEPER"));
            usuario.setEmail(rs.getString("EMAPER"));
        }
        rs.close();
        st.close();

        // El correo gmail de envio
        String remitente = "jhianpol.ramos@gmail.com";
        String clave = "Ramos2021...";

        //Destinatario segun el usuario en el login
//        String destinatario = usuario.getEmail();
        String destinatario = "jhianpol.ramos@vallegrande.edu.pe";

        //IP, fecha y hora
        String ip = InetAddress.getLocalHost().getHostAddress();
        String fecha = new SimpleDateFormat("dd/MMMM/yyyy").format(Calendar.getInstance().getTime());
        String hora = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        //Asunto, cuerpo y la notificacion de inicio de sesion
        String asunto = "NUEVO INICIO DE SESIÓN DETECTADO DE UN USUARIO EN SISREGVG";
        String cuerpo = "Buen día ADMINISTRADOR, tenemos NOVEDADES para usted."
                + "\n Se ha detectado un nuevo iniciado sesión, por parte de la persona con nombre :" + usuario.getNombre() + " " + usuario.getApellido() + "\n"
                + "Recientemente accedió a  SISREGVG, con el usuario : " + usuario.getUSUUSU() + "\n"
                + "A continuación se muestran algunos detalles que pueden ayudar a garantizar la seguridad del Sistema: " + "\n"
                + "\n País o región: Perú" + "\n"
                + "\n El numero de IP, es :  " + ip + "\n"
                + "\n Ingresó en la Fecha :  " + fecha + "\n"
                + "\n Su ingreso se registró a la Hora :  " + hora + "\n"
                + "\n Gracias, le estaremos comunicando cualquier movimiento de usuario o fallas en el serivor en SISREGVG.";
        try {
            EmailS.configurateEmail(remitente, clave, destinatario, asunto, cuerpo);
        } catch (MessagingException ex) {
            System.out.println("Error en enviarNotificacion_S " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public static void refactorPWD(String email) throws Exception {
        Usuario usuario = null;
        String sql = "SELECT\n"
                + "U.IDUSU AS IDUSU,\n"
                + "U.USUUSU AS USUUSU,\n"
                + "INITCAP(P.NOMPER) AS NOMPER,\n"
                + "INITCAP(P.APEPER) AS APEPER,\n"
                + "P.EMAPER AS EMAPER\n"
                + "FROM \n"
                + "USUARIO U\n"
                + "INNER JOIN PERSONAL P ON\n"
                + "U.IDPER = P.IDPER\n"
                + "WHERE \n"
                + "P.EMAPER = '" + email + "'";
        
        Statement st = conectar().createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            usuario = new Usuario();
            usuario.setIDUSU(rs.getInt("IDUSU"));
            usuario.setUSUUSU(rs.getString("USUUSU"));
            usuario.setNombre(rs.getString("NOMPER"));
            usuario.setApellido(rs.getString("APEPER"));
            usuario.setEmail(rs.getString("EMAPER"));
        }
        rs.close();
        st.close();

//AQUI MURIO  
        // El correo gmail de envio
        String remitente = "jhianpol.ramos@gmail.com";
        String clave = "Ramos2021...";

        //Destinatario segun el usuario en el login
//        String destinatario = email;
        String destinatario = usuario.getEmail();

        //Asunto y cuerpo y la contraseña generada desde el modelo
        String asunto = "Restablecer tu contraseña de la cuenta SISREGVG";
        String cuerpo = "BUEN DÍA " + usuario.getNombre() + " " + usuario.getApellido() + "\n"
                + "Has indicado que olvidaste tu contraseña en la cuenta SISREGVG: " + usuario.getUSUUSU() + "\n"
                + "\n Su nueva contraseña es: " + usuario.getPWDUSU() + "\n"
                + "\n Puedes iniciar sesión aqui: http://localhost:8080/Sisregvg_ODAO/faces/login_1.xhtml" + "\n"
                + "\n Puedes comunicarte al email: " + remitente + " para mayor ayuda o información." + "\n"
                + "Estaremos encantados de responder las preguntas que puedas tener." + "\n"
                + "\n Gracias, atentamente el equipo de cuentas de SISREGVG.";
        
        String sqlllll = "update USUARIO set PWDUSU=? where IDUSU=? ";
        usuario.setPWDUSU(EncriptarPWD.encriptar(usuario.getPWDUSU()));
        try {
            PreparedStatement ps = conectar().prepareStatement(sqlllll);
            ps.setString(1, usuario.getPWDUSU());
            ps.setInt(2, usuario.getIDUSU());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en EncriptarPWD" + e.getMessage());
            e.printStackTrace();
        }

//        // La configuracion para enviar correo
//        Properties props = new Properties();
//        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.user", remitente);
//        props.put("mail.smtp.clave", clave);
//        Session session = Session.getDefaultInstance(props);
//        MimeMessage message = new MimeMessage(session);
        try {
            EmailS.configurateEmail(remitente, clave, destinatario, asunto, cuerpo);
//            message.setFrom(new InternetAddress(remitente));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
//            message.setSubject(asunto);
//
//            BodyPart texto = new MimeBodyPart();
//            texto.setText(cuerpo);
//
//            MimeMultipart multiParte = new MimeMultipart();
//            multiParte.addBodyPart(texto);
//            message.setContent(multiParte);
//
//            Transport transport = session.getTransport("smtp");
//            transport.connect("smtp.gmail.com", remitente, clave);
//            transport.sendMessage(message, message.getAllRecipients());
//            transport.close();
        } catch (MessagingException ex) {
            System.out.println("Error en refactorPWDS " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public static void configurateEmail(String remitente, String clave, String destinatario, String asunto, String cuerpo) throws Exception {
        // La configuracion para enviar correo
        Properties props = new Properties();
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", clave);
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            
            BodyPart texto = new MimeBodyPart();
            texto.setText(cuerpo);
            
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            message.setContent(multiParte);
            
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, clave);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ex) {
            System.out.println("Error en ConfigurateEmail " + ex.getMessage());
            ex.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) throws Exception {
        try {
            Personal per = new Personal();
            per.setEmail("jose.rivera@vallegrande.edu.pe");
            per.setNombre("Jhianpol Maximiliano");
            per.setApellido("Ramos Gil");
            per.setDni("74140394");
            EmailS.enviarContraseña(per);
            System.out.println("CORREO ENVIADO");
            JOptionPane.showMessageDialog(null, "CORREO ENVIADO", "CORRECTO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            System.out.println("Error en mandarCorreo/mail " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "NO SE ENVIO EL CORREO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
