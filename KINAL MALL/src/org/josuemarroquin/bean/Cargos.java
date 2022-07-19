/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.josuemarroquin.bean;

/**
 *
 * @author Josué Daniel Marroquín Hernández <jmarroquin-2020296@kinal.edu.gt>
 * @date 13 jul. 2021
 * @time 18:48:20
 * Codigo Tecnico: IN5BV
 * Carnet: 2020296
 */
public class Cargos {
    
    private int id;
    private String nombre;

    public Cargos() {
    }

    public Cargos(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return  id +  " | " + " " + nombre;
    }
    
    

}
