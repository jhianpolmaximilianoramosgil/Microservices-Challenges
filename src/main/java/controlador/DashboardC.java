package controlador;

import dao.DashboardImpl;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
//import org.primefaces.model.chart.PieChartModel;
//import org.primefaces.model.charts.ChartData;
//import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartDataSet;
import lombok.Data;


@Data

//Notación CDI
@Named(value = "dashboardC")
//@Dependent
@SessionScoped
public class DashboardC implements Serializable {

    private PieChartModel dashboardPersonal;
    private List<Number> listaPersonal;
    private DashboardImpl dao;
    private PieChartModel dashboardCargo;
    private List<Number> listaCargo;

    public DashboardC() {
        dashboardPersonal = new PieChartModel();
        dashboardCargo = new PieChartModel();
        dao = new DashboardImpl();
    }

    private void dashboardPersonal() throws Exception {
        ChartData data = new ChartData();
        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = listaPersonal;
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(113, 175, 220)");
        bgColors.add("rgb(227, 99, 91)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("PERSONAL ACTIVOS");
        labels.add("PERSONAL INACTIVOS");
        data.setLabels(labels);
        dashboardPersonal.setData(data);
    }

    private void dashboardCargo() throws Exception {
        ChartData data = new ChartData();
        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = listaCargo;
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(113, 175, 220)");
        bgColors.add("rgb(198, 145, 141)");
        bgColors.add("rgb(227, 99, 91)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("MONITORES");
        labels.add("TESORERO");
        labels.add("JEFEAREA");
        data.setLabels(labels);
        dashboardCargo.setData(data);
    }

    @PostConstruct
    public void construir() {
        try {
            listaPersonal = dao.dashboardPersonal();
            dashboardPersonal();

            listaCargo = dao.dashboardCargo();
            dashboardCargo();
        } catch (Exception e) {
            System.out.println("Error en el dashboardCargoD " + e.getMessage());
        }

    }
       private int number = 30;

    public void activarSesion() {
        number = 30;
    }

      public void decrementoNumero() throws IOException {
        if (number > 0) {
            number--;
        } else if (number == 0) {
            System.out.println("salir de sesion");
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Sisregvg_ODAO/");
        }
    }
}
