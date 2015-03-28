package com.example.facundo.recorridaszotp._3_Domain;

import java.math.BigDecimal;
import com.example.facundo.recorridaszotp._1_Infraestructure.Utils;
import com.google.android.gms.maps.model.LatLng;
import com.orm.SugarRecord;


public class Persona extends SugarRecord<Persona> {
    private int id; // -1 si es una persona no guardada en la BDWeb
    private String nombre;
    private String apellido;
    private String direccion;
    private String zona;
    private String descripcion;
    private String ultMod;
    private String estado;

    public Persona() {
    }

    public Persona(int id, String nombre, String apellido, String direccion,
                   String zona, String descripcion, LatLng ubicacion, String ultMod,
                   String estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.zona = zona;
        this.descripcion = descripcion;
        this.ultMod = ultMod;
        this.estado = estado;
    }

    public Persona(String nombre, String apellido, String direccion,
                   String zona, String descripcion, LatLng ubicacion) {
        this(-1, nombre, apellido, direccion, zona, descripcion, ubicacion, Utils.getDateTime(), Utils.EST_NUEVO);
    }

    public Persona(String nombre, String apellido, LatLng latLng) {
        this(-1, nombre, apellido, "", "", "", latLng, Utils.getDateTime(), Utils.EST_NUEVO);
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

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setId(int id) {
        this.id = id;
    }



}
