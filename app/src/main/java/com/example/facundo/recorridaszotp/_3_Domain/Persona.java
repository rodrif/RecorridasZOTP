package com.example.facundo.recorridaszotp._3_Domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.PersonaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;

import java.util.List;


@Table(name = "Personas")
public class Persona extends Model implements Parcelable {
    @Column(name = "WebId")
    private int webId = -1; // -1 si es una persona no guardada en la BDWeb
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Apellido")
    private String apellido;
    @Column(name = "Estado")
    private int estado;
    //@Column(name = "Zona") //FIXME crear columna Zona primero en web
    private Zona zona;
    private String direccion;
    private String descripcion;
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

    protected Persona(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<Persona> CREATOR = new Creator<Persona>() {
        @Override
        public Persona createFromParcel(Parcel in) {
            return new Persona(in);
        }

        @Override
        public Persona[] newArray(int size) {
            return new Persona[size];
        }
    };

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    @Override
    public boolean equals(Object obj) {
        Persona other = (Persona) obj;
        return (PersonaJsonUtils.get().toJSonValue(other).equals(PersonaJsonUtils.get().toJSonValue(this)));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(webId);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeInt(estado);
        dest.writeParcelable(zona, 0);
        dest.writeString(direccion);
        dest.writeString(descripcion);
        dest.writeString(ultMod);
    }

    private void readFromParcel(Parcel in) {
        webId = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        estado = in.readInt();
        zona = in.readParcelable(Zona.class.getClassLoader());
        direccion = in.readString();
        descripcion = in.readString();
        ultMod = in.readString();
    }
}
