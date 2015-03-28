package com.example.facundo.recorridaszotp._3_Domain;

import com.example.facundo.recorridaszotp._1_Infraestructure.Utils;
import com.google.android.gms.maps.model.LatLng;
import com.orm.SugarRecord;


public class Persona extends SugarRecord<Persona> {
    private int id; // -1 si es una persona no guardada en la BDWeb
    private String nombre;
    private String apellido;
    private String direccion;
    private String descripcion;
    private String ultMod;
    public Zona zona;

    public Persona() {
    }

    public Persona(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUltMod() {
        return ultMod;
    }

    public void setUltMod(String ultMod) {
        this.ultMod = ultMod;
    }

    public void setId(int id) {
        this.id = id;
    }



}
