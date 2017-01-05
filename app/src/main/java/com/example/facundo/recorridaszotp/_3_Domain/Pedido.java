package com.example.facundo.recorridaszotp._3_Domain;

/**
 * Created by gonzalo on 05/01/17.
 */
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;

@Table(name = "Pedidos")
public class Pedido extends Model {
    @Column(name = "WebId")
    private int webId = -1; // -1 si es una persona no guardada en la BDWeb
    @Column(name = "Persona")
    private Persona persona;
    @Column(name = "Descripcion")
    private String descripcion;
    @Column(name = "Completado")
    private Boolean completado;
    @Column(name = "Estado")
    private int estado;
    @Column(name = "Fecha")
    private long fecha;

    private String ultMod;

    public Pedido(int webId, Persona persona, String descripcion, Boolean completado, long fecha) {
        this.descripcion = descripcion;
        this.ultMod = ultMod;
        this.fecha = fecha;
        this.completado = completado;
        this.persona = persona;
        this.webId = webId;
    }

    public void mergeFromWeb(Pedido pedido) throws Exception {
        if (pedido.webId != this.getWebId()) {
            throw new Exception("MergeConDiferenteWebId");
        }
        this.persona = pedido.getPersona();
        this.descripcion = pedido.getDescripcion();
        this.completado = pedido.getCompletado();
        this.estado = Utils.EST_ACTUALIZADO;
        this.fecha = pedido.getFecha();
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getCompletado() {
        return completado;
    }

    public void setCompletado(Boolean completado) {
        this.completado = completado;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public String getUltMod() {
        return ultMod;
    }

    public void setUltMod(String ultMod) {
        this.ultMod = ultMod;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
