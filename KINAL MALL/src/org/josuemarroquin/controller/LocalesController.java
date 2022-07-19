/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josuemarroquin.controller;

import java.math.*;
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
import javafx.scene.control.CheckBox;
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
 * @date 10 jun. 2021
 * @time 17:42:32 Codigo Tecnico: IN5BV Carnet: 2020296
 */
public class LocalesController implements Initializable {

    //Declaracion de Variables Privadas
    private Principal escenarioPrincipal;

    private enum Operaciones {
        NUEVO, GUARDAR, EDITAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private Operaciones operacion = Operaciones.NINGUNO;

    private ObservableList<Local> listaLocal;

    //Metodo Initializable
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        contarLocalesDisponibles();
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
    private TableView tblLocales;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colSaldoFavor;

    @FXML
    private TableColumn colSaldoContra;

    @FXML
    private TableColumn colMesesPendientes;

    @FXML
    private TableColumn colDisponibilidad;

    @FXML
    private TableColumn colValorLocal;

    @FXML
    private TableColumn colValorAdministracion;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtSaldoFavor;

    @FXML
    private TextField txtSaldoContra;

    @FXML
    private CheckBox cbxDisponible;

    @FXML
    private TextField txtMesesPendientes;

    @FXML
    private TextField txtValorLocal;

    @FXML
    private TextField txtValorAdministracion;
    @FXML
    private TextField txtSaldoLiquido;

    @FXML
    private TextField txtLocalesDisponibles;

    //ObservablesList para Listar Locales
    public ObservableList<Local> getLocal() {
        ArrayList<Local> lista = new ArrayList<>();

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarLocales()}");
            ResultSet r = pstmt.executeQuery();

            while (r.next()) {
                lista.add(new Local(
                        r.getInt("id"),
                        r.getBigDecimal("saldoFavor"),
                        r.getBigDecimal("saldoContra"),
                        r.getInt("mesesPendientes"),
                        r.getBoolean("disponibilidad"),
                        r.getBigDecimal("valorLocal"),
                        r.getBigDecimal("valorAdministracion")
                )
                );
            }
            r.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaLocal = FXCollections.observableArrayList(lista);
        return listaLocal;
    }

    //Agregar Datos en Locales
    public void agregarLocales() {

        Local registro = new Local();
        registro.setSaldoFavor(new BigDecimal(txtSaldoFavor.getText()));
        registro.setSaldoContra(new BigDecimal(txtSaldoContra.getText()));
        registro.setMesesPendientes(Integer.valueOf(txtMesesPendientes.getText()));
        registro.setDisponibilidad(cbxDisponible.isSelected());
        registro.setValorLocal(new BigDecimal(txtValorLocal.getText()));
        registro.setValorAdministracion(new BigDecimal(txtValorAdministracion.getText()));

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarLocales(?,?,?,?,?,?)}");
            pstmt.setBigDecimal(1, registro.getSaldoFavor());
            pstmt.setBigDecimal(2, registro.getSaldoContra());
            pstmt.setInt(3, registro.getMesesPendientes());
            pstmt.setBoolean(4, registro.isDisponibilidad());
            pstmt.setBigDecimal(5, registro.getValorLocal());
            pstmt.setBigDecimal(6, registro.getValorAdministracion());

            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    //Editar Datos en Locales
    public void EditarLocales() {
        Local registro = (Local) tblLocales.getSelectionModel().getSelectedItem();
        registro.setId(Integer.valueOf(txtId.getText()));
        registro.setSaldoFavor(new BigDecimal(txtSaldoFavor.getText()));
        registro.setSaldoContra(new BigDecimal(txtSaldoContra.getText()));
        registro.setMesesPendientes(Integer.valueOf(txtMesesPendientes.getText()));
        registro.setDisponibilidad(cbxDisponible.isSelected());
        registro.setValorLocal(new BigDecimal(txtValorLocal.getText()));
        registro.setValorAdministracion(new BigDecimal(txtValorAdministracion.getText()));

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarLocales(?,?,?,?,?,?,?)}");
            pstmt.setInt(1, registro.getId());
            pstmt.setBigDecimal(2, registro.getSaldoFavor());
            pstmt.setBigDecimal(3, registro.getSaldoContra());
            pstmt.setInt(4, registro.getMesesPendientes());
            pstmt.setBoolean(5, registro.isDisponibilidad());
            pstmt.setBigDecimal(6, registro.getValorLocal());
            pstmt.setBigDecimal(7, registro.getValorAdministracion());

            pstmt.executeUpdate();
            System.out.println(pstmt.toString());

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    //Eliminar Datos en Locales
    public void eliminarLocales() {
        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarLocales(?)}");
            pstmt.setInt(1, Integer.parseInt(txtId.getText()));
            pstmt.execute();
            System.out.println(pstmt.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Metodo Cargar Datos
    public void cargarDatos() {
        tblLocales.setItems(getLocal());
        colId.setCellValueFactory(new PropertyValueFactory<Local, Integer>("id"));
        colSaldoFavor.setCellValueFactory(new PropertyValueFactory<Local, BigDecimal>("saldoFavor"));
        colSaldoContra.setCellValueFactory(new PropertyValueFactory<Local, BigDecimal>("saldoContra"));
        colMesesPendientes.setCellValueFactory(new PropertyValueFactory<Local, Integer>("mesesPendientes"));
        colDisponibilidad.setCellValueFactory(new PropertyValueFactory<Local, Boolean>("disponibilidad"));
        colValorLocal.setCellValueFactory(new PropertyValueFactory<Local, BigDecimal>("valorLocal"));
        colValorAdministracion.setCellValueFactory(new PropertyValueFactory<Local, BigDecimal>("valorAdministracion"));
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

    //Metodo para Validar los Meses
    public boolean validarMeses(String numero) {
        Pattern pattern = Pattern.compile("^[true, false]$");
        Matcher matcher = pattern.matcher(numero);
        return matcher.matches();
    }

    //Limpiar Campos de los Text Field y Check Box
    public void limpiarCampos() {
        txtId.clear();
        txtSaldoFavor.clear();
        txtSaldoContra.clear();
        txtMesesPendientes.clear();
        txtValorLocal.clear();
        txtSaldoLiquido.clear();
        txtValorAdministracion.clear();
        cbxDisponible.setSelected(false);
    }

    //Habilitar Campos de los Text Field y Check Box
    public void habilitarCampos() {
        txtId.setEditable(true);
        txtSaldoFavor.setEditable(true);
        txtSaldoContra.setEditable(true);
        txtMesesPendientes.setEditable(true);
        txtValorLocal.setEditable(true);
        txtValorAdministracion.setEditable(true);
        cbxDisponible.setSelected(false);
    }

    //Deshabilitar Campos de los Text Field y Check Box
    public void deshabilitarCampos() {
        txtId.setEditable(false);
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
        txtMesesPendientes.setEditable(false);
        txtValorLocal.setEditable(false);
        txtValorAdministracion.setEditable(false);
        cbxDisponible.setSelected(false);
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
                listaTextField.add(txtSaldoFavor);
                listaTextField.add(txtSaldoContra);
                listaTextField.add(txtMesesPendientes);
                listaTextField.add(txtValorLocal);
                listaTextField.add(txtValorAdministracion);

                if (validar(listaTextField)) {
                    if (validarBigDecimal(txtSaldoFavor.getText())) {
                        if (validarBigDecimal(txtSaldoContra.getText())) {
                            if (validarBigDecimal(txtValorAdministracion.getText())) {
                                if (validarBigDecimal(txtValorLocal.getText())) {
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
                                    alert.setContentText("EL Valor de Local es Incorrecto");
                                    alert.show();
                                }
                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Kinal Mall");
                                alert.setHeaderText(null);
                                alert.setContentText("EL Valor de Administracion es Incorrecto");
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
                        alert.setContentText("EL Saldo a Favor es Incorrecto");
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
                        eliminarLocales();
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
                    listaTextField.add(txtMesesPendientes);
                    listaTextField.add(txtValorLocal);
                    listaTextField.add(txtValorAdministracion);

                    if (validar(listaTextField)) {
                        if (validarBigDecimal(txtSaldoFavor.getText())) {
                            if (validarBigDecimal(txtSaldoContra.getText())) {
                                if (validarBigDecimal(txtValorAdministracion.getText())) {
                                    if (validarBigDecimal(txtValorLocal.getText())) {
                                        btnNuevo.setDisable(false);
                                        agregarLocales();
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
                                        alert.setContentText("EL Valor de Local es Incorrecto");
                                        alert.show();
                                    }
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setTitle("Kinal Mall");
                                    alert.setHeaderText(null);
                                    alert.setContentText("EL Valor de Administracion es Incorrecto");
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
                            alert.setContentText("EL Saldo a Favor es Incorrecto");
                            alert.show();
                        }
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
                GenerarReportes.getInstance().MostrarReporte("reporteLocales.jasper", "Reporte Locales", parametros);
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

    //Seleccionar Elemento en Tabla
    @FXML
    void selecionarElemento(MouseEvent event
    ) {
        selecionarElemento();
    }
    //Metodo para Seleccionar Elemento

    public void selecionarElemento() {

        try {
            txtId.setText(String.valueOf(((Local) tblLocales.getSelectionModel().getSelectedItem()).getId()));
            txtSaldoFavor.setText(String.valueOf((((Local) tblLocales.getSelectionModel().getSelectedItem()).getSaldoFavor())));
            txtSaldoContra.setText(String.valueOf((((Local) tblLocales.getSelectionModel().getSelectedItem()).getSaldoContra())));
            txtMesesPendientes.setText(String.valueOf(((Local) tblLocales.getSelectionModel().getSelectedItem()).getMesesPendientes()));
            txtValorAdministracion.setText(String.valueOf((((Local) tblLocales.getSelectionModel().getSelectedItem()).getValorAdministracion())));
            txtValorLocal.setText(String.valueOf((((Local) tblLocales.getSelectionModel().getSelectedItem()).getValorLocal())));
            txtSaldoLiquido.setText(String.valueOf(calcularSaldoLiquido()));

            cbxDisponible.setText(String.valueOf((((Local) tblLocales.getSelectionModel().getSelectedItem()).isDisponibilidad())));
            cbxDisponible.setSelected(((Local) tblLocales.getSelectionModel().getSelectedItem()).isDisponibilidad());
            cbxDisponible.setText(cambiarNombreBox(cbxDisponible));
        } catch (NullPointerException e) {

        }
    }

    //Metodo para Calcular el Saldo Liquido
    public BigDecimal calcularSaldoLiquido() {
        BigDecimal saldoFavor = new BigDecimal(txtSaldoFavor.getText());
        BigDecimal saldoContra = new BigDecimal(txtSaldoContra.getText());
        BigDecimal retornar = saldoFavor.subtract(saldoContra);

        return retornar;
    }

    //Action del Check Box
    @FXML
    void checkdisponible(ActionEvent event) {
        cbxDisponible.setText(String.valueOf(cbxDisponible.isSelected()));
    }

    //Metodo para Cambiar el nombre al Check Box
    public String cambiarNombreBox(CheckBox asdBox) {
        String estado = null;

        if (asdBox.isSelected()) {
            estado = "Disponible";
        } else {
            estado = "No Disponible";
        }
        return estado;
    }

    //Metodo para Mostrar Locales Disponibles
    public void contarLocalesDisponibles() {
        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_CalcularLocalesDisponibles()}");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                txtLocalesDisponibles.setText(String.valueOf(rs.getInt("cantidas")));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {

        }
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
