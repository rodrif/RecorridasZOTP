package com.example.facundo.recorridaszotp._3_Domain;

import android.hardware.Camera;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;

/**
 * Created by Gonzalo on 04/12/2015.
 */
public class GrupoFamiliar extends Model {
    @Column(name = "WebId")
    private int webId = -1;
    @Column(name = "Estado")
    private int estado;
    @Column(name = "Nombre")
    private String nombre = "";
    @Column(name = "Area")
    private Area area = null;
    @Column(name = "Zona")
    private Zona zona = null;
    @Column(name = "Ranchada")
    private Ranchada ranchada = null;
    @Column(name = "Descripcion")
    private String descripcion = "";

    public GrupoFamiliar(String nombre) {
        super();
        this.nombre = nombre;
        this.estado = Utils.EST_ACTUALIZADO;
    }

    public GrupoFamiliar() {
        super();
    }

    public void mergeFromWeb(GrupoFamiliar grupoFamiliar) throws Exception{ //FIXME revisar mergeFromWeb
        if (grupoFamiliar.webId != this.getWebId()) {
            throw new Exception("MergeConDiferenteWebId");
        }
        this.nombre = grupoFamiliar.getNombre();
        this.estado = grupoFamiliar.getEstado();
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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
}
