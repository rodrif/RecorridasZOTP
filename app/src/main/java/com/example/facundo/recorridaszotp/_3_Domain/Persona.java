package com.example.facundo.recorridaszotp._3_Domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;


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
    private String direccion;
    private String descripcion;
    private String ultMod;
    public Zona zona;

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
    }

    public Persona(String nombre) {
        super();
        this.nombre = nombre;
    }

    public void mergeFromWeb(Persona persona) throws Exception{
        if (persona.webId != this.getWebId()) {
            throw new Exception("MergeConDiferenteWebId");
        }
        this.nombre = persona.getNombre();
        this.apellido = persona.getApellido();
        this.estado = persona.getEstado();
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

    public void setEstado (int estado){ this.estado = estado; }

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

    public String getUltMod() {
        return ultMod;
    }

    public void setUltMod(String ultMod) {
        this.ultMod = ultMod;
    }

    @Override
    public boolean equals(Object obj) {
        Persona other = (Persona)obj;
        return (other.toJSonValue().equals(this.toJSonValue()));
    }

    public static Persona fromJsonObject(JSONObject personaJson) throws Exception {
        Persona persona = new Persona();
        persona.setNombre(personaJson.optString("nombre"));
        persona.setApellido(personaJson.optString("apellido"));
        persona.setEstado(personaJson.optInt("estado"));
        persona.setWebId(personaJson.optInt("webId"));
        return persona;
    }

    public JSONObject toJsonObject() {
        try {
            JSONObject jsonObj = new JSONObject();
            //jsonObj.put("id", this.getId());
            jsonObj.put("nombre", this.getNombre());
            jsonObj.put("apellido", this.getApellido());
            jsonObj.put("estado", this.getEstado());
            jsonObj.put("webId", this.getWebId());

            return jsonObj;

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public String toJSonValue() {
        JSONObject jsonObj = this.toJsonObject();

        if (jsonObj != null)
            return jsonObj.toString();
        return null;
    }

}
