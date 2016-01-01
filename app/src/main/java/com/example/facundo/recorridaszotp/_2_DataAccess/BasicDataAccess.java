package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import java.util.List;

/**
 * Created by Facundo on 19/10/2015.
 */
public abstract class BasicDataAccess<T extends Model> {

    public abstract Class getClase();

    public abstract int acualizarDB(List<T> ts) throws Exception;

    public void save(T t) {
        t.save();
    }

    public int save(List<T> ts) {
        int resultado = -1;
        ActiveAndroid.beginTransaction();
        try {
            for (T t : ts) {
                t.save();
            }
            ActiveAndroid.setTransactionSuccessful();
            resultado = 0;
        } finally {
            ActiveAndroid.endTransaction();
        }
        return resultado;
    }

    public List<T> findASincronizar() {
        return new Select()
                .from(getClase())
                .where("Estado = ? OR Estado = ? OR Estado = ?", Utils.EST_NUEVO, Utils.EST_MODIFICADO, Utils.EST_BORRADO)
                .orderBy("Id desc")
                .execute();
    }

    public T findByWebId(int id) {
        return new Select()
                .from(getClase())
                .where("WebId = ?", id)
                .executeSingle();
    }

    public T findById(Long id) {
        return new Select()
                .from(getClase())
                .where("Id = ?", id)
                .executeSingle();
    }

    public List<T> getAll() {
        return new Select()
                .from(getClase())
                .orderBy("Id ASC")
                .execute();
    }

    public List<T> getAllOK() {
        return new Select()
                .from(getClase())
                .orderBy("Id ASC")
                .where("Estado != ?", Utils.EST_BORRADO)
                .execute();
    }
}
