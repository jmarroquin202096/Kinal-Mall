/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.josuemarroquin.bean;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Josué Daniel Marroquín Hernández <jmarroquin-2020296@kinal.edu.gt>
 * @date 3 jul. 2021
 * @time 23:29:50 Codigo Tecnico: IN5BV Carnet: 2020296
 */
public class CuentasporPagar {

    private int id;
    private String numeroFactura;
    private Date fechaLimitePago;
    private String estadoPago;
    private BigDecimal valorNetoPago;
    private int idAdministracion;
    private int idProveedor;

    public CuentasporPagar() {
    }

    public CuentasporPagar(int id, String numeroFactura, Date fechaLimitePago, String estadoPago, BigDecimal valorNetoPago, int idAdministracion, int idProveedor) {
        this.id = id;
        this.numeroFactura = numeroFactura;
        this.fechaLimitePago = fechaLimitePago;
        this.estadoPago = estadoPago;
        this.valorNetoPago = valorNetoPago;
        this.idAdministracion = idAdministracion;
        this.idProveedor = idProveedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Date getFechaLimitePago() {
        return fechaLimitePago;
    }

    public void setFechaLimitePago(Date fechaLimitePago) {
        this.fechaLimitePago = fechaLimitePago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public BigDecimal getValorNetoPago() {
        return valorNetoPago;
    }

    public void setValorNetoPago(BigDecimal valorNetoPago) {
        this.valorNetoPago = valorNetoPago;
    }

    public int getIdAdministracion() {
        return idAdministracion;
    }

    public void setIdAdministracion(int idAdministracion) {
        this.idAdministracion = idAdministracion;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    @Override
    public String toString() {
        return "CuentasporPagar{" + "id=" + id + ", numeroFactura=" + numeroFactura + ", fechaLimitePago=" + fechaLimitePago + ", estadoPago=" + estadoPago + ", valorNetoPago=" + valorNetoPago + ", idAdministracion=" + idAdministracion + ", idProveedor=" + idProveedor + '}';
    }  
}
