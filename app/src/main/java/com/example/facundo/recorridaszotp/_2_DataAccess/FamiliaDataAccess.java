package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Familia;

import java.util.List;

/**
 * Created by Gonzalo on 05/12/2015.
 */
public class FamiliaDataAccess extends BasicDataAccess<Familia> {
    private static FamiliaDataAccess ourInstance = new FamiliaDataAccess();

    public static FamiliaDataAccess get() {
        return ourInstance;
    }

    public Class getClase() {
        return Familia.class;
    }

    @Override
    public int acualizarDB(List<Familia> grupoFamiliars) throws Exception {
        int resultado = -1;
        ActiveAndroid.beginTransaction();
        try {
            for (Familia familia : grupoFamiliars) {
                Familia v = new Select()
                        .from(Familia.class)
                        .where("WebId = ?", familia.getWebId())
                        .executeSingle();
                if (v != null && familia.getEstado() == Utils.EST_BORRADO) {
                    v.delete();
                } else if (v != null) {
                    v.mergeFromWeb(familia);
                    v.save();
                } else {
                    familia.save();
                }
            }
            ActiveAndroid.setTransactionSuccessful();
            resultado = 0;
        } finally {
            ActiveAndroid.endTransaction();
        }
        return resultado;
    }

    public Familia find(String nombre) {
        if (nombre != null)
            return new Select()
                    .from(Familia.class)
                    .where("Nombre = ?", nombre)
                    .executeSingle();

        return null;
    }
}
