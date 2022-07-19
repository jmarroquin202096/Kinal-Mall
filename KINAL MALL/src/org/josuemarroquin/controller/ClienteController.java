/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josuemarroquin.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.josuemarroquin.DB.Conexion;
import org.josuemarroquin.bean.*;
import org.josuemarroquin.system.*;
import java.sql.ResultSet;
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
import org.josuemarroquin.Report.GenerarReportes;

/**
 *
 * @author Josué Daniel Marroquín Hernández <jmarroquin-2020296@kinal.edu.gt>
 * @date 9 jun. 2021
 * @time 16:42:10 Codigo Tecnico: IN5BV Carnet: 2020296
 */
public class ClienteController implements Initializable {

    //Declaracion de Variables Privadas
    private Principal escenarioPrincipal;

    private enum Operaciones {
        NUEVO, GUARDAR, EDITAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO
    }

    private Operaciones operacion = Operaciones.NINGUNO;

    private ObservableList<Clientes> listaClientes;
    private ObservableList<TipoCliente> listatipoCliente;
    private ObservableList<Local> listaLocal;

    //Metodo Initializable
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    //Declaracion de las Variables FXML
    @FXML
    private Button btNuevo;

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
    private Button btnVistaCuentasporCobrar;

    @FXML
    private TableView tblClientes;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colNombre;

    @FXML
    private TableColumn colApellido;

    @FXML
    private TableColumn colTelefono;

    @FXML
    private TableColumn colEmail;

    @FXML
    private TableColumn colDireccion;

    @FXML
    private TableColumn colTipoCliente;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtDireccion;

    @FXML
    private ComboBox cmbTipoCliente;

    //ObservablesList para Listar Clientes
    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet r = pstmt.executeQuery();

            while (r.next()) {
                lista.add(new Clientes(
                        r.getInt("id"),
                        r.getString("nombres"),
                        r.getString("apellidos"),
                        r.getString("telefono"),
                        r.getString("direccion"),
                        r.getString("email"),
                        r.getInt("idTipoCliente"))
                );
            }

            r.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        listaClientes = FXCollections.observableArrayList(lista);
        return listaClientes;
    }

    //ObservablesList para Listar Tipo CLientes
    public ObservableList<TipoCliente> getTipoClientes() {
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

        listatipoCliente = FXCollections.observableArrayList(lista);
        return listatipoCliente;
    }

    //Metodo para Buscar Tipo Clientes
    private TipoCliente buscarTipoCliente(int id) {
        TipoCliente registro = null;

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoCliente(?)}");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                registro = new TipoCliente(
                        rs.getInt("id"),
                        rs.getString("descripcion"));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return registro;
    }

    //Agregar Datos en Cargos
    public void agregarClientes() {
        Clientes registro = new Clientes();
        registro.setNombres(txtNombre.getText());
        registro.setApellidos(txtApellido.getText());
        registro.setTelefono(txtTelefono.getText());
        registro.setEmail(txtEmail.getText());
        registro.setDireccion(txtDireccion.getText());
        registro.setIdTipoCliente(((TipoCliente) cmbTipoCliente.getSelectionModel().getSelectedItem()).getId());

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarClientes(?,?,?,?,?,?)}");
            pstmt.setString(1, registro.getNombres());
            pstmt.setString(2, registro.getApellidos());
            pstmt.setString(3, registro.getTelefono());
            pstmt.setString(4, registro.getDireccion());
            pstmt.setString(5, registro.getEmail());
            pstmt.setInt(6, registro.getIdTipoCliente());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Editar Datos en Cargos
    public void editarClientes() {

        Clientes registro = new Clientes();
        registro.setId(Integer.parseInt(txtId.getText()));
        registro.setNombres(txtNombre.getText());
        registro.setApellidos(txtApellido.getText());
        registro.setTelefono(txtTelefono.getText());
        registro.setEmail(txtEmail.getText());
        registro.setDireccion(txtDireccion.getText());
        registro.setIdTipoCliente(((TipoCliente) cmbTipoCliente.getSelectionModel().getSelectedItem()).getId());
        System.out.println(registro);

        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarClientes(?,?,?,?,?,?,?)}");
            pstmt.setInt(1, registro.getId());
            pstmt.setString(2, registro.getNombres());
            pstmt.setString(3, registro.getApellidos());
            pstmt.setString(4, registro.getTelefono());
            pstmt.setString(5, registro.getDireccion());
            pstmt.setString(6, registro.getEmail());
            pstmt.setInt(7, registro.getIdTipoCliente());

            System.out.println(pstmt.toString());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Eliminar Datos en Cargos
    public void eliminarClientes() {
        try {
            PreparedStatement pstmt;
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarClientes(?)}");
            //pstmt.setInt(1, Integer.parseInt(txtId.getText()));
            pstmt.setInt(1, ((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getId());
            pstmt.execute();
            System.out.println(pstmt.toString() + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Metodo Cargar Datos
    public void cargarDatos() {

        tblClientes.setItems(getClientes());
        colId.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombres"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidos"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefono"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Clientes, String>("email"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccion"));
        colTipoCliente.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("idTipoCliente"));

        cmbTipoCliente.setItems(getTipoClientes());
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

    //Deshabilitar Campos de los Text Field y Combo Box
    public void desactivarCampos() {
        txtId.setEditable(false);
        txtApellido.setEditable(false);
        txtNombre.setEditable(false);
        txtDireccion.setEditable(false);
        txtEmail.setEditable(false);
        txtTelefono.setEditable(false);
        cmbTipoCliente.setDisable(true);

    }

    //Habilitar Campos de los Text Field y Combo Box
    public void habilitarCampos() {
        txtId.setEditable(false);
        txtApellido.setEditable(true);
        txtNombre.setEditable(true);
        txtDireccion.setEditable(true);
        txtEmail.setEditable(true);
        txtTelefono.setEditable(true);
        cmbTipoCliente.setDisable(false);
    }

    //Limpiar Campos de los Text Field y Combo Box
    public void limpiarCampos() {
        txtId.clear();
        txtApellido.clear();
        txtNombre.clear();
        txtDireccion.clear();
        txtEmail.clear();
        txtTelefono.clear();
        cmbTipoCliente.valueProperty().set(null);
    }

    //Metodo para validar el Telefono
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
                    btNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    operacion = Operaciones.ACTUALIZAR;
                }
                break;
            case ACTUALIZAR:
                ArrayList<TextField> listaTextField = new ArrayList<>();
                listaTextField.add(txtNombre);
                listaTextField.add(txtApellido);
                listaTextField.add(txtDireccion);
                listaTextField.add(txtTelefono);
                listaTextField.add(txtEmail);

                ArrayList<ComboBox> listaComboBox = new ArrayList<>();
                listaComboBox.add(cmbTipoCliente);

                if (validarTelefono(txtTelefono.getText())) {

                    if (validarEmail(txtEmail.getText())) {

                        if (validar(listaTextField, listaComboBox)) {
                            editarClientes();
                            limpiarCampos();
                            desactivarCampos();
                            cargarDatos();
                            btNuevo.setDisable(false);
                            btnEliminar.setDisable(false);
                            btnEditar.setText("Editar");
                            imgEditar.setImage(new Image("/org/josuemarroquin/resource/images/btn Editar.png"));
                            btnReportar.setText("Reportar");
                            imgReportar.setImage(new Image("/org/josuemarroquin/resource/images/btn Reporte.png"));
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
                        alert.setContentText("El Correo Electronico no es Valido");
                        alert.show();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("El Numero es Invalido");
                    alert.show();
                }
                System.out.println("Operación: " + operacion);
        }
    }

    @FXML
    void eliminar(ActionEvent event
    ) {
        System.out.println("Operación: " + operacion);
        switch (operacion) {
            case GUARDAR:
                btNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image("/org/josuemarroquin/resource/images/btn Nuevo.png"));
                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image("/org/josuemarroquin/resource/images/btn Eliminar.png"));
                btnEditar.setDisable(false);
                btnReportar.setDisable(false);
                limpiarCampos();
                desactivarCampos();
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
                        eliminarClientes();
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
                limpiarCampos();
                habilitarCampos();
                btNuevo.setText("Guardar");
                imgNuevo.setImage(new Image("/org/josuemarroquin/resource/images/btn Guardar.png"));
                btnEliminar.setText("Cancelar");
                imgEliminar.setImage(new Image("/org/josuemarroquin/resource/images/btn Cancelar.png"));
                btnEditar.setDisable(true);
                btnReportar.setDisable(true);
                operacion = Operaciones.GUARDAR;
                break;
            case GUARDAR:
                if (txtNombre.getText().isEmpty() && txtApellido.getText().isEmpty() && txtTelefono.getText().isEmpty()
                        && txtDireccion.getText().isEmpty() && txtDireccion.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Kinal Mall");
                    alert.setHeaderText(null);
                    alert.setContentText("Debe Ingresar Datos para Guardar");
                    alert.show();
                } else {

                    ArrayList<TextField> listaTextField = new ArrayList<>();
                    listaTextField.add(txtNombre);
                    listaTextField.add(txtApellido);
                    listaTextField.add(txtDireccion);
                    listaTextField.add(txtTelefono);
                    listaTextField.add(txtEmail);

                    ArrayList<ComboBox> listaComboBox = new ArrayList<>();
                    listaComboBox.add(cmbTipoCliente);

                    if (validarTelefono(txtTelefono.getText())) {

                        if (validarEmail(txtEmail.getText())) {

                            if (validar(listaTextField, listaComboBox)) {

                                btNuevo.setDisable(false);
                                agregarClientes();
                                cargarDatos();
                                limpiarCampos();
                                btNuevo.setText("Nuevo");
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
                            alert.setContentText("El Correo Electronico es Inválido");
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
                btNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                limpiarCampos();
                desactivarCampos();
                operacion = Operaciones.NINGUNO;
                break;
            case NINGUNO: //Reportar
                Map parametros = new HashMap();
                btnReportar.setText("Reportar");
                GenerarReportes.getInstance().MostrarReporte("reporteClientes.jasper", "Reporte Clientes", parametros);
                btNuevo.setDisable(false);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                limpiarCampos();
                desactivarCampos();
                cargarDatos();
                operacion = Operaciones.NINGUNO;
                break;

        }
        System.out.println("Operación: " + operacion);
    }

    //Mostrar la Vista Cuentas por Cobrar
    @FXML
    void mostrarVistaCuentasporCobrar(ActionEvent event) {
        escenarioPrincipal.mostrarCuentasporCobrar();
    }

    //Seleccionar Elemento en Tabla
    @FXML
    void selecionarElemento(MouseEvent event) {
        selecionarElemento();
    }

    //Metodo para Seleccionar Elemento
    public void selecionarElemento() {

        try {
            txtId.setText(String.valueOf(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getId()));
            txtNombre.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNombres());
            txtApellido.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getApellidos());
            txtTelefono.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getTelefono());
            txtEmail.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getEmail());
            txtDireccion.setText(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getDireccion());
            cmbTipoCliente.getSelectionModel().select(buscarTipoCliente(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getIdTipoCliente()));

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
