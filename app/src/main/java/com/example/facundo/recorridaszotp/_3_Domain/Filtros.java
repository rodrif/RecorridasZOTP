package com.example.facundo.recorridaszotp._3_Domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._2_DataAccess.ZonaDataAccess;

/**
 * Created by gonzalo on 30/10/17.
 */

@Table(name = "Filtros")
public class Filtros extends Model {
    @Column(name = "ZonaId")
    private long zonaId = -1;

    public Filtros() {
        super();
    }

    public long getZonaId() {
        return zonaId;
    }

    public void setZonaId(long zonaId) {
        this.zonaId = zonaId;
    }

    static public String getZonaString() {
        Filtros filtros = Filtros.get();
        if (filtros != null && filtros.getZonaId() != -1) {
            return ZonaDataAccess.get().findById(filtros.getId()).getNombre();
        }
        return null;
    }

    static public Filtros get() {
        Filtros resultado = new Select()
            .from(Filtros.class)
            .where("id = ?", 1)
            .executeSingle();

        if (resultado == null) {
            resultado = new Filtros();
        }
        return resultado;
    }

    public String toString() {
        return "tablaFiltros";
    }
}