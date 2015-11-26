package com.example.facundo.recorridaszotp._3_Domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;

/**
 * Created by Facundo on 28/03/2015.
 */

@Table(name = "Zonas")
public class Zona extends Model{
    @Column(name = "WebId")
    private int webId = -1;
    @Column(name = "Nombre")
    private String nombre = "";
    @Column(name = "Estado")
    private int estado;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Zona(String nombre) {
        super();
        this.nombre = nombre;
        this.estado = Utils.EST_ACTUALIZADO;
    }

    public Zona() {
        super();
    }

    public int getEstado() {
        return this.estado;
    }

    public void setEstado (int estado){ this.estado = estado; }

    public int getWebId() {
        return this.webId;
    }

    public void setWebId(int webId) {
        this.webId = webId;
    }

    public void mergeFromWeb(Zona zona) throws Exception{
        if (zona.webId != this.getWebId()) {
            throw new Exception("MergeConDiferenteWebId");
        }
        this.nombre = zona.getNombre();
        this.estado = zona.getEstado();
    }

}
