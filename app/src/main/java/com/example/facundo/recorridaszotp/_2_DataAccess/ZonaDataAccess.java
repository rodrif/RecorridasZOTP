package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionZonas;
import com.example.facundo.recorridaszotp._3_Domain.Area;
import com.example.facundo.recorridaszotp._3_Domain.Zona;

import java.util.List;

/**
 * Created by GoRodriguez on 16/11/2015.
 */
public class ZonaDataAccess extends BasicDataAccess<Zona>{

    private static ZonaDataAccess ourInstance = new ZonaDataAccess();

    public static ZonaDataAccess get() {
        return ourInstance;
    }

    private ZonaDataAccess() {
    }

    public Class getClase() {
        return Zona.class;
    }

    @Override
    public int acualizarDB(List<Zona> zonas) throws Exception {
        int resultado = -1;
        ActiveAndroid.beginTransaction();
        try {
            for (Zona zona : zonas) {
                Zona v = new Select()
                        .from(Zona.class)
                        .where("WebId = ?", zona.getWebId())
                        .executeSingle();
                if (v != null && zona.getEstado() == Utils.EST_BORRADO) {
                    v.delete();
                } else if (v != null) {
                    v.mergeFromWeb(zona);
                    v.save();
                } else if (zona.getEstado() != Utils.EST_BORRADO) {
                    zona.save();
                }
            }
            ActiveAndroid.setTransactionSuccessful();
            resultado = 0;
        } finally {
            ActiveAndroid.endTransaction();
        }
        return resultado;
    }

    public Zona find(String nombre) {
        if (nombre != null)
            return new Select()
                    .from(Zona.class)
                    .where("Nombre = ?", nombre)
                    .executeSingle();

        return null;
    }

    public List<Zona> getAllOKFiltradoPorArea() {
        Area area = new Select()
                .from(Area.class)
                .where("WebId = ?", Config.getInstance().getArea())
                .executeSingle();

        return new Select()
                .from(Zona.class)
                .orderBy("Nombre ASC")
                .where("Area = ?", area.getId())
                .where("Estado != ?", Utils.EST_BORRADO)
                .execute();
    }

}
