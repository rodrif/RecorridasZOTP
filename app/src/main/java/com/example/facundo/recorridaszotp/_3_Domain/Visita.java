package com.example.facundo.recorridaszotp._3_Domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.android.gms.maps.model.LatLng;

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
    private Long fecha;

    @Column(name = "Comentario")
    private String comentario;
    //TODO
    private LatLng ubicacion;

    public Visita() {
        super();
    }

    public Visita(Persona persona, Long fecha, String comentario) {
        this(persona, fecha);
        this.comentario = comentario;
    }

    public Visita(Persona persona, Long fecha) {
        this(persona);
        this.fecha = fecha;
    }

    public Visita(Persona persona) {
        super();
        this.persona = persona;
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

    public Long getFecha() {
        return fecha;
    }

    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LatLng getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(LatLng ubicacion) {
        this.ubicacion = ubicacion;
    }

}
