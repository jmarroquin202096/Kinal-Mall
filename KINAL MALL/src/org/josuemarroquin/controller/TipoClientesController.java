/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josuemarroquin.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.josuemarroquin.DB.Conexion;
import org.josuemarroquin.Report.GenerarReportes;
import org.josuemarroquin.bean.*;
import org.josuemarroquin.system.Principal;

/**
 *
 * @author Josué Daniel Marroquín Hernández <jmarroquin-2020296@kinal.edu.gt>
 * @date 13 jun. 2021
 * @time 12:46:20 Codigo Tecnico: IN5BV Carnet: 2020296
 */
public class TipoClientesController implements Initializable {

    //Declaracion de Variables Privadas
    private Principal escenarioPrincipal;

    private enum Operaciones {
        NUEVO, GUARDAR, EDITAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private Operaciones operacion = Operaciones.NINGUNO;

    private ObservableList<TipoCliente> listaTipoCliente;

    //Metodo Initializable
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    //Declaracion de las Variables FXML
    @FXML
    private Button btnNuevo;

    @FXML
    private ImageView imgNuevo;

    @FXML
    private Button btnEliminar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private Button btnEditar;

    @FXML
    private ImageView imgEditar;

    @FXML
    private Button btnReportar;

    @FXML
    private ImageView imgReportar;

    @FXML
    private TableView tblTipoClientes;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtDescripcion;
    
    //ObservablesList para Listar Tipo Clientes
    public ObservableList<TipoCliente> getTipoCLiente() {

        ArrayList<TipoCliente> lista = new ArrayList<>();
        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoCliente()}");
            ResultSet r = pstmt.executeQuery();

            while (r.next()) {
                lista.add(new TipoCliente(
                        r.getInt("id"),
                        r.getString("descripcion"))
                );

            }
            r.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaTipoCliente = FXCollections.observableArrayList(lista);

        return listaTipoCliente;
    }

    //Agregar Datos en Tipo Clientes
    public void agregarTipoCliente() {
        TipoCliente registro = new TipoCliente();
        registro.setDescripcion(txtDescripcion.getText());

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoCliente(?)}");
            pstmt.setString(1, registro.getDescripcion());
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Editar Datos en Tipo Clientes
    public void editarTipoCliente() {

        TipoCliente registro = new TipoCliente();
        registro.setId(Integer.parseInt(txtId.getText()));
        registro.setDescripcion(txtDescripcion.getText());

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarTipoCliente(?,?)}");
            pstmt.setInt(1, registro.getId());
            pstmt.setString(2, registro.getDescripcion());
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Eliminar Datos en Tipo Clientes
    public void eliminarTipoCliente() {

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoCliente(?)}");
            pstmt.setInt(1, Integer.parseInt(txtId.getText()));
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Metodo Cargar Datos
    public void cargarDatos() {
        tblTipoClientes.setItems(getTipoCLiente());
        colId.setCellValueFactory(new PropertyValueFactory<TipoCliente, Integer>("id"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoCliente, String>("descripcion"));
    }

    //Metodo para Validar los Text Field
    public boolean validar(ArrayList<TextField> listaTextField) {
        boolean respuesta = true;

        for (TextField textfield : listaTextField) {
            if (textfield.getText().trim().isEmpty()) {
                return false;
            }

        }
        return respuesta;
    }

    //Habilitar Campos de los Text Field
    public void habilitarCampos() {
        txtId.setEditable(true);
        txtDescripcion.setEditable(true);
    }

    //Deshabilitar Campos de los Text Field
    public void deshabilitarCampos() {
        txtId.setEditable(false);
        txtDescripcion.setEditable(false);
    }

    //Limpiar Campos de los Text Field
    public void LimpiarCampos() {
        txtId.clear();
        txtDescripcion.clear();
    }

    //Botones para Guardar, Eliminar, Editar y Reportar
    @FXML
    void eliminar(ActionEvent event) {
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case GUARDAR:
                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image("/org/josuemarroquin/resource/images/btn Nuevo.png"));
                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image("/org/josuemarroquin/resource/images/btn Eliminar.png"));
                btnEditar.setDisable(false);
                btnReportar.setDisable(false);
                LimpiarCampos();
                deshabilitarCampos();
                operacion = Operaciones.NINGUNO;
                break;
            case NINGUNO: //Eliminar
                if (txtId.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("Debe Seleccionar un registro para poder Eliminarlo.");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("KINAL MALL");
                    alert.setHeaderText(null);
                    alert.setContentText("¿Está seguro que desea eliminar este registro?");

                    Optional<ButtonType> a = alert.showAndWait();

                    if (a.get() == ButtonType.OK) {
                        eliminarTipoCliente();
                        LimpiarCampos();
                        cargarDatos();
                    } else {

                    }
                }
                break;

        }
        System.out.println("Operación: " + operacion);
    }

    @FXML
    void nuevo(ActionEvent event) {
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image("/org/josuemarroquin/resource/images/btn Guardar.png"));
                btnEliminar.setText("Cancelar");
                imgEliminar.setImage(new Image("/org/josuemarroquin/resource/images/btn Cancelar.png"));
                btnEditar.setDisable(true);
                btnReportar.setDisable(true);
                operacion = Operaciones.GUARDAR;
                break;
            case GUARDAR:
                if (txtDescripcion.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("Debe Ingresar Datos para Guardar");
                    alert.show();
                } else {

                    ArrayList<TextField> listaTextField = new ArrayList<>();
                    listaTextField.add(txtDescripcion);

                    if (validar(listaTextField)) {
                        btnNuevo.setDisable(false);
                        agregarTipoCliente();
                        cargarDatos();
                        LimpiarCampos();
                        btnNuevo.setText("Nuevo");
                        imgNuevo.setImage(new Image("/org/josuemarroquin/resource/images/btn Nuevo.png"));
                        btnEliminar.setText("Eliminar");
                        imgEliminar.setImage(new Image("/org/josuemarroquin/resource/images/btn Eliminar.png"));
                        btnEditar.setDisable(false);
                        btnReportar.setDisable(false);
                        operacion = Operaciones.NINGUNO;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Kinal Mall");
                        alert.setHeaderText(null);
                        alert.setContentText("Por favor llene todos los Campos de Texto");
                        alert.show();
                    }

                }

                break;
        }
        System.out.println("Operación: " + operacion);
    }

    @FXML
    void editar(ActionEvent event) {
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case NINGUNO:
                if ("".equals(txtId.getText())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("No se pude realizar esta accion, " + " " + "Debe Seleccionar un Elemento");
                    alert.show();

                } else {
                    habilitarCampos();
                    btnEditar.setText("Actualizar");
                    imgNuevo.setImage(new Image("/org/josuemarroquin/resource/images/btn Actualizar.png"));
                    btnReportar.setText("Cancelar");
                    imgReportar.setImage(new Image("/org/josuemarroquin/resource/images/btn Cancelar.png"));
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    operacion = Operaciones.ACTUALIZAR;
                }
                break;

            case ACTUALIZAR:

                ArrayList<TextField> listaTextField = new ArrayList<>();
                listaTextField.add(txtDescripcion);

                if (validar(listaTextField)) {
                    editarTipoCliente();
                    LimpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    btnEditar.setText("Editar");
                    imgEditar.setImage(new Image("/org/josuemarroquin/resource/images/btn Editar.png"));
                    btnReportar.setText("Reportar");
                    imgReportar.setImage(new Image("/org/josuemarroquin/resource/images/btn Reportar.png"));
                    operacion = Operaciones.NINGUNO;
                    break;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("Por favor llene todos los Campos de Texto");
                    alert.show();
                }

        }
        System.out.println("Operación: " + operacion);
    }

    @FXML
    void reportar(ActionEvent event
    ) {
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case ACTUALIZAR:
                btnEditar.setText("Editar");
                imgEditar.setImage(new Image("/org/josuemarroquin/resource/images/btn Editar.png"));
                btnReportar.setText("Reportar");
                imgReportar.setImage(new Image("/org/josuemarroquin/resource/images/btn Reportar.png"));
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                LimpiarCampos();
                deshabilitarCampos();
                operacion = Operaciones.NINGUNO;
                break;
            case NINGUNO: //Reportar
                Map parametros = new HashMap();
                btnReportar.setText("Reportar");
                GenerarReportes.getInstance().MostrarReporte("reporteTipoClientes.jasper", "Reporte Tipo Clientes", parametros);
                btnNuevo.setDisable(false);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                LimpiarCampos();
                deshabilitarCampos();
                cargarDatos();
                operacion = Operaciones.NINGUNO;
                break;

        }
        System.out.println("Operación: " + operacion);
    }

    //Seleccionar Elemento en Tabla
    @FXML
    void selecionarElemento(MouseEvent event) {
        selccionarElemento();
    }

    //Metodo para Seleccionar Elemento
    public void selccionarElemento() {
        txtId.setText(String.valueOf(((TipoCliente) tblTipoClientes.getSelectionModel().getSelectedItem()).getId()));
        txtDescripcion.setText(((TipoCliente) tblTipoClientes.getSelectionModel().getSelectedItem()).getDescripcion());
    }

    //Regresar a Administracion
    @FXML
    void btnRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarAdministracion();
    }

    //Metodos Get y Set de la Clase Principal
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}
