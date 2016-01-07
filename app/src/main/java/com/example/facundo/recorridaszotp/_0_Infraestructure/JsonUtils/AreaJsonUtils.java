package com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils;

import com.example.facundo.recorridaszotp._3_Domain.Area;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gonzalo on 05/01/2016.
 */
public class AreaJsonUtils extends BasicJsonUtil<Area> {

    private static AreaJsonUtils ourInstance = new AreaJsonUtils();

    public static AreaJsonUtils get() {
        return ourInstance;
    }

    private AreaJsonUtils() {
    }

    @Override
    public Class getClase() {
        return Area.class;
    }

    @Override
    public String toJSonAEnviar(Area area) {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("android_id", area.getId());
            jsonObj.put("nombre", area.getNombre());
            jsonObj.put("estado", area.getEstado());
            jsonObj.put("web_id", area.getWebId());

            return jsonObj.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Area fromJsonObject(JSONObject areaJson) throws Exception {
        Area area = new Area();
        area.setNombre(areaJson.optString("nombre"));
        area.setEstado(areaJson.optInt("estado"));
        area.setWebId(areaJson.optInt("web_id"));
        return area;
    }

    @Override
    public JSONObject toJsonObject(Area area) {
        try {
            JSONObject jsonObj = new JSONObject();
            //jsonObj.put("id", this.getId());
            jsonObj.put("nombre", area.getNombre());
            jsonObj.put("estado", area.getEstado());
            jsonObj.put("web_id", area.getWebId());

            return jsonObj;

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
