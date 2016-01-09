package com.example.facundo.recorridaszotp._3_Domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.AreaDataAccess;

/**
 * Created by Facundo on 28/03/2015.
 */

@Table(name = "Zonas")
public class Zona extends Model {
    @Column(name = "WebId")
    private int webId = -1;
    @Column(name = "Estado")
    private int estado;
    @Column(name = "Nombre")
    private String nombre = "";
    @Column(name = "Area")
    private Area area = null;
    @Column(name = "Latitud")
    private double latitud = Double.NaN;
    @Column(name = "Longitud")
    private double longitud = Double.NaN;

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

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getWebId() {
        return this.webId;
    }

    public void setWebId(int webId) {
        this.webId = webId;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public Area getArea() {
        return area;
    }

    public void setAreaByWebId(int areaWebId) {
        this.area = AreaDataAccess.get().findByWebId(areaWebId);
    }

    public void mergeFromWeb(Zona zona) throws Exception {
        if (zona.webId != this.getWebId()) {
            throw new Exception("MergeConDiferenteWebId");
        }
        this.webId = zona.getWebId();
        this.estado = zona.getEstado();
        this.nombre = zona.getNombre();
        this.area = zona.getArea();
        this.latitud = zona.getLatitud();
        this.longitud = zona.getLongitud();
    }

}
