package com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils;

import com.example.facundo.recorridaszotp._2_DataAccess.ZonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Ranchada;

import org.json.JSONObject;

/**
 * Created by Gonzalo on 09/01/2016.
 */
public class RanchadaJsonUtils extends BasicJsonUtil<Ranchada> {

    private static RanchadaJsonUtils ourInstance = new RanchadaJsonUtils();

    public static RanchadaJsonUtils get() {
        return ourInstance;
    }

    private RanchadaJsonUtils() {
    }

    @Override
    public Class getClase() {
        return Ranchada.class;
    }

    @Override
    public Ranchada fromJsonObject(JSONObject ranchadaJson) throws Exception {
        Ranchada ranchada = new Ranchada();
        ranchada.setWebId(ranchadaJson.optInt("web_id"));
        ranchada.setEstado(ranchadaJson.optInt("estado"));
        ranchada.setNombre(ranchadaJson.optString("nombre"));
        ranchada.setZona(ZonaDataAccess.get().findByWebId(ranchadaJson.optInt("web_zone_id")));
        ranchada.setLatitud(ranchadaJson.optDouble("latitud"));
        ranchada.setLongitud(ranchadaJson.optDouble("longitud"));
        ranchada.setDescripcion(ranchadaJson.optString("descripcion"));
        return ranchada;
    }

    @Override
    public JSONObject toJsonObject(Ranchada ranchada) {
        return null;
    }
}
