/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josuemarroquin.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import org.josuemarroquin.system.Principal;

/**
 *
 * @author Josué Daniel Marroquín Hernández <jmarroquin-2020296@kinal.edu.gt>
 * @date 26 may. 2021
 * @time 21:55:12 Codigo Tecnico: IN5BV Carnet: 2020296
 */
public class AutorController implements Initializable {

    //Declaracion de Variables Privadas
    private Principal escenarioPrincipal;

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

    //Regresar a Menu Principal
    @FXML
    private void btnAtras(MouseEvent event) {
        escenarioPrincipal.mostrarMenuPrincipal();
    }
}
