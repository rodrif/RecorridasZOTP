package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Query.PersonaQuery;

import java.util.List;

/**
 * Created by Facundo on 28/03/2015.
 */
public class PersonaDataAccess {

    public static void save(Persona persona) {
        persona.save();
    }

    public static int save(List<Persona> personas) {
        int resultado = -1;
        ActiveAndroid.beginTransaction();
        try {
            for (Persona persona : personas) {
                persona.save();
            }
            ActiveAndroid.setTransactionSuccessful();
            resultado = 0;
        } finally {
            ActiveAndroid.endTransaction();
        }
        return resultado;
    }

    public static Persona find(PersonaQuery query) {
        return new Select()
                .from(Persona.class)
                .where("Nombre = ?", query.nombre)
                .orderBy("id desc")
                .executeSingle();
    }

    public static List<Persona> getAll() {
        return new Select()
                .from(Persona.class)
                .orderBy("Nombre ASC")
                .execute();
    }

}
