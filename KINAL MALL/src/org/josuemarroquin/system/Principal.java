/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josuemarroquin.system;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.josuemarroquin.controller.*;

/**
 *
 * @author Josué Daniel Marroquín Hernández <jmarroquin-2020296@kinal.edu.gt>
 * @date 26 may. 2021
 * @time 21:41:42 Codigo Tecnico: IN5BV Carnet: 2020296
 */
public class Principal extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.escenarioPrincipal = stage;
        escenarioPrincipal.getIcons().add(new Image("/org/josuemarroquin/resource/images/Kinal Mall.png"));
        mostrarMenuPrincipal();
    }

    public boolean validar(ArrayList<TextField> listaTextField, ArrayList<ComboBox> listaComboBox) {
        boolean respuesta = true;
        for (ComboBox combobox : listaComboBox) {
            if (combobox.getSelectionModel().getSelectedItem() == null) {
                return false;
            }

        }
        for (TextField textfield : listaTextField) {
            if (textfield.getText().trim().isEmpty()) {
                return false;
            }

        }
        return respuesta;
    }

    public void mostrarMenuPrincipal() {
        try {
            MenuPrincipalController menuController = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 1360, 760);
            menuController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Menu Principal");
            e.printStackTrace();
        }
    }

    public void mostrarAutor() {
        try {
            AutorController autorController = (AutorController) cambiarEscena("AutorView.fxml", 1360, 760);
            autorController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Autor");
            e.printStackTrace();
        }
    }

    public void mostrarAdministracion() {
        try {
            AdminsitarcionContriller admiController = (AdminsitarcionContriller) cambiarEscena("AdministracionView.fxml", 1360, 760);
            admiController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Administracion");
            e.printStackTrace();
        }
    }

    public void mostrarCliente() {

        try {
            ClienteController clienteController = (ClienteController) cambiarEscena("ClienteView.fxml", 1360, 760);
            clienteController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Clientes");
            e.printStackTrace();
        }
    }

    public void mostrarTipoCLientes() {

        try {
            TipoClientesController tpClientesController = (TipoClientesController) cambiarEscena("TipoClienteView.fxml", 1360, 760);
            tpClientesController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista TipoClientes");
            e.printStackTrace();
        }
    }

    public void mostrarLocales() {

        try {
            LocalesController localController = (LocalesController) cambiarEscena("LocalesView.fxml", 1360, 760);
            localController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Locales");
            e.printStackTrace();
        }
    }

    public void mostrarDepartamentos() {

        try {
            DepartamentosController depaController = (DepartamentosController) cambiarEscena("DepartamentosView.fxml", 1360, 760);
            depaController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Departamentos");
            e.printStackTrace();
        }
    }

    public void mostrarProveedores() {

        try {
            ProveedoresController proveController = (ProveedoresController) cambiarEscena("ProveedoresView.fxml", 1360, 760);
            proveController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Proveedores");
            e.printStackTrace();
        }
    }

    public void mostrarCuentasporCobrar() {

        try {
            CuentasporCobrarController cuentaspcobrarController = (CuentasporCobrarController) cambiarEscena("CuentasporCobrarView.fxml", 1360, 760);
            cuentaspcobrarController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Cuentas por Cobrar");
            e.printStackTrace();
        }
    }

    public void mostrarCuentasporPagar() {

        try {
            CuentasporPagarController cuentasporpagarController = (CuentasporPagarController) cambiarEscena("CuentasporPagarView.fxml", 1360, 760);
            cuentasporpagarController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Cuentas por Pagar");
            e.printStackTrace();
        }
    }

    public void mostrarHorarios() {

        try {
            HorariosController horariosController = (HorariosController) cambiarEscena("HorariosView.fxml", 1360, 760);
            horariosController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Horarios");
            e.printStackTrace();
        }
    }

    public void mostrarCargos() {

        try {
            CargosController cargosController = (CargosController) cambiarEscena("CargosView.fxml", 1360, 760);
            cargosController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Cargos");
            e.printStackTrace();
        }
    }

    public void mostrarEmpleados() {

        try {
            EmpeladosController empleadosController = (EmpeladosController) cambiarEscena("EmpleadosView.fxml", 1360, 760);
            empleadosController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Empleados");
            e.printStackTrace();
        }
    }
    
    public void mostrarRegistrarse() {

        try {
            RegistrarseController registrarseController = (RegistrarseController) cambiarEscena("EmpleadosView.fxml", 1360, 760);
            registrarseController.setEscenarioPrincipal(this);
        } catch (IOException e) {
            System.out.println("Se Produjo un error en la vista Registrarse");
            e.printStackTrace();
        }
    }

    public Initializable cambiarEscena(String viewFxml, int ancho, int alto) throws IOException {
        Initializable resultado = null;
        FXMLLoader cargarFXML = new FXMLLoader();
        cargarFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargarFXML.setLocation(Principal.class.getResource("/org/josuemarroquin/view/" + viewFxml));
        InputStream archivo = Principal.class.getResourceAsStream("/org/josuemarroquin/view/" + viewFxml);
        escena = new Scene((AnchorPane) cargarFXML.load(archivo), ancho, alto);
        this.escenarioPrincipal.setScene(escena);
        this.escenarioPrincipal.sizeToScene();
        this.escenarioPrincipal.show();
        this.escenarioPrincipal.setResizable(false);
        this.escenarioPrincipal.centerOnScreen();

        resultado = (Initializable) cargarFXML.getController();

        return resultado;
    }

}
