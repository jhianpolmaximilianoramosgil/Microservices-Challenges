/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import dao.Conexion;
//import java.io.File;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//import javax.faces.context.FacesContext;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperRunManager;


import java.io.*;
import java.sql.SQLException;
import java.util.*;
import javax.faces.context.FacesContext;
import javax.servlet.*;
import javax.servlet.http.*;
import net.sf.jasperreports.engine.*;


/**
 *
 * @author Jhianpol //
 */
public class Reporte extends Conexion {

    public void exportarPDFGlobal(Map parameters, String url, String nomPDF) throws JRException, IOException, Exception {
        this.conectar();
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/" + url + ""));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parameters, this.getCn());
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=" + nomPDF + "");
        ServletOutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    
//   vista previa
    public void ReportePdf(String root) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        try {
            File reportfile = new File(root);
            Map<String, Object> parameter = new HashMap<String, Object>();
            byte[] bytes = JasperRunManager.runReportToPdf(reportfile.getPath(), parameter, this.conectar());
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.setContentType("application/pdf");
            httpServletResponse.setContentLength(bytes.length);
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            outputStream.write(bytes, 0, bytes.length);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            System.out.println("Error en ReportePdf_S " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
//    vista previa por id
    public void reporteIdVal(String root, String id) {
        try {
            File reportfile = new File(root);
            Map<String, Object> parameter = new HashMap<String, Object>();
            parameter.put("val", id);
            byte[] bytes = JasperRunManager.runReportToPdf(reportfile.getPath(), parameter, Conexion.conectar());
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.setContentType("application/pdf");
            httpServletResponse.setContentLength(bytes.length);
            ServletOutputStream outputstream = httpServletResponse.getOutputStream();
            outputstream.write(bytes, 0, bytes.length);
            outputstream.flush();
            outputstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    
    
    
      public void reporteIdDej(String root, String id) {
        try {
            File reportfile = new File(root);
            Map<String, Object> parameter = new HashMap<String, Object>();
            parameter.put("dej", id);
            byte[] bytes = JasperRunManager.runReportToPdf(reportfile.getPath(), parameter, Conexion.conectar());
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.setContentType("application/pdf");
            httpServletResponse.setContentLength(bytes.length);
            ServletOutputStream outputstream = httpServletResponse.getOutputStream();
            outputstream.write(bytes, 0, bytes.length);
            outputstream.flush();
            outputstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      
      
      
      
      
//    public void ReportePdf(String root, String numeroinformesocial) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
