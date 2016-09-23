package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._1_Red.Delegates.DelegateEnviarPersonas;
import com.example.facundo.recorridaszotp._1_Red.Delegates.DelegateRecibirFamilia;
import com.example.facundo.recorridaszotp._1_Red.Delegates.DelegateRecibirRanchada;
import com.example.facundo.recorridaszotp._1_Red.Delegates.DelegateRecibirZonas;
import com.example.facundo.recorridaszotp._1_Red.Delegates.DelegateSincronizarPersonas;
import com.example.facundo.recorridaszotp._1_Red.Delegates.DelegateSincronizarVisitas;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionAreas;
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
                } else if (area.getEstado() != Utils.EST_BORRADO) {
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
        //AsyncDelegate delegateEnviarAreas = new DelegateEnviarAreas(delegate);
        //AsyncDelegate delegateRecibirZonas = new DelegateRecibirZonas(delegate);
        RecepcionAreas recepcionAreas = new RecepcionAreas(delegate);
        recepcionAreas.execute(Utils.WEB_RECIBIR_AREAS);
    }

    public void sincronizarTodo(AsyncDelegate delegate) {
        AsyncDelegate delegateSincronizarVisitas = new DelegateSincronizarVisitas(delegate);
        AsyncDelegate delegateSincronizarPersonas = new DelegateSincronizarPersonas(delegateSincronizarVisitas);
        AsyncDelegate delegateRecibirFamilia = new DelegateRecibirFamilia(delegateSincronizarPersonas);
        AsyncDelegate delegateRecibirRanchada = new DelegateRecibirRanchada(delegateRecibirFamilia);
        AsyncDelegate delegateRecibirZonas = new DelegateRecibirZonas(delegateRecibirRanchada);
        sincronizar(delegateRecibirZonas);
    }
}
