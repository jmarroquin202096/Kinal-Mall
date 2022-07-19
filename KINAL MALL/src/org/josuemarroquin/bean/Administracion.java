/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.josuemarroquin.bean;

/**
 *
 * @author Josué Daniel Marroquín Hernández <jmarroquin-2020296@kinal.edu.gt>
 * @date 27 may. 2021
 * @time 21:57:45
 * Codigo Tecnico: IN5BV
 * Carnet: 2020296
 */
public class Administracion {
    
    private int id;
    private String direccion;
    private String telefono;

    public Administracion() {
    }


    public Administracion(int id, String direccion, String telefono) {
        this.id = id;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return  id + "|" + " " + direccion + " " + telefono;
    }
    

}

