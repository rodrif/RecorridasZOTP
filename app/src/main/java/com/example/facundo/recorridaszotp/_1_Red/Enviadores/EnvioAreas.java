package com.example.facundo.recorridaszotp._1_Red.Enviadores;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.AreaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.BasicJsonUtil;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._2_DataAccess.AreaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Area;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Gonzalo on 05/01/2016.
 */
public class EnvioAreas extends BasicEnvio<Area> {
    private AsyncDelegate delegate;

    public EnvioAreas(List<Area> areas) {
        super(AreaJsonUtils.get(), null);
        this.delegate = null;
    }

    public EnvioAreas(List<Area> areas, AsyncDelegate delegate) {
        super(AreaJsonUtils.get(), areas);
        this.delegate = delegate;
    }


    @Override
    protected void onPostExecute(String result) {
        try {
            this.respuesta = new JSONObject(result);
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, this.getClass().getSimpleName() +
                    " Envio Area respuestaJsonInvalida: " + ex.getMessage());
        }
        ActiveAndroid.beginTransaction();
        try {
            for (Area area: ts) {
                area.setWebId(this.respuesta.getJSONObject("datos").optInt(area.getId().toString()));
                if (area.getEstado() == Utils.EST_BORRADO) {
                    area.delete();
                } else {
                    area.save();
                }
            }
        }catch (Exception ex) {
            Log.e(Utils.APPTAG, this.getClass().getSimpleName()
                    + " falloEnviarAreas: " + ex.getMessage());
        } finally {
            ActiveAndroid.endTransaction();
        }
    }
}
