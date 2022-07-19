/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josuemarroquin.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.josuemarroquin.system.Principal;

/**
 *
 * @author Josué Daniel Marroquín Hernández <jmarroquin-2020296@kinal.edu.gt>
 * @date 26 may. 2021
 * @time 21:52:02 Codigo Tecnico: IN5BV Carnet: 2020296
 */
public class MenuPrincipalController implements Initializable {

    //Declaracion de Variables Privadas
    private Principal escenarioPrincipal;
    @FXML
    private Button btnCerrar;

    //Metodo Initializable
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //To change body of generated methods, choose Tools | Templates.
    }

    //Metodos Get y Set de la Clase Principal
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    //Mostrar Vista Autor
    @FXML
    void abrirVistaAutor(ActionEvent event) {
        escenarioPrincipal.mostrarAutor();
    }

    //Mostrar Adminitracion
    @FXML
    void mostrarAdministracion(ActionEvent event) {
        escenarioPrincipal.mostrarAdministracion();
    }

    //Mostrar Clientes
    @FXML
    void mostrarClientes(ActionEvent event) {
        escenarioPrincipal.mostrarCliente();
    }

    //Mostrar Proveedores
    @FXML
    void mostrarProveedores(ActionEvent event) {
        escenarioPrincipal.mostrarProveedores();
    }

    //Mostrar Cobros Adminitracion
    @FXML
    void mostrarCobrosAdmin(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Kinal Mall");
        alert.setHeaderText(null);
        alert.setContentText("Esta Vista esta en Desarrollo no se puede abrir todavia");
        alert.show();
    }

    //Mostrar Empleados
    @FXML
    void mostararEmpleados(ActionEvent event) {
        escenarioPrincipal.mostrarEmpleados();
    }

    @FXML
    void cerrarSeccion(ActionEvent event) {

    }
}
