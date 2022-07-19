/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josuemarroquin.controller;

import com.jfoenix.controls.JFXDatePicker;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.ComboBox;
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
 * @date 13 jul. 2021
 * @time 16:43:02 Codigo Tecnico: IN5BV Carnet: 2020296
 */
public class EmpeladosController implements Initializable {

    //Declaracion de Variables Privadas
    private Principal escenarioPrincipal;

    private enum Operaciones {
        NUEVO, GUARDAR, ACTUALIZAR, ELIMINAR, CANCELAR, NINGUNO
    }

    private Operaciones operacion = Operaciones.NINGUNO;

    private ObservableList<Empleados> listaEmpleados;
    private ObservableList<Departamentos> listaDepartamentos;
    private ObservableList<Cargos> listaCargos;
    private ObservableList<Horarios> listaHorarios;
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
    private TableView tblEmpleados;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colNombres;

    @FXML
    private TableColumn colApellidos;

    @FXML
    private TableColumn colEmail;

    @FXML
    private TableColumn colTelefono;

    @FXML
    private TableColumn colFechaContratacion;

    @FXML
    private TableColumn colSueldo;

    @FXML
    private TableColumn colDepartamentos;

    @FXML
    private TableColumn colCargos;

    @FXML
    private TableColumn colHorario;

    @FXML
    private TableColumn colAdministracion;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelefono;

    @FXML
    private JFXDatePicker dtFechaContratacion;

    @FXML
    private TextField txtSueldo;

    @FXML
    private ComboBox cmbDepartamentos;

    @FXML
    private ComboBox cmbHorarios;

    @FXML
    private ComboBox cmbCargos;

    @FXML
    private ComboBox cmbAdministracion;

    @FXML
    private Button btnHorarios;

    @FXML
    private ImageView imgIr;

    //ObservablesList para Listar Empleados
    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> lista = new ArrayList<>();

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados()}");
            ResultSet r = pstmt.executeQuery();

            while (r.next()) {
                lista.add(new Empleados(
                        r.getInt("id"),
                        r.getString("nombres"),
                        r.getString("apellidos"),
                        r.getString("email"),
                        r.getString("telefono"),
                        r.getDate("fechaContratacion"),
                        r.getBigDecimal("sueldo"),
                        r.getInt("idDepartamento"),
                        r.getInt("idCargo"),
                        r.getInt("idHorario"),
                        r.getInt("idAdministracion"))
                );
            }
            r.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaEmpleados = FXCollections.observableArrayList(lista);
        return listaEmpleados;
    }

    //ObservablesList para Listar Departamentos
    public ObservableList<Departamentos> getDepartamentos() {
        ArrayList<Departamentos> lista = new ArrayList<>();
        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDepartamentos()}");
            ResultSet r = pstmt.executeQuery();

            while (r.next()) {
                lista.add(new Departamentos(
                        r.getInt("id"),
                        r.getString("nombre"))
                );

            }

            r.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaDepartamentos = FXCollections.observableArrayList(lista);

        return listaDepartamentos;
    }

    //Metodo para Buscar Departamentos
    public Departamentos buscarDepartamentos(int id) {
        Departamentos departamentos = null;

        PreparedStatement pstmt = null;
        ResultSet r = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarDepartamentos(?)}");
            pstmt.setInt(1, id);
            r = pstmt.executeQuery();

            while (r.next()) {
                departamentos = new Departamentos(
                        r.getInt("id"),
                        r.getString("nombre"));
            }

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar buscar una Administracion con el ID " + id);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                r.close();
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return departamentos;
    }

    //ObservablesList para Listar Cargos
    public ObservableList<Cargos> getCargos() {
        ArrayList<Cargos> lista = new ArrayList<>();

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargos()}");
            ResultSet r = pstmt.executeQuery();

            while (r.next()) {
                lista.add(new Cargos(
                        r.getInt("id"),
                        r.getString("nombre"))
                );
            }
            r.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaCargos = FXCollections.observableArrayList(lista);
        return listaCargos;
    }

    //Metodo para Buscar Cargos
    public Cargos buscarCargos(int id) {
        Cargos cargos = null;

        PreparedStatement pstmt = null;
        ResultSet r = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarCargos(?)}");
            pstmt.setInt(1, id);
            r = pstmt.executeQuery();

            while (r.next()) {
                cargos = new Cargos(
                        r.getInt("id"),
                        r.getString("nombre"));
            }

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar buscar una Administracion con el ID " + id);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                r.close();
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return cargos;
    }

    //ObservablesList para Listar Horarios
    public ObservableList<Horarios> getHorarios() {
        ArrayList<Horarios> lista = new ArrayList<>();

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarHorarios()}");
            ResultSet r = pstmt.executeQuery();

            while (r.next()) {
                lista.add(new Horarios(
                        r.getInt("id"),
                        r.getTime("horarioEntrada"),
                        r.getTime("horarioSalida"),
                        r.getBoolean("lunes"),
                        r.getBoolean("martes"),
                        r.getBoolean("miercoles"),
                        r.getBoolean("jueves"),
                        r.getBoolean("viernes"))
                );
            }
            r.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaHorarios = FXCollections.observableArrayList(lista);
        return listaHorarios;
    }

    //Metodo para Buscar Horarios
    public Horarios buscarHorarios(int id) {
        Horarios horarios = null;

        PreparedStatement pstmt = null;
        ResultSet r = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarHorarios(?)}");
            pstmt.setInt(1, id);
            r = pstmt.executeQuery();

            while (r.next()) {
                horarios = new Horarios(r.getInt("id"),
                        r.getTime("horarioEntrada"),
                        r.getTime("horarioSalida"),
                        r.getBoolean("lunes"),
                        r.getBoolean("martes"),
                        r.getBoolean("miercoles"),
                        r.getBoolean("jueves"),
                        r.getBoolean("viernes"));
            }

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar buscar una Administracion con el ID " + id);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                r.close();
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return horarios;
    }

    //ObservablesList para Listar Adminitracion
    public ObservableList<Administracion> getAdministracion() {

        ArrayList<Administracion> listado = new ArrayList<>();
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaAdministracion = FXCollections.observableArrayList(listado);

        return listaAdministracion;

    }

    //Metodo para Buscar Adminitracion
    public Administracion buscarAdministracion(int id) {
        Administracion administracion = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarAdministracion(?)}");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                administracion = new Administracion(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        rs.getString("telefono")
                );
            }

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar buscar una Administracion con el ID " + id);
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return administracion;
    }

    //Agregar Datos en Empleados
    public void agregarEmpleados() {

        Empleados registro = new Empleados();
        registro.setNombres(txtNombre.getText());
        registro.setApellidos(txtApellido.getText());
        registro.setEmail(txtEmail.getText());
        registro.setTelefono(txtTelefono.getText());
        registro.setFechaContratacion(Date.valueOf(dtFechaContratacion.getValue()));
        registro.setSueldo(new BigDecimal(txtSueldo.getText()));
        registro.setIdDepartamento(((Departamentos) cmbDepartamentos.getSelectionModel().getSelectedItem()).getId());
        registro.setIdCargo(((Cargos) cmbCargos.getSelectionModel().getSelectedItem()).getId());
        registro.setIdHorario(((Horarios) cmbHorarios.getSelectionModel().getSelectedItem()).getId());
        registro.setIdAdministracion(((Administracion) cmbAdministracion.getSelectionModel().getSelectedItem()).getId());

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleados(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            pstmt.setString(1, registro.getNombres());
            pstmt.setString(2, registro.getApellidos());
            pstmt.setString(3, registro.getEmail());
            pstmt.setString(4, registro.getTelefono());
            pstmt.setDate(5, registro.getFechaContratacion());
            pstmt.setBigDecimal(6, registro.getSueldo());
            pstmt.setInt(7, registro.getIdDepartamento());
            pstmt.setInt(8, registro.getIdCargo());
            pstmt.setInt(9, registro.getIdHorario());
            pstmt.setInt(10, registro.getIdAdministracion());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Editar Datos en Empleados
    public void editarEmpleados() {
        Empleados registro = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();
        registro.setId(Integer.parseInt(txtId.getText()));
        registro.setNombres(txtNombre.getText());
        registro.setApellidos(txtApellido.getText());
        registro.setEmail(txtEmail.getText());
        registro.setTelefono(txtTelefono.getText());
        registro.setFechaContratacion(Date.valueOf(dtFechaContratacion.getValue()));
        registro.setSueldo(new BigDecimal(txtSueldo.getText()));
        registro.setIdDepartamento(((Departamentos) cmbDepartamentos.getSelectionModel().getSelectedItem()).getId());
        registro.setIdCargo(((Cargos) cmbCargos.getSelectionModel().getSelectedItem()).getId());
        registro.setIdHorario(((Horarios) cmbHorarios.getSelectionModel().getSelectedItem()).getId());
        registro.setIdAdministracion(((Administracion) cmbAdministracion.getSelectionModel().getSelectedItem()).getId());

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmpleados(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            pstmt.setInt(1, registro.getId());
            pstmt.setString(2, registro.getNombres());
            pstmt.setString(3, registro.getApellidos());
            pstmt.setString(4, registro.getEmail());
            pstmt.setString(5, registro.getTelefono());
            pstmt.setDate(6, registro.getFechaContratacion());
            pstmt.setBigDecimal(7, registro.getSueldo());
            pstmt.setInt(8, registro.getIdDepartamento());
            pstmt.setInt(9, registro.getIdCargo());
            pstmt.setInt(10, registro.getIdHorario());
            pstmt.setInt(11, registro.getIdAdministracion());

            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Eliminar Datos en Empleados
    public void eliminarEmpleados() {

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleados(?)}");
            pstmt.setInt(1, Integer.parseInt(txtId.getText()));
            pstmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Metodo Cargar Datos
    public void cargarDatos() {
        tblEmpleados.setItems(getEmpleados());
        colId.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("id"));
        colNombres.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidos"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Empleados, String>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empleados, String>("telefono"));
        colFechaContratacion.setCellValueFactory(new PropertyValueFactory<Empleados, Date>("fechaContratacion"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados, BigDecimal>("sueldo"));
        colDepartamentos.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("idDepartamento"));
        colCargos.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("idCargo"));
        colHorario.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("idHorario"));
        colAdministracion.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("idAdministracion"));

        cmbAdministracion.setItems(getAdministracion());
        cmbCargos.setItems(getCargos());
        cmbDepartamentos.setItems(getDepartamentos());
        cmbHorarios.setItems(getHorarios());
    }

    //Metodo para Validar los Text Field y Combo Box
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

    //Metodo para Validar el Telefono
    public boolean validarTelefono(String numero) {
        Pattern pattern = Pattern.compile("^[0-9]{8}$");
        Matcher matcher = pattern.matcher(numero);
        return matcher.matches();
    }

    //Metodo para validar el Email
    public boolean validarEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //Metodo para Validar el BigDecimal
    public boolean validarBigDecimal(String numero) {
        Pattern pattern = Pattern.compile("^\\d{1,8}([.]\\d{1,2})?$");
        Matcher matcher = pattern.matcher(numero);
        return matcher.matches();
    }

    //Habilitar Campos de los Text Field
    public void habilitarCampos() {
        txtId.setEditable(false);
        txtNombre.setEditable(true);
        txtApellido.setEditable(true);
        txtEmail.setEditable(true);
        txtTelefono.setEditable(true);
        txtSueldo.setEditable(true);
        cmbAdministracion.setDisable(false);
        cmbDepartamentos.setDisable(false);
        cmbCargos.setDisable(false);
        cmbHorarios.setDisable(false);
        dtFechaContratacion.setDisable(false);
    }

    //Deshablitar Campos de los Text Field
    public void deshabilitarCampos() {
        txtId.setEditable(false);
        txtNombre.setEditable(false);
        txtApellido.setEditable(false);
        txtEmail.setEditable(false);
        txtTelefono.setEditable(false);
        txtSueldo.setEditable(false);
        cmbAdministracion.setDisable(true);
        cmbDepartamentos.setDisable(true);
        cmbCargos.setDisable(true);
        cmbHorarios.setDisable(true);
        dtFechaContratacion.setDisable(false);
    }

    //Limpiar Campos de los Text Field
    public void LimpiarCampos() {
        txtId.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtEmail.clear();
        txtTelefono.clear();
        txtSueldo.clear();
        cmbAdministracion.valueProperty().set(null);
        cmbDepartamentos.valueProperty().set(null);
        cmbCargos.valueProperty().set(null);
        cmbHorarios.valueProperty().set(null);
        dtFechaContratacion.getEditor().clear();
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
                listaTextField.add(txtNombre);
                listaTextField.add(txtApellido);
                listaTextField.add(txtEmail);
                listaTextField.add(txtTelefono);
                listaTextField.add(txtSueldo);

                ArrayList<ComboBox> listaComboBox = new ArrayList<>();
                listaComboBox.add(cmbAdministracion);
                listaComboBox.add(cmbCargos);
                listaComboBox.add(cmbDepartamentos);
                listaComboBox.add(cmbHorarios);

                Alert aler = new Alert(Alert.AlertType.CONFIRMATION);
                aler.setTitle("Kinal Mall");
                aler.setHeaderText(null);
                aler.setContentText("¿Desea actualizar este registro?");

                Optional<ButtonType> respuesta = aler.showAndWait();

                if (respuesta.get() == ButtonType.OK) {
                if (validar(listaTextField, listaComboBox)) {
                    if (validarEmail(txtEmail.getText())) {
                        if (validarTelefono(txtTelefono.getText())) {
                            if (validarBigDecimal(txtSueldo.getText())) {
                                editarEmpleados();
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
                                alert.setContentText("EL Sueldo es Incorrecto");
                                alert.show();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Kinal Mall");
                            alert.setHeaderText(null);
                            alert.setContentText("EL Email es Incorrecto");
                            alert.show();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Kinal Mall");
                        alert.setHeaderText(null);
                        alert.setContentText("EL Telefono es Incorrecto");
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
        }
        System.out.println("Operación: " + operacion);
    }

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
                        eliminarEmpleados();
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
                if (txtNombre.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("Debe Ingresar Datos para Guardar");
                    alert.show();
                } else {

                    ArrayList<TextField> listaTextField = new ArrayList<>();
                    listaTextField.add(txtNombre);

                    ArrayList<ComboBox> listaComboBox = new ArrayList<>();
                    listaComboBox.add(cmbAdministracion);
                    listaComboBox.add(cmbCargos);
                    listaComboBox.add(cmbDepartamentos);
                    listaComboBox.add(cmbHorarios);

                    if (validar(listaTextField, listaComboBox)) {
                        if (validarEmail(txtEmail.getText())) {
                            if (validarTelefono(txtTelefono.getText())) {
                                if (validarBigDecimal(txtSueldo.getText())) {
                                    btnNuevo.setDisable(false);
                                    agregarEmpleados();
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
                                    alert.setContentText("EL Sueldo es Incorrecto");
                                    alert.show();
                                }
                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Kinal Mall");
                                alert.setHeaderText(null);
                                alert.setContentText("EL Email es Incorrecto");
                                alert.show();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Kinal Mall");
                            alert.setHeaderText(null);
                            alert.setContentText("EL Telefono es Incorrecto ");
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
    void reportar(ActionEvent event) {
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
                GenerarReportes.getInstance().MostrarReporte("reporteEmpleado.jasper", "Reporte Empleado", parametros);
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
    void seleccionarElemento(MouseEvent event) {
        seleccionarElemento();
    }

    //Metodo para Seleccionar Elemento
    public void seleccionarElemento() {
        try {
            txtId.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getId()));
            txtNombre.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getNombres());
            txtApellido.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getApellidos());
            txtEmail.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getEmail());
            txtTelefono.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getTelefono());
            txtSueldo.setText(String.valueOf(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));

            cmbAdministracion.getSelectionModel().select(buscarAdministracion(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getIdAdministracion()));
            cmbDepartamentos.getSelectionModel().select(buscarDepartamentos(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getIdDepartamento()));
            cmbCargos.getSelectionModel().select(buscarCargos(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getIdCargo()));
            cmbHorarios.getSelectionModel().select(buscarHorarios(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getIdHorario()));

            dtFechaContratacion.setValue(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getFechaContratacion().toLocalDate());
        } catch (NullPointerException e) {

        }
    }

    //Regresar a Menu Principal
    @FXML
    void btnRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarMenuPrincipal();
    }

    //Mostrar Vista Horarios
    @FXML
    void mostrarHorarios(ActionEvent event) {
        escenarioPrincipal.mostrarHorarios();
    }

    //Metodos Get y Set de la Clase Principal
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}
