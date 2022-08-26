/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.UUID;
import lombok.Data;

@Data
public class Usuario {

    String pwd1 = UUID.randomUUID().toString().toUpperCase().substring(2, 6);
    String pwd2 = UUID.randomUUID().toString().toLowerCase().substring(2, 6);
    String pwd3 = "/-";
    String password = pwd1 + pwd2 + pwd3;

    private int IDUSU;
    private String USUUSU;
    private String PWDUSU = password;
    private int LEVUSU;
    private String ESTUSU;
    private Integer idper;
    private String nombre;
    private String apellido;
    private String email;
    private Personal personal = new Personal();

    public Usuario() {
    }

}
