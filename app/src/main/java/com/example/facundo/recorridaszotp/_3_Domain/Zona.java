package com.example.facundo.recorridaszotp._3_Domain;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Facundo on 28/03/2015.
 */

@Table(name = "Zonas")
public class Zona {
    @Column(name = "Nombre")
    private String nombre;
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
