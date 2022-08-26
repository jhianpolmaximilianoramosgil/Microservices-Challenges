package servicios;

// Copyright (c) 2018, Altiria TIC SL
// All rights reserved.
// El uso de este código de ejemplo es solamente para mostrar el uso de la pasarela de envío de SMS de Altiria
// Para un uso personalizado del código, es necesario consultar la API de especificaciones técnicas, donde también podrás encontrar
// más ejemplos de programación en otros lenguajes de programación y otros protocolos (http, REST, web services)
// https://www.altiria.com/api-envio-sms/
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Personal;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SmsS {

    public static void enviarSms(Personal personal) {
        //Se fija el tiempo maximo de espera para conectar con el servidor (5000)
        //Se fija el tiempo maximo de espera de la respuesta del servidor (60000)
        RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(60000).build();

        //Se inicia el objeto HTTP
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setDefaultRequestConfig(config);
        CloseableHttpClient httpClient = builder.build();
        String celular = personal.getCelular();
//        String celular = "940460321";
//        String celu = "974862031";

        //Se fija la URL sobre la que enviar la petici�n POST
        HttpPost post = new HttpPost("http://www.altiria.net/api/http");

        String cuerpo = "BUEN DÍA " + personal.getNombre() + " " + personal.getApellido() + "\n"
                + "\n Su usuario es: " + personal.getDni() + "\n y su contraseña es: " + personal.getPwdper()
                + "\n Se agradece por usar la plataforma SISREGVG";

        //Se crea la lista de par�metros a enviar en la petici�n POST
        List<NameValuePair> parametersList = new ArrayList<NameValuePair>();
        //XX, YY y ZZ se corresponden con los valores de identificaci�n del
        //usuario en el sistema.
        parametersList.add(new BasicNameValuePair("cmd", "sendsms"));
        //domainId solo es necesario si el login no es un email
        //parametersList.add(new BasicNameValuePair("domainId", "XX"));
        parametersList.add(new BasicNameValuePair("login", "jhianpol.ramos@vallegrande.edu.pe"));
        parametersList.add(new BasicNameValuePair("passwd", "hyetm46y"));
        parametersList.add(new BasicNameValuePair("dest", "51" + celular));
        //        parametersList.add(new BasicNameValuePair("dest", "51" + "51989038489"));
//        parametersList.add(new BasicNameValuePair("dest", "51" + celu));
        parametersList.add(new BasicNameValuePair("msg", cuerpo));
        //No es posible utilizar el remitente en Am�rica pero s� en Espa�a y Europa
        //Descomentar la l�nea solo si se cuenta con un remitente autorizado por Altiria
        //parametersList.add(new BasicNameValuePair("senderId", "remitente"));

        try {
            //Se fija la codificacion de caracteres de la peticion POST
            post.setEntity(new UrlEncodedFormEntity(parametersList, "UTF-8"));
        } catch (UnsupportedEncodingException uex) {
            System.out.println("ERROR: codificaci�n de caracteres no soportada");
        }
        CloseableHttpResponse response = null;
        try {
            System.out.println("Su peticion se está enviando");
            //Se env�a la petici�n
            response = httpClient.execute(post);
            //Se consigue la respuesta
            String resp = EntityUtils.toString(response.getEntity());
            //Error en la respuesta del servidor
            if (response.getStatusLine().getStatusCode() != 200) {
                System.out.println("ERROR: C�digo de error HTTP:  " + response.getStatusLine().getStatusCode());
                System.out.println("Compruebe que ha configurado correctamente la direccion/url ");
                System.out.println("suministrada por Altiria");
                return;
            } else {
                //Se procesa la respuesta capturada en la cadena 'response'
                if (resp.startsWith("ERROR")) {
                    System.out.println(resp);
                    System.out.println("C�digo de error de Altiria. Compruebe las especificaciones");
                } else {
                    System.out.println(resp);
                }
            }
        } catch (Exception e) {
            System.out.println("Excepci�n");
            e.printStackTrace();
            return;
        } finally {
            //En cualquier caso se cierra la conexi�n
            post.releaseConnection();
            if (response != null) {
                try {
                    response.close();
                } catch (IOException ioe) {
                    System.out.println("ERROR cerrando recursos");
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            Personal per = new Personal();
//            per.seCelular"940460321");
            SmsS.enviarSms(per);
            System.out.println("MENSAJE SMS ENVIADO");
            JOptionPane.showMessageDialog(null, "MENSAJE SMS ENVIADO", "CORRECTO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            System.out.println("ERROR AL ENVIAR MENSAJE SMS");
            System.out.println("Error en enviarMensajeSms_S " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "NO SE ENVIO EL CORREO", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
