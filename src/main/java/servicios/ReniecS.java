package servicios;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.xml.ws.Response;
import modelo.Personal;


public class ReniecS {

    public static void buscarDni(Personal per) throws Exception {
        String dni = per.getDni();
        String token = "?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imp1bGlvLnF1aXNwZUB2YWxsZWdyYW5kZS5lZHUucGUifQ.6M-P2QMMvKFZEeMvTUXvkOooM02N_pWqt0OdlaYW3PM";
        String enlace = "https://dniruc.apisperu.com/api/v1/dni/" + dni + token;
        try {
            URL url = new URL(enlace);
            URLConnection request = url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            if (root.isJsonObject()) {
                JsonObject rootobj = root.getAsJsonObject();
                String nombres = rootobj.get("nombres").getAsString();
                String apellido_paterno = rootobj.get("apellidoPaterno").getAsString();
                String apellido_materno = rootobj.get("apellidoMaterno").getAsString();
                per.setNombre(nombres);
                per.setApellido(apellido_paterno + " " + apellido_materno);
                per.setEmail("");
                per.setCelular("");
                per.setDomper("");
                per.setSexo("");
                per.setCargo("Seleccionar");
                System.out.println("RESULTADO:\n");
                System.out.println(nombres + "\n" + apellido_paterno + "\n" + apellido_materno + "\n");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Busqueda exitosa", "DNI encontrado"));
            } else {
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "DNI no  encontrado"));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Busqueda", "DNI no encontrado"));
        }
    }

    public static void main(String[] args) throws Exception {
        Personal per = new Personal();
        per.setDni("74140394");

        buscarDni(per);

    }

}
