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

public class Visita extends Model implements Parcelable {
    @Column(name = "WebId")
    private int webId = -1;
    @Column(name = "Persona")
    public Persona persona;
    @Column(name = "Fecha")
    private long fecha;
    @Column(name = "Descripcion")
    private String descripcion;
    @Column(name = "Estado")
    private int estado;
    //TODO
    private LatLng ubicacion = null;

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
    }

    protected Visita(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<Visita> CREATOR = new Creator<Visita>() {
        @Override
        public Visita createFromParcel(Parcel in) {
            return new Visita(in);
        }

        @Override
        public Visita[] newArray(int size) {
            return new Visita[size];
        }
    };

    public void mergeFromWeb(Visita visita) throws Exception {
        if (visita.webId != this.getWebId()) {
            throw new Exception("VisitaMergeConDiferenteWebId");
        }
        this.descripcion = visita.getDescripcion();
        this.fecha = visita.getFecha();
        this.estado = visita.getEstado();
        this.persona = visita.getPersona();
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

    public LatLng getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(LatLng ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //Cuidado con el orden
        dest.writeInt(webId);
        dest.writeParcelable(persona, 0);
        dest.writeLong(fecha);
        dest.writeString(descripcion);
        dest.writeInt(estado);
        dest.writeDouble(ubicacion.latitude);
        dest.writeDouble(ubicacion.longitude);
    }

    private void readFromParcel(Parcel in) {
        //Cuidado con el orden
        webId = in.readInt();
        persona = in.readParcelable(Persona.class.getClassLoader());
        fecha = in.readLong();
        descripcion = in.readString();
        estado = in.readInt();
        ubicacion = new LatLng(in.readDouble(), in.readDouble());
    }
}
