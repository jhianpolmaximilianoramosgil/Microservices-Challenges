package modelo;

import lombok.Data;

@Data

public class Dashboard {

    private int ACTIVO;
    private int INACTIVO;
    private int MONITOR;
    private int TESORERO;
    private int JEFEAREA;

    public int Dashboard() {
        return ACTIVO;
    }
}
