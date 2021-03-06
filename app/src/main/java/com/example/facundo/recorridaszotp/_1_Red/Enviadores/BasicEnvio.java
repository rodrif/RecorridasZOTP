package com.example.facundo.recorridaszotp._1_Red.Enviadores;

import android.util.Log;

import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.BasicJsonUtil;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.EnvioPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Facundo on 24/10/2015.
 */
public abstract class BasicEnvio<T> extends EnvioPost {
    protected JSONObject respuesta;
    private BasicJsonUtil<T> basicJsonUtil;
    protected List<T> ts;

    public BasicEnvio(String webUrl, BasicJsonUtil<T> basicJsonUtil, List<T> ts) {
        super(webUrl);
        this.basicJsonUtil = basicJsonUtil;
        this.ts = ts;
    }

    public JSONObject getRespuesta() {
        return this.respuesta;
    }

    @Override
    protected String getUltimaFechaMod() {
        return Utils.UltFechaSincr + basicJsonUtil.getClase().getSimpleName();
    }

    @Override
    protected JSONArray cargarJson() throws Exception {
        JSONArray datos = new JSONArray();
        try {
            for (T t : this.ts) {
                datos.put(new JSONObject(basicJsonUtil.toJSonAEnviar(t)));
            }
        } catch (JSONException ex) {
            Log.e(Utils.APPTAG, this.getClass().getSimpleName() + " JSONException: " + ex.getMessage());
            ex.printStackTrace();
        }

        return datos;
    }

}
