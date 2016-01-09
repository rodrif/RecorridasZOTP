package com.example.facundo.recorridaszotp._3_Domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.RanchadaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.ZonaDataAccess;

/**
 * Created by Gonzalo on 09/01/2016.
 */
@Table(name = "Familias")
public class Familia extends Model {
    @Column(name = "WebId")
    private int webId = -1;
    @Column(name = "Estado")
    private int estado;
    @Column(name = "Nombre")
    private String nombre = "";
    @Column(name = "Zona")
    private Zona zona = null;
    @Column(name = "Ranchada")
    private Ranchada ranchada = null;
    @Column(name = "Descripcion")
    private String descripcion = "";

    public Familia(String nombre) {
        super();
        this.nombre = nombre;
        this.estado = Utils.EST_ACTUALIZADO;
    }

    public Familia() {
        super();
    }

    public int getWebId() {
        return webId;
    }

    public void setWebId(int webId) {
        this.webId = webId;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Ranchada getRanchada() {
        return ranchada;
    }

    public void setRanchada(Ranchada ranchada) {
        this.ranchada = ranchada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setZonaByWebId (int zonaWebId) {
        this.zona = ZonaDataAccess.get().findByWebId(zonaWebId);
    }

    public void setRanchadaByWebId (int ranchadaWebId) {
        this.ranchada = RanchadaDataAccess.get().findByWebId(ranchadaWebId);
    }

    public void mergeFromWeb(Familia familia) throws Exception {
        if (familia.webId != this.getWebId()) {
            throw new Exception("MergeConDiferenteWebId");
        }
        this.webId = familia.getWebId();
        this.estado = familia.getEstado();
        this.nombre = familia.getNombre();
        this.zona = familia.getZona();
        this.ranchada = familia.getRanchada();
        this.descripcion = familia.getDescripcion();
    }
}
