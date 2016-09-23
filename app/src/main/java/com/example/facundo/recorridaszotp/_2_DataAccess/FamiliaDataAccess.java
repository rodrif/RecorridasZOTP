package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionFamilias;
import com.example.facundo.recorridaszotp._3_Domain.Familia;
import com.example.facundo.recorridaszotp._3_Domain.Zona;

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
                } else if (familia.getEstado() != Utils.EST_BORRADO) {
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

    public List<Familia> filtrarPorZona(String sZona) {
        List<Familia> listaFamilias = null;
        if(sZona != null) {
            Zona zona = new Select ()
                    .from(Zona.class)
                    .where("Nombre = ?", sZona)
                    .executeSingle();

            if (zona != null) {
                listaFamilias = new Select()
                        .from(Familia.class)
                        .where("Zona = ?", zona.getId())
                        .execute();
            }
        }

        return listaFamilias;
    }

    public Familia find(String nombre) {
        if (nombre != null)
            return new Select()
                    .from(Familia.class)
                    .where("Nombre = ?", nombre)
                    .executeSingle();

        return null;
    }

    public void sincronizar (AsyncDelegate delegate){
        RecepcionFamilias recepcionFamilias = new RecepcionFamilias(delegate);
        recepcionFamilias.execute(Utils.WEB_RECIBIR_FAMILIAS);
    }
}
