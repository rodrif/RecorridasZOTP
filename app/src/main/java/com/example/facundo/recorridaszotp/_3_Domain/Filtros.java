package com.example.facundo.recorridaszotp._3_Domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * Created by gonzalo on 30/10/17.
 */

@Table(name = "Filtros")
public class Filtros extends Model {
    @Column(name = "ZonaId")
    private int zonaId;

    public Filtros() {
        super();
    }

    public int getZonaId() {
        return zonaId;
    }

    public void setZonaId(int zonaId) {
        this.zonaId = zonaId;
    }

    static public Filtros get() {
        return new Select()
            .from(Filtros.class)
            .where("id = ?", 1)
            .executeSingle();
    }

    public String toString() {
        return "tablaFiltros";
    }
}