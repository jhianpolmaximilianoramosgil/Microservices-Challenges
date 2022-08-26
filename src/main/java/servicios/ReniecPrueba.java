package servicios;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;

import modelo.Personal;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ReniecPrueba {

    public static void buscarDniReniec(Personal per) throws Exception {
        String dni = per.getDni();
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        Request request = new Request.Builder()
                .url("https://apiperu.dev/api/dni/"+dni)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer 510a2ca922ec3dae2b3a7ef49c6663165181bc5e4a419e0e44e2b528efddda97")
                .build();
        try(Response response = client.newCall(request).execute()) {
            String json =response.body().string();
            System.out.println(json);
            JsonElement root = JsonParser.parseString​(json).getAsJsonObject();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            if (root.isJsonObject()) {
                System.out.println("es un j");
                JsonObject rootobj = root.getAsJsonObject().getAsJsonObject("data");
                String nombres= rootobj.get("nombres").getAsString();
                String apellido_paterno= rootobj.get("apellido_paterno").getAsString();
                String apellido_materno= rootobj.get("apellido_materno").getAsString();
                System.out.println("Resultado\n");
                System.out.println(apellido_paterno + "\n" + apellido_materno + "\n" + nombres + "\n");

                per.setNombre(nombres);
                per.setApellido(apellido_paterno + " " + apellido_materno);
            }
           
        }
    }
    public static void main(String[] args) throws Exception {
        Personal per = new Personal();       
        per.setDni("72717476");
        buscarDniReniec(per);
        
    }
    
}
