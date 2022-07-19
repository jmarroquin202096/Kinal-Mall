/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josuemarroquin.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import org.josuemarroquin.DB.Conexion;
import org.josuemarroquin.Report.GenerarReportes;
import org.josuemarroquin.bean.*;
import org.josuemarroquin.system.Principal;

/**
 *
 * @author Josué Daniel Marroquín Hernández <jmarroquin-2020296@kinal.edu.gt>
 * @date 3 jul. 2021
 * @time 17:02:53 Codigo Tecnico: IN5BV Carnet: 2020296
 */
public class ProveedoresController implements Initializable {

    //Declaracion de Variables Privadas
    private Principal escenarioPrincipal;

    private enum Operaciones {
        NUEVO, GUARDAR, EDITAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private Operaciones operacion = Operaciones.NINGUNO;

    private ObservableList<Proveedores> listaProveedores;

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
    private Button btnMostrarCuentaporCobrar;

    @FXML
    private ImageView imgReportar;

    @FXML
    private TableView tblProveedores;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colNIT;

    @FXML
    private TableColumn colServicioPrestado;

    @FXML
    private TableColumn colTelefono;

    @FXML
    private TableColumn colDireccion;

    @FXML
    private TableColumn colSaldoFavor;

    @FXML
    private TableColumn colSaldoContra;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNIT;

    @FXML
    private TextField txtServicioPrestado;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtSaldoFavor;

    @FXML
    private TextField txtSaldoContra;

    @FXML
    private TextField txtSaldoLiquido;

    //ObservablesList para Listar Proveedores
    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet r = pstmt.executeQuery();

            while (r.next()) {
                lista.add(new Proveedores(
                        r.getInt("id"),
                        r.getString("nit"),
                        r.getString("servicioPrestado"),
                        r.getString("telefono"),
                        r.getString("direccion"),
                        r.getBigDecimal("saldoFavor"),
                        r.getBigDecimal("saldoContra"))
                );
            }
            r.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaProveedores = FXCollections.observableArrayList(lista);
        return listaProveedores;
    }

    //Agregar Datos en Proveedores
    public void agregarProveedores() {
        Proveedores registro = new Proveedores();
        registro.setNit(txtNIT.getText());
        registro.setDireccion(txtDireccion.getText());
        registro.setTelefono(txtTelefono.getText());
        registro.setServicioPrestado(txtServicioPrestado.getText());
        registro.setSaldoContra(new BigDecimal(txtSaldoContra.getText()));
        registro.setSaldoFavor(new BigDecimal(txtSaldoFavor.getText()));

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_AgregarProveedores(?, ?, ?, ?, ?, ?)}");
            pstmt.setString(1, registro.getNit());
            pstmt.setString(2, registro.getServicioPrestado());
            pstmt.setString(3, registro.getTelefono());
            pstmt.setString(4, registro.getDireccion());
            pstmt.setBigDecimal(5, registro.getSaldoFavor());
            pstmt.setBigDecimal(6, registro.getSaldoContra());

            System.out.println(pstmt);

            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Editar Datos en Proveedores
    public void EditarLocales() {
        Proveedores registro = (Proveedores) tblProveedores.getSelectionModel().getSelectedItem();
        registro.setId(Integer.valueOf(txtId.getText()));
        registro.setNit(txtNIT.getText());
        registro.setDireccion(txtDireccion.getText());
        registro.setTelefono(txtTelefono.getText());
        registro.setServicioPrestado(txtServicioPrestado.getText());
        registro.setSaldoContra(new BigDecimal(txtSaldoContra.getText()));
        registro.setSaldoFavor(new BigDecimal(txtSaldoFavor.getText()));

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProveedores( ?, ?, ?, ?, ?, ?, ?)}");
            pstmt.setInt(1, registro.getId());
            pstmt.setString(2, registro.getNit());
            pstmt.setString(3, registro.getServicioPrestado());
            pstmt.setString(4, registro.getTelefono());
            pstmt.setString(5, registro.getDireccion());
            pstmt.setBigDecimal(6, registro.getSaldoFavor());
            pstmt.setBigDecimal(7, registro.getSaldoContra());

            pstmt.executeUpdate();
            System.out.println(pstmt.toString());

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    //Eliminar Datos en Proveedores
    public void eliminarProveedores() {
        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedores(?)}");
            pstmt.setInt(1, Integer.parseInt(txtId.getText()));
            pstmt.execute();
            System.out.println(pstmt.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Metodo Cargar Datos
    public void cargarDatos() {
        tblProveedores.setItems(getProveedores());
        colId.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("id"));
        colNIT.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nit"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("telefono"));
        colServicioPrestado.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("servicioPrestado"));
        colSaldoFavor.setCellValueFactory(new PropertyValueFactory<Proveedores, BigDecimal>("saldoFavor"));
        colSaldoContra.setCellValueFactory(new PropertyValueFactory<Proveedores, BigDecimal>("saldoContra"));
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

    //Metodo para Validar el BigDecimal
    public boolean validarBigDecimal(String numero) {
        Pattern pattern = Pattern.compile("^\\d{1,8}([.]\\d{1,2})?$");
        Matcher matcher = pattern.matcher(numero);
        return matcher.matches();
    }

    //Limpiar Campos de los Text Field
    public void limpiarCampos() {
        txtId.clear();
        txtSaldoFavor.clear();
        txtSaldoContra.clear();
        txtNIT.clear();
        txtDireccion.clear();
        txtTelefono.clear();
        txtServicioPrestado.clear();
        txtSaldoLiquido.clear();
    }

    //Habilitar Campos de los Text Field
    public void habilitarCampos() {
        txtId.setEditable(true);
        txtSaldoFavor.setEditable(true);
        txtSaldoContra.setEditable(true);
        txtNIT.setEditable(true);
        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);
        txtServicioPrestado.setEditable(true);
    }

    //Deshabilitar Campos de los Text Field
    public void deshabilitarCampos() {
        txtId.setEditable(false);
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
        txtNIT.setEditable(false);
        txtDireccion.setEditable(false);
        txtTelefono.setEditable(false);
        txtServicioPrestado.setEditable(false);
    }

    //Botones para Guardar, Eliminar, Editar y Reportar
    @FXML
    void Nuevo(ActionEvent event) {
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
                if (txtSaldoFavor.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("Debe Ingresar Datos para Guardar");
                    alert.show();
                } else {
                    ArrayList<TextField> listaTextField = new ArrayList<>();
                    listaTextField.add(txtSaldoFavor);
                    listaTextField.add(txtSaldoContra);
                    listaTextField.add(txtNIT);
                    listaTextField.add(txtTelefono);
                    listaTextField.add(txtDireccion);
                    listaTextField.add(txtServicioPrestado);

                    if (validar(listaTextField)) {
                        if (validarBigDecimal(txtSaldoContra.getText())) {
                            if (validarBigDecimal(txtSaldoFavor.getText())) {
                                btnNuevo.setDisable(false);
                                agregarProveedores();
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
                                alert.setContentText("EL Saldo a Favor es Incorrecto");
                                alert.show();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Kinal Mall");
                            alert.setHeaderText(null);
                            alert.setContentText("EL Saldo en Contra es Incorrecto");
                            alert.show();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Kinal Mall");
                        alert.setHeaderText(null);
                        alert.setContentText("Por favor llene todos los Campos de Texto");
                        alert.show();
                    }

                    break;
                }
        }
        System.out.println("Operación: " + operacion);
    }

    @FXML
    void editar(ActionEvent event
    ) {
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
                    limpiarCampos();
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
                listaTextField.add(txtId);
                listaTextField.add(txtSaldoFavor);
                listaTextField.add(txtSaldoContra);
                listaTextField.add(txtNIT);
                listaTextField.add(txtTelefono);
                listaTextField.add(txtDireccion);
                listaTextField.add(txtServicioPrestado);

                if (validar(listaTextField)) {
                    if (validarBigDecimal(txtSaldoContra.getText())) {
                        if (validarBigDecimal(txtSaldoFavor.getText())) {
                            EditarLocales();
                            limpiarCampos();
                            deshabilitarCampos();
                            cargarDatos();
                            btnNuevo.setDisable(false);
                            btnEliminar.setDisable(false);
                            btnEditar.setText("Editar");
                            imgEditar.setImage(new Image("/org/josuemarroquin/resource/images/btn Editar.png"));
                            btnReportar.setText("Reportar");
                            imgReportar.setImage(new Image("/org/josuemarroquin/resource/images/btn Cancelar.png"));
                            operacion = Operaciones.NINGUNO;
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Kinal Mall");
                            alert.setHeaderText(null);
                            alert.setContentText("EL Saldo a Favor es Incorrecto");
                            alert.show();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Kinal Mall");
                        alert.setHeaderText(null);
                        alert.setContentText("EL Saldo en Contra es Incorrecto");
                        alert.show();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("Por favor llene todos los Campos de Texto");
                    alert.show();
                }

                break;
        }

        System.out.println(
                "Operación: " + operacion);
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
                        eliminarProveedores();
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
                deshabilitarCampos();
                operacion = Operaciones.NINGUNO;
                break;
            case NINGUNO: //Reportar
                Map parametros = new HashMap();
                btnReportar.setText("Reportar");
                GenerarReportes.getInstance().MostrarReporte("reporteProveedores.jasper", "Reporte Proveedores", parametros);
                btnNuevo.setDisable(false);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                limpiarCampos();
                deshabilitarCampos();
                cargarDatos();
                operacion = Operaciones.NINGUNO;
                break;

        }
        System.out.println("Operación: " + operacion);
    }

    //Mostrar Cuentas por Pagar
    @FXML
    void mostrarCuentasporPagar(ActionEvent event) {
        escenarioPrincipal.mostrarCuentasporPagar();
    }

    //Seleccionar Elemento en Tabla
    @FXML
    void selecionarElemento(MouseEvent event) {
        seleccionarElemento();
    }

    //Metodo para Seleccionar Elemento
    public void seleccionarElemento() {
        try {
            txtId.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getId()));
            txtNIT.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNit());
            txtDireccion.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getDireccion());
            txtTelefono.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getTelefono());
            txtServicioPrestado.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getServicioPrestado());
            txtSaldoFavor.setText(String.valueOf((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getSaldoFavor())));
            txtSaldoContra.setText(String.valueOf((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getSaldoContra())));
            txtSaldoLiquido.setText(String.valueOf(calcularSaldoLiquido()));
        } catch (NullPointerException e) {

        }

    }

    //Metodo para Calcular Saldo Liquido
    public BigDecimal calcularSaldoLiquido() {
        BigDecimal saldoFavor = new BigDecimal(txtSaldoFavor.getText());
        BigDecimal saldoContra = new BigDecimal(txtSaldoContra.getText());
        BigDecimal retornar = saldoFavor.subtract(saldoContra);

        return retornar;
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
