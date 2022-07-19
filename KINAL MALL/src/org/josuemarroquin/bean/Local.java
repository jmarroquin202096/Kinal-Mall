/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.josuemarroquin.bean;
import java.math.BigDecimal;

/**
 *
 * @author Josué Daniel Marroquín Hernández <jmarroquin-2020296@kinal.edu.gt>
 * @date 12 jun. 2021
 * @time 13:39:58
 * Codigo Tecnico: IN5BV
 * Carnet: 2020296
 */
public class Local {
    private int id;
    private BigDecimal saldoFavor;
    private BigDecimal saldoContra;
    private int mesesPendientes;
    private boolean disponibilidad;
    private BigDecimal valorLocal;
    private BigDecimal ValorAdministracion;

    public Local() {
    }
    

    public Local(int id, BigDecimal saldoFavor, BigDecimal saldoContra, int mesesPendientes, 
                boolean disponibilidad, BigDecimal valorLocal, BigDecimal ValorAdministracion) {
        this.id = id;
        this.saldoFavor = saldoFavor;
        this.saldoContra = saldoContra;
        this.mesesPendientes = mesesPendientes;
        this.disponibilidad = disponibilidad;
        this.valorLocal = valorLocal;
        this.ValorAdministracion = ValorAdministracion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getSaldoFavor() {
        return saldoFavor;
    }

    public void setSaldoFavor(BigDecimal saldoFavor) {
        this.saldoFavor = saldoFavor;
    }

    public BigDecimal getSaldoContra() {
        return saldoContra;
    }

    public void setSaldoContra(BigDecimal saldoContra) {
        this.saldoContra = saldoContra;
    }

    public int getMesesPendientes() {
        return mesesPendientes;
    }

    public void setMesesPendientes(int mesesPendientes) {
        this.mesesPendientes = mesesPendientes;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public BigDecimal getValorLocal() {
        return valorLocal;
    }

    public void setValorLocal(BigDecimal valorLocal) {
        this.valorLocal = valorLocal;
    }

    public BigDecimal getValorAdministracion() {
        return ValorAdministracion;
    }

    public void setValorAdministracion(BigDecimal ValorAdministracion) {
        this.ValorAdministracion = ValorAdministracion;
    }

    @Override
    public String toString() {

        String strDisponible = "";

        if (disponibilidad) {
            strDisponible = "Disponible";
        }else{
            strDisponible = "No Disponible";
        }

        return id + " | " + strDisponible;
    }
    
}
