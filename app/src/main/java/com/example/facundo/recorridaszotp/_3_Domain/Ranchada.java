package com.example.facundo.recorridaszotp._3_Domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;

/**
 * Created by GoRodriguez on 16/11/2015.
 */
@Table(name = "Ranchadas")
public class Ranchada extends Model {
    @Column(name = "WebId")
    private int webId = -1;
    @Column(name = "Estado")
    private int estado;
    @Column(name = "Nombre")
    private String nombre = "";
    @Column(name = "Zona")
    private Zona zona = null;
    @Column(name = "Latitud")
    private double latitud = Double.NaN;
    @Column(name = "Longitud")
    private double longitud = Double.NaN;
    @Column(name = "Descripcion")
    private String descripcion = "";

    public Ranchada() {
        super();
    }

    public Ranchada(String nombre) {
        super();
        this.nombre = nombre;
        this.estado = Utils.EST_ACTUALIZADO;
    }

    public String getNombre() {
        return nombre;
    }

    public void mergeFromWeb(Ranchada ranchada) throws Exception {
        if (ranchada.webId != this.getWebId()) {
            throw new Exception("MergeConDiferenteWebId");
        }
        this.webId = ranchada.getWebId();
        this.estado = ranchada.getEstado();
        this.nombre = ranchada.getNombre();
        this.zona = ranchada.getZona();
        this.latitud = ranchada.getLatitud();
        this.longitud = ranchada.getLongitud();
        this.descripcion = ranchada.getDescripcion();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return this.estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getWebId() {
        return this.webId;
    }

    public void setWebId(int webId) {
        this.webId = webId;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
