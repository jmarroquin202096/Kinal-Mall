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
import org.josuemarroquin.DB.Conexion;
import org.josuemarroquin.system.Principal;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.josuemarroquin.bean.Administracion;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.josuemarroquin.Report.*;

/**
 *
 * @author Josué Daniel Marroquín Hernández <jmarroquin-2020296@kinal.edu.gt>
 * @date 26 may. 2021
 * @time 19:31:15 Codigo Tecnico: IN5BV Carnet: 2020296
 */
public class AdminsitarcionContriller implements Initializable {

    //Declaracion de Variables Privadas
    private Principal escenarioPrincipal;

    private enum Operaciones {
        NUEVO, GUARDAR, EDITAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private Operaciones operacion = Operaciones.NINGUNO;

    private ObservableList<Administracion> listaAdministracion;

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
    private Button btnDepartamentos;

    @FXML
    private ImageView imgIrDepartamentos;

    @FXML
    private Button btnCargos;

    @FXML
    private ImageView imgIrCargos;

    @FXML
    private Button btnTipoClientes;

    @FXML
    private ImageView imgIrTipoClientes;

    @FXML
    private Button btnLocales;

    @FXML
    private ImageView imgIrLocales;

    @FXML
    private TableView tblAdministracion;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colDireccion;

    @FXML
    private TableColumn colTelefono;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtTelefono;

    //ObservablesList para Listar Adminitracion
    public ObservableList<Administracion> getAdministracion() {

        ArrayList<Administracion> listado = new ArrayList<>();
        try {
            //CallableStatement stmt;
            PreparedStatement stmt;
            stmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarAdministracion()}");
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                listado.add(new Administracion(
                        resultado.getInt("id"),
                        resultado.getString("direccion"),
                        resultado.getString("telefono")
                )
                );

            }

            resultado.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        listaAdministracion = FXCollections.observableArrayList(listado);

        return listaAdministracion;

    }

    //Agregar Datos en Adminitracion
    public void agregarAdministracion() {

        Administracion registro = new Administracion();
        registro.setDireccion(txtDireccion.getText());
        registro.setTelefono(txtTelefono.getText());

        try {
            PreparedStatement stmt;
            stmt = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarAdministracion(?,?)}");
            stmt.setString(1, registro.getDireccion());
            stmt.setString(2, registro.getTelefono());
            stmt.execute();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    //Editar Datos en Adminitracion
    private void editarAdministracion() {

        Administracion registro = new Administracion();

        registro.setId(Integer.parseInt(txtId.getText()));
        registro.setDireccion(txtDireccion.getText());
        registro.setTelefono(txtTelefono.getText());

        try {
            PreparedStatement stmt;
            stmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarAdministracion(?, ?, ?)}");
            stmt.setInt(1, registro.getId());
            stmt.setString(2, registro.getDireccion());
            stmt.setString(3, registro.getTelefono());
            stmt.execute();
            System.out.println(stmt.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Eliminar Datos en Administracion
    private void eliminarAdministracion() {

        try {
            PreparedStatement stmt;
            stmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarAdministracion(?)}");
            stmt.setInt(1, Integer.parseInt(txtId.getText()));
            stmt.execute();
            System.out.println(stmt.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Metodo Cargar Datos
    public void cargarDatos() {
        tblAdministracion.setItems(getAdministracion());
        colId.setCellValueFactory(new PropertyValueFactory<Administracion, Integer>("id"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Administracion, String>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Administracion, String>("telefono"));

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

    //Metodo para Validar el Telefono
    public boolean validarTelefono(String numero) {
        Pattern pattern = Pattern.compile("^[0-9]{8}$");
        Matcher matcher = pattern.matcher(numero);
        return matcher.matches();
    }

    //Habilitar Campos de los Text Field
    public void habilitarCampos() {
        txtId.setEditable(true);
        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);

    }

    //Deshablitar Campos de los Text Field
    public void desabilitarControles() {
        txtId.setEditable(true);
        txtDireccion.setEditable(false);
        txtTelefono.setEditable(false);
        btnNuevo.setDisable(false);
    }

    //Limpiar Campos de los Text Field
    public void limpiarCampos() {
        txtId.clear();
        txtDireccion.clear();
        txtTelefono.clear();
    }

    //Botones para Guardar, Eliminar, Editar y Reportar
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
                    imgEditar.setImage(new Image("/org/josuemarroquin/resource/images/btn Actualizar.png"));
                    btnReportar.setText("Cancelar");
                    imgReportar.setImage(new Image("/org/josuemarroquin/resource/images/btn Cancelar.png"));
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    operacion = Operaciones.ACTUALIZAR;
                }
                break;
            case ACTUALIZAR:

                ArrayList<TextField> listaTextField = new ArrayList<>();
                listaTextField.add(txtDireccion);
                listaTextField.add(txtTelefono);

                Alert aler = new Alert(Alert.AlertType.CONFIRMATION);
                aler.setTitle("Kinal Mall");
                aler.setHeaderText(null);
                aler.setContentText("¿Desea actualizar este registro?");

                Optional<ButtonType> respuesta = aler.showAndWait();

                if (respuesta.get() == ButtonType.OK) {
                    if (validarTelefono(txtTelefono.getText())) {

                        if (validar(listaTextField)) {

                            editarAdministracion();
                            limpiarCampos();
                            desabilitarControles();
                            cargarDatos();
                            btnNuevo.setDisable(false);
                            btnEliminar.setDisable(false);
                            btnEditar.setText("Editar");
                            imgEditar.setImage(new Image("/org/josuemarroquin/resource/images/btn Editar.png"));
                            btnReportar.setText("Reportar");
                            operacion = Operaciones.NINGUNO;
                            break;
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Kinal Mall");
                            alert.setHeaderText(null);
                            alert.setContentText("Por favor llene todos los Campos de Texto");
                            alert.show();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Kinal Mall");
                        alert.setHeaderText(null);
                        alert.setContentText("El Numero es Invalido");
                        alert.show();
                    }
                }
        }
        System.out.println("Operación: " + operacion);
    }

    @FXML
    void eliminar(ActionEvent event
    ) {
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case GUARDAR:
                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image("/org/josuemarroquin/resource/images/btn Nuevo.png"));
                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image("/org/josuemarroquin/resource/images/btn Eliminar.png"));
                btnEditar.setDisable(false);
                btnReportar.setDisable(false);
                limpiarCampos();
                desabilitarControles();
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
                        eliminarAdministracion();
                        limpiarCampos();
                        cargarDatos();
                    } else {

                    }
                }
                break;

        }
        System.out.println("Operación: " + operacion);
    }

    @FXML
    void nuevo(ActionEvent event
    ) {
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
                if (txtDireccion.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("Debe Ingresar Datos para Guardar");
                    alert.show();
                } else {

                    ArrayList<TextField> listaTextField = new ArrayList<>();
                    listaTextField.add(txtDireccion);
                    listaTextField.add(txtTelefono);

                    if (validarTelefono(txtTelefono.getText())) {

                        if (validar(listaTextField)) {
                            btnNuevo.setDisable(false);
                            agregarAdministracion();
                            cargarDatos();
                            limpiarCampos();
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
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Kinal Mall");
                        alert.setHeaderText(null);
                        alert.setContentText("El Numero es Inválido");
                        alert.show();
                    }

                }
                break;
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
                limpiarCampos();
                desabilitarControles();
                operacion = Operaciones.NINGUNO;
                break;
            case NINGUNO: //Reportar
                Map parametros = new HashMap();
                btnReportar.setText("Reportar");
                GenerarReportes.getInstance().MostrarReporte("reporteAdministracion.jasper", "Reporte Admnistración", parametros);
                btnNuevo.setDisable(false);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                limpiarCampos();
                desabilitarControles();
                cargarDatos();
                operacion = Operaciones.NINGUNO;
                break;

        }
        System.out.println("Operación: " + operacion);
    }

    //Mostrar Vista Cargos
    @FXML
    void mostrarCargos(ActionEvent event
    ) {
        escenarioPrincipal.mostrarCargos();
    }

    //Mostrar Vista Departamentos
    @FXML
    void mostrarDepartamentos(ActionEvent event
    ) {
        escenarioPrincipal.mostrarDepartamentos();
    }

    //Mostrar Vista Locales
    @FXML
    void mostrarLocales(ActionEvent event
    ) {
        escenarioPrincipal.mostrarLocales();
    }

    //Mostrar Vista Tipo Clientes
    @FXML
    void mostrarTipoClientes(ActionEvent event
    ) {
        escenarioPrincipal.mostrarTipoCLientes();
    }

    //Seleccionar Elemento en Tabla
    @FXML
    void seleccionarElemento(MouseEvent event
    ) {
        selecionarElemento();
    }
    //Metodo para Seleccionar Elemento

    public void selecionarElemento() {
        try {
            txtId.setText(String.valueOf(((Administracion) tblAdministracion.getSelectionModel().getSelectedItem()).getId()));
            txtDireccion.setText(((Administracion) tblAdministracion.getSelectionModel().getSelectedItem()).getDireccion());
            txtTelefono.setText(((Administracion) tblAdministracion.getSelectionModel().getSelectedItem()).getTelefono());
        } catch (NullPointerException e) {

        }
    }

    //Regresar a Menu Principal
    @FXML
    void btnRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarMenuPrincipal();
    }

    //Metodos Get y Set de la Clase Principal
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}
