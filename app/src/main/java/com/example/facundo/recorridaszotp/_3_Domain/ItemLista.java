package com.example.facundo.recorridaszotp._3_Domain;

/**
 * Created by Gonzalo on 05/09/2015.
 */
public class ItemLista {
    private String nombre;
    private int icono;

    public ItemLista(String nombre) {
        this.nombre = nombre;
    }

    public ItemLista(String nombre, int icono) {
        this.nombre = nombre;
        this.icono = icono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String item) {
        this.nombre = nombre;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }
}
