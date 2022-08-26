package servicios;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncriptarPWD {
//    jalar excepciones
    public static String encriptar(String input) throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            //Now we need to zero pad it if you actually want the full 32 chars
            // Ahora necesitamos ponerle relleno a cero si realmente quieres los 32 caracteres completos
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static void main(String[] args) throws Exception {
        String input = "12345@ABC";
        System.out.println(EncriptarPWD.encriptar(input));
    }
    
}


