package com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils;

import com.example.facundo.recorridaszotp._3_Domain.Familia;

import org.json.JSONObject;

/**
 * Created by Gonzalo on 09/01/2016.
 */
public class FamiliaJsonUtils extends BasicJsonUtil<Familia> {

    private static FamiliaJsonUtils ourInstance = new FamiliaJsonUtils();

    public static FamiliaJsonUtils get() {
        return ourInstance;
    }

    private FamiliaJsonUtils() {
    }

    @Override
    public Class getClase() {
        return Familia.class;
    }

    @Override
    public Familia fromJsonObject(JSONObject familiaJson) throws Exception {
        Familia familia = new Familia();
        familia.setWebId(familiaJson.optInt("web_id"));
        familia.setEstado(familiaJson.optInt("estado"));
        familia.setNombre(familiaJson.optString("nombre"));
        familia.setZonaByWebId(familiaJson.optInt("web_zone_id"));
        familia.setRanchadaByWebId(familiaJson.optInt("web_ranchada_id"));
        familia.setDescripcion(familiaJson.optString("descripcion"));
        return familia;
    }

    @Override
    public JSONObject toJsonObject(Familia familia) {
        return null;
    }
}
