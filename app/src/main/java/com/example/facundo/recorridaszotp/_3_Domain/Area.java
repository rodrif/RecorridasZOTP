package com.example.facundo.recorridaszotp._3_Domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;

/**
 * Created by Gonzalo on 04/12/2015.
 */
public class Area extends Model {
    @Column(name = "WebId")
    private int webId = -1;
    @Column(name = "Estado")
    private int estado;
    @Column(name = "Nombre")
    private String nombre = "";

    public Area(String nombre) {
        super();
        this.nombre = nombre;
        this.estado = Utils.EST_ACTUALIZADO;
    }

    public Area() {
        super();
    }

    public int getWebId() {
        return webId;
    }

    public void mergeFromWeb(Area area) throws Exception {
        if (area.webId != this.getWebId()) {
            throw new Exception("MergeConDiferenteWebId");
        }
        this.nombre = area.getNombre();
        this.estado = area.getEstado();
    }

    public void setWebId(int webId) {
        this.webId = webId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
