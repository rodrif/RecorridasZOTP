package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.GrupoFamiliar;

import java.util.List;

/**
 * Created by Gonzalo on 05/12/2015.
 */
public class GrupoFamiliarDataAccess extends BasicDataAccess<GrupoFamiliar> {
    private static GrupoFamiliarDataAccess ourInstance = new GrupoFamiliarDataAccess();

    public static GrupoFamiliarDataAccess get() {
        return ourInstance;
    }

    public Class getClase() {
        return GrupoFamiliar.class;
    }

    @Override
    public int acualizarDB(List<GrupoFamiliar> grupoFamiliars) throws Exception {
        int resultado = -1;
        ActiveAndroid.beginTransaction();
        try {
            for (GrupoFamiliar grupoFamiliar : grupoFamiliars) {
                GrupoFamiliar v = new Select()
                        .from(GrupoFamiliar.class)
                        .where("WebId = ?", grupoFamiliar.getWebId())
                        .executeSingle();
                if (v != null && grupoFamiliar.getEstado() == Utils.EST_BORRADO) {
                    v.delete();
                } else if (v != null) {
                    v.mergeFromWeb(grupoFamiliar);
                    v.save();
                } else {
                    grupoFamiliar.save();
                }
            }
            ActiveAndroid.setTransactionSuccessful();
            resultado = 0;
        } finally {
            ActiveAndroid.endTransaction();
        }
        return resultado;
    }

    public GrupoFamiliar find(String nombre) {
        if (nombre != null)
            return new Select()
                    .from(GrupoFamiliar.class)
                    .where("Nombre = ?", nombre.toCharArray())
                    .executeSingle();

        return null;
    }
}
