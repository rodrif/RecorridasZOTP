package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._1_Red.Delegates.DelegateEnviarAreas;
import com.example.facundo.recorridaszotp._1_Red.Delegates.DelegateEnviarPersonas;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionAreas;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionPersonas;
import com.example.facundo.recorridaszotp._3_Domain.Area;

import java.util.List;

/**
 * Created by Gonzalo on 05/12/2015.
 */
public class AreaDataAccess extends BasicDataAccess<Area> {
    private static AreaDataAccess ourInstance = new AreaDataAccess();

    public static AreaDataAccess get() {
        return ourInstance;
    }

    private AreaDataAccess() {
    }

    public Class getClase() {
        return Area.class;
    }

    @Override
    public int acualizarDB(List<Area> areas) throws Exception {
        int resultado = -1;
        ActiveAndroid.beginTransaction();
        try {
            for (Area area : areas) {
                Area v = new Select()
                        .from(Area.class)
                        .where("WebId = ?", area.getWebId())
                        .executeSingle();
                if (v != null && area.getEstado() == Utils.EST_BORRADO) {
                    v.delete();
                } else if (v != null) {
                    v.mergeFromWeb(area);
                    v.save();
                } else {
                    area.save();
                }
            }
            ActiveAndroid.setTransactionSuccessful();
            resultado = 0;
        } finally {
            ActiveAndroid.endTransaction();
        }
        return resultado;
    }

    public void sincronizar(AsyncDelegate delegate) {
        AsyncDelegate delegateEnviarAreas = new DelegateEnviarAreas(delegate);
        RecepcionAreas recepcionAreas = new RecepcionAreas(delegateEnviarAreas);
        recepcionAreas.execute(Utils.WEB_RECIBIR_AREAS);
    }
}
