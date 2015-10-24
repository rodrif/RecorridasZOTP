package com.example.facundo.recorridaszotp._3_Domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Facundo on 26/09/2015.
 */
@Table(name = "Configuracion")
public class Configuracion extends Model {
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Valor")
    private String valor;

    public Configuracion() {
        super();
    }

    public Configuracion(String nombre, String valor) {
        super();
        this.nombre = nombre;
        this.valor = valor;
    }

    public static String get(String nombre) {
        Configuracion configuracion = getConfByNombre(nombre);
        return configuracion != null ? configuracion.valor : null;
    }

    public static void guardar(String nombre, String valor) {
        Configuracion configuracion = getConfByNombre(nombre);

        if (configuracion != null) {
            configuracion.valor = valor;
        } else {
            configuracion = new Configuracion(nombre, valor);
        }
        configuracion.save();
    }

    public static Configuracion getConfByNombre(String nombre) {
        Configuracion configuracion = new Select()
                .from(Configuracion.class)
                .where("Nombre = ?", nombre)
                .executeSingle();

        return configuracion;
    }

    public static List<Configuracion> getAll() {
        return new Select()
                .from(Configuracion.class)
                .orderBy("Id ASC")
                .execute();
    }

}
