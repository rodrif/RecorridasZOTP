package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._1_Red.AsyncDelegate;
import com.example.facundo.recorridaszotp._1_Red.RecepcionPersonas;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.DelegateEnviarPersonas;
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

    public static Persona findByWebId(int id) {
        return new Select()
                .from(Persona.class)
                .where("WebId = ?", id)
                .executeSingle();
    }

    public static Persona find(PersonaQuery query) {
        return new Select()
                .from(Persona.class)
                .where("Nombre = ?", query.nombre)
                .executeSingle();
    }

    public static List<Persona> getAll() {
        return new Select()
                .from(Persona.class)
                .orderBy("Nombre ASC")
                .execute();
    }

    public static List<Persona> findPersonasASincronizar() {
        return new Select()
                .from(Persona.class)
                .where("Estado = ? OR Estado = ? OR Estado = ?", Utils.EST_NUEVO, Utils.EST_MODIFICADO, Utils.EST_BORRADO)
                .orderBy("Id desc")
                .execute();
    }

    public static int acualizarDB(List<Persona> personas) throws Exception {
        int resultado = -1;
        ActiveAndroid.beginTransaction();
        try {
            for (Persona persona : personas) {
                Persona p = new Select()
                        .from(Persona.class)
                        .where("WebId = ?", persona.getWebId())
                        .executeSingle();
                if (p != null && persona.getEstado() == Utils.EST_BORRADO) {
                    p.delete();
                } else if (p != null) {
                    p.mergeFromWeb(persona);
                    p.save();
                } else {
                    persona.save();
                }
            }
            ActiveAndroid.setTransactionSuccessful();
            resultado = 0;
        } finally {
            ActiveAndroid.endTransaction();
        }
        return resultado;
    }

    public static void sincronizar(AsyncDelegate delegate) {
        AsyncDelegate delegateEnviarPersonas = new DelegateEnviarPersonas(delegate);
        RecepcionPersonas recepcionPersonas = new RecepcionPersonas(delegateEnviarPersonas);
        recepcionPersonas.execute(Utils.WEB_RECIBIR_PERSONAS);
    }

}
