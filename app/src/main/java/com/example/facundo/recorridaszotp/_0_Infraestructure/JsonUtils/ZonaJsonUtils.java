package com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils;

import com.example.facundo.recorridaszotp._3_Domain.Zona;

import org.json.JSONObject;

/**
 * Created by Gonzalo on 09/01/2016.
 */
public class ZonaJsonUtils extends BasicJsonUtil<Zona> {

    private static ZonaJsonUtils ourInstance = new ZonaJsonUtils();

    public static ZonaJsonUtils get() {
        return ourInstance;
    }

    private ZonaJsonUtils() {
    }

    @Override
    public Class getClase() {
        return Zona.class;
    }

    @Override
    public String toJSonAEnviar(Zona zona) {
        return null;
    }

    @Override
    public Zona fromJsonObject(JSONObject zonaJson) throws Exception {
        Zona zona = new Zona();
        zona.setWebId(zonaJson.optInt("web_id"));
        zona.setEstado(zonaJson.optInt("estado"));
        zona.setNombre(zonaJson.optString("nombre"));
        zona.setAreaByWebId(zonaJson.optInt("web_area_id"));
        zona.setLatitud(zonaJson.getDouble("latitud"));
        zona.setLongitud(zonaJson.getDouble("longitud"));

        return zona;
    }

    @Override
    public JSONObject toJsonObject(Zona zona) {
        return null;
    }
}
