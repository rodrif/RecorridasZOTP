package com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils;

import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Facundo on 03/10/2015.
 */
public class VisitaJsonUtils extends BasicJsonUtil<Visita> {

    private static VisitaJsonUtils ourInstance = new VisitaJsonUtils();

    public static VisitaJsonUtils get() {
        return ourInstance;
    }

    private VisitaJsonUtils() {
    }

    @Override
    public Class getClase() {
        return Visita.class;
    }

    @Override
    public String toJSonAEnviar(Visita visita) {
        return toJsonObject(visita).toString();
    }

    @Override
    public Visita fromJsonObject(JSONObject visitaJson) throws Exception {
        Visita visita = new Visita();
        visita.setWebId(visitaJson.optInt("web_id"));
        visita.setPersona(PersonaDataAccess.get().findByWebId(visitaJson.optInt("web_person_id")));
        visita.setEstado(visitaJson.optInt("estado"));
        visita.setFecha(visitaJson.getLong("fecha"));
        visita.setDescripcion(visitaJson.optString("descripcion"));
        visita.setLatitud(visitaJson.optDouble("latitud"));
        visita.setLongitud(visitaJson.optDouble("longitud"));

        return visita;
    }

    @Override
    public JSONObject toJsonObject(Visita visita) {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("android_id", visita.getId());
            jsonObj.put("descripcion", visita.getDescripcion());
            jsonObj.put("fecha", visita.getFecha());
            jsonObj.put("estado", visita.getEstado());
            jsonObj.put("web_id", visita.getWebId());
            jsonObj.put("web_person_id", visita.getPersona().getWebId());
            jsonObj.put("latitud", visita.getLatitud());
            jsonObj.put("longitud", visita.getLongitud());

            return jsonObj;

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
