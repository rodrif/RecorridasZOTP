package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Query.VisitaQuery;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import java.util.List;

/**
 * Created by Facundo on 03/10/2015.
 */
public class VisitaDataAccess extends BasicDataAccess<Visita> {

    private static VisitaDataAccess ourInstance = new VisitaDataAccess();

    public static VisitaDataAccess get() {
        return ourInstance;
    }

    private VisitaDataAccess() {
    }

    public Class getClase() {
        return Visita.class;
    }

    @Override
    public int acualizarDB(List<Visita> visitas) throws Exception {
        int resultado = -1;
        ActiveAndroid.beginTransaction();
        try {
            for (Visita visita : visitas) {
                Visita v = new Select()
                        .from(Visita.class)
                        .where("WebId = ?", visita.getWebId())
                        .executeSingle();
                if (v != null && visita.getEstado() == Utils.EST_BORRADO) {
                    v.delete();
                } else if (v != null) {
                    v.mergeFromWeb(visita);
                    v.save();
                } else {
                    visita.save();
                }
            }
            ActiveAndroid.setTransactionSuccessful();
            resultado = 0;
        } finally {
            ActiveAndroid.endTransaction();
        }
        return resultado;
    }

    public Visita find(VisitaQuery query) {
        if (query != null)
            if (query.observaciones != null)
            return new Select()
                    .from(Visita.class)
                    .where("Descripcion = ?", query.observaciones)
                    .executeSingle();

        return null;
    }

}
