package com.example.facundo.recorridaszotp._3_Domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.PersonaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.LocationListenerEx;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.GrupoFamiliarDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.RanchadaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.ZonaDataAccess;

import java.util.List;


@Table(name = "Personas")
public class Persona extends Model {
    @Column(name = "WebId")
    private int webId = -1; // -1 si es una persona no guardada en la BDWeb
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Apellido")
    private String apellido;
    @Column(name = "Estado")
    private int estado;
    @Column(name = "Zona")
    private Zona zona;
    @Column(name = "FechaNacimiento")
    private String fechaNacimiento;
    @Column(name = "Observaciones")
    private String observaciones;
    @Column(name = "DNI")
    private String DNI;
    @Column(name = "GrupoFamiliar")
    private GrupoFamiliar grupoFamiliar;
    @Column(name = "Ranchada")
    private Ranchada ranchada;


    private String ultMod;

    public Persona() {
        super();
    }

    public Persona(String nombre, String apellido, int estado, int webId) {
        this(nombre, apellido, estado);
        this.webId = webId;
    }

    public Persona(String nombre, String apellido, int estado) {
        this(nombre, apellido);
        this.estado = estado;
    }

    public Persona(String nombre, String apellido) {
        this(nombre);
        this.apellido = apellido;
        this.estado = Utils.EST_ACTUALIZADO;
    }

    public Persona(String nombre) {
        super();
        this.nombre = nombre;
        this.estado = Utils.EST_ACTUALIZADO;
    }

    public void mergeFromWeb(Persona persona) throws Exception {
        if (persona.webId != this.getWebId()) {
            throw new Exception("MergeConDiferenteWebId");
        }
        this.nombre = persona.getNombre();
        this.apellido = persona.getApellido();
        this.estado = persona.getEstado();
    }

    public List<Visita> visitas() {
        return getMany(Visita.class, "Persona");
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

    public String getUltMod() {
        return ultMod;
    }

    public void setUltMod(String ultMod) {
        this.ultMod = ultMod;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public GrupoFamiliar getGrupoFamiliar() {
        return grupoFamiliar;
    }

    public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
        this.grupoFamiliar = grupoFamiliar;
    }

    public Ranchada getRanchada() {
        return ranchada;
    }

    public void setRanchada(Ranchada ranchada) {
        this.ranchada = ranchada;
    }

    @Override
    public boolean equals(Object obj) {
        Persona other = (Persona) obj;
        return (PersonaJsonUtils.get().toJSonValue(other).equals(PersonaJsonUtils.get().toJSonValue(this)));
    }

    public void setGrupoFamiliar(String grupoFamiliar) {
        this.grupoFamiliar = GrupoFamiliarDataAccess.get().find(grupoFamiliar);
    }

    public void setZona(String unaZona) {
        this.zona = ZonaDataAccess.get().find(unaZona);
    }

    public void setRanchada(String ranchada) {
        this.ranchada = RanchadaDataAccess.get().find(ranchada);
    }
}
