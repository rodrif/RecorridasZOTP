package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import java.util.List;

/**
 * Created by Facundo on 03/10/2015.
 */
public class VisitaDataAccess {
    public static void save(Visita visita) {
        visita.save();
    }

    public static int save(List<Visita> visitas) {
        int resultado = -1;
        ActiveAndroid.beginTransaction();
        try {
            for (Visita visita : visitas) {
                visita.save();
            }
            ActiveAndroid.setTransactionSuccessful();
            resultado = 0;
        } finally {
            ActiveAndroid.endTransaction();
        }
        return resultado;
    }

    public static Visita findByWebId(int id) {
        return new Select()
                .from(Visita.class)
                .where("WebId = ?", id)
                .executeSingle();
    }

    public static Visita findById(Long id) {
        return new Select()
                .from(Visita.class)
                .where("Id = ?", id)
                .executeSingle();
    }

    public static List<Visita> getAll() {
        return new Select()
                .from(Visita.class)
                .orderBy("Id ASC")
                .execute();
    }
}
