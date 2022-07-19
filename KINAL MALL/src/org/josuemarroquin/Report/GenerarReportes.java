/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josuemarroquin.Report;

import java.util.Map;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.josuemarroquin.DB.Conexion;
import java.io.InputStream;

/**
 *
 * @author Josué Daniel Marroquín Hernández <jmarroquin-2020296@kinal.edu.gt>
 * @date 20 jul. 2021
 * @time 14:52:19 Codigo Tecnico: IN5BV Carnet: 2020296
 */
public class GenerarReportes {

    private static GenerarReportes instancia;
    //private static GenerarReporte instancia;
    private final String PAQUETE_IMAGE = "/org/josuemarroquin/resource/images/";

    private GenerarReportes() {
    }

    public static GenerarReportes getInstance() {
        if (instancia == null) {
            instancia = new GenerarReportes();
        }
        return instancia;
    }

    public void MostrarReporte(String nombreReporte, String titulo, Map parametros) {

        try {

            parametros.put("LOGO_HEADER", getClass().getResourceAsStream("/org/josuemarroquin/resource/images/Kinal Mall.png"));

            InputStream archivo = GenerarReportes.class.getResourceAsStream(nombreReporte);
            JasperReport report = (JasperReport) JRLoader.loadObject(archivo);
            JasperPrint print = JasperFillManager.fillReport(report, parametros, Conexion.getInstance().getConexion());
            JasperViewer viewer = new JasperViewer(print, false);
            viewer.setTitle(titulo);
            viewer.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
