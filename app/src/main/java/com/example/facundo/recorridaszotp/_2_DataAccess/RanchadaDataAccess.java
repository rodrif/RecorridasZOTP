package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionRanchadas;
import com.example.facundo.recorridaszotp._3_Domain.Familia;
import com.example.facundo.recorridaszotp._3_Domain.Ranchada;
import com.example.facundo.recorridaszotp._3_Domain.Zona;

import java.util.List;


/**
 * Created by GoRodriguez on 16/11/2015.
 */
public class RanchadaDataAccess extends BasicDataAccess<Ranchada> {

    private static RanchadaDataAccess ourInstance = new RanchadaDataAccess();

    public static RanchadaDataAccess get() {
        return ourInstance;
    }

    private RanchadaDataAccess() {
    }

    public Class getClase() {
        return Ranchada.class;
    }

    @Override
    public int acualizarDB(List<Ranchada> ranchadas) throws Exception {
        int resultado = -1;
        ActiveAndroid.beginTransaction();
        try {
            for (Ranchada ranchada : ranchadas) {
                Ranchada v = new Select()
                        .from(Ranchada.class)
                        .where("WebId = ?", ranchada.getWebId())
                        .executeSingle();
                if (v != null && ranchada.getEstado() == Utils.EST_BORRADO) {
                    v.delete();
                } else if (v != null) {
                    v.mergeFromWeb(ranchada);
                    v.save();
                } else {
                    ranchada.save();
                }
            }
            ActiveAndroid.setTransactionSuccessful();
            resultado = 0;
        } finally {
            ActiveAndroid.endTransaction();
        }
        return resultado;
    }

    public Ranchada find(String nombre) {
        if (nombre != null)
            return new Select()
                    .from(Ranchada.class)
                    .where("Nombre = ?", nombre)
                    .executeSingle();

        return null;
    }

    public List<Ranchada> filtrarPorZona(String sZona) {
        List<Ranchada> listaRanchadas = null;
        if(sZona != null) {
            Zona zona = new Select ()
                    .from(Zona.class)
                    .where("Nombre = ?", sZona)
                    .executeSingle();

            if (zona != null) {
                listaRanchadas = new Select()
                        .from(Ranchada.class)
                        .where("Zona = ?", zona.getId())
                        .execute();
            }
        }

        return listaRanchadas;
    }

    public void sincronizar (AsyncDelegate delegate){
        RecepcionRanchadas recepcionRanchadas = new RecepcionRanchadas(delegate);
        recepcionRanchadas.execute(Utils.WEB_RECIBIR_RANCHADAS);
    }
}
