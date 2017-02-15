package com.example.facundo.recorridaszotp._3_Domain;

/**
 * Created by gonzalo on 05/01/17.
 */
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Table(name = "Pedidos")
public class Pedido extends Model {
    @Column(name = "WebId")
    private int webId = -1; // -1 si es una persona no guardada en la BDWeb
    @Column(name = "Persona", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
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

    public Pedido() {
        super();
    }

    public Pedido(int webId, Persona persona, String descripcion, Boolean completado, long fecha) {
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.completado = completado;
        this.persona = persona;
        this.webId = webId;
    }

    public Pedido(Persona persona) {
        this(-1, persona, "", false, new Date().getTime());
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

    public String toString() {
        return "hola";
    } //FIXME

    public String getFechaString() {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(getFecha());

        Date date = cal.getTime();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        return formateador.format(date);
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
}
