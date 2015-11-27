package com.example.facundo.recorridaszotp._3_Domain;


import android.text.format.DateFormat;
import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created by Facundo on 03/10/2015.
 */
@Table(name = "Visitas")

public class Visita extends Model {
    @Column(name = "WebId")
    private int webId = -1;
    @Column(name = "Persona")
    public Persona persona;
    @Column(name = "Fecha")
    private long fecha;
    @Column(name = "Descripcion")
    private String descripcion = "";
    @Column(name = "Estado")
    private int estado;
    @Column(name = "Latitud")
    private double latitud = Double.NaN;
    @Column(name = "Longitud")
    private double longitud = Double.NaN;

    //private LatLng ubicacion;

    public Visita() {
        super();
    }

    public Visita(Persona persona, long fecha, String descripcion) {
        this(persona, fecha);
        this.descripcion = descripcion;
    }

    public Visita(Persona persona, long fecha) {
        this(persona);
        this.fecha = fecha;
    }

    public Visita(Persona persona) {
        super();
        setFechaActual();
        this.persona = persona;
        this.latitud = Double.NaN;
        this.longitud = Double.NaN;
    }

    public void mergeFromWeb(Visita visita) throws Exception {
        if (visita.webId != this.getWebId()) {
            throw new Exception("VisitaMergeConDiferenteWebId");
        }
        this.descripcion = visita.getDescripcion();
        this.fecha = visita.getFecha();
        this.estado = visita.getEstado();
        this.persona = visita.getPersona();
        this.latitud = visita.getLatitud();
        this.longitud = visita.getLongitud();
    }

    public int getWebId() {
        return webId;
    }

    public void setWebId(int webId) {
        this.webId = webId;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public void setFecha(String fecha) {
        Calendar cal = new GregorianCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.fecha = date.getTime();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
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

    public void setUbicacion(LatLng latLng) {
        setLatitud(latLng.latitude);
        setLongitud(latLng.longitude);
    }

    public LatLng getUbicacion() {
        if (!(Double.isNaN(latitud) || Double.isNaN(longitud)))
            return new LatLng(getLatitud(), getLongitud());
        else
            return null;
    }

    public String getFechaString() {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(getFecha());

        Date date = cal.getTime();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        return formateador.format(date);
    }

    private void setFechaActual() {
        fecha = new Date().getTime();
    }
}
