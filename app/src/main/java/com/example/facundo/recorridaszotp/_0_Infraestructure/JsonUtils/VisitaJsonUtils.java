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
    public String toJSonAEnviar(Visita visita) { //TODO falta ubicacion
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("android_id", visita.getId());
            jsonObj.put("descripcion", visita.getDescripcion());
            jsonObj.put("fecha", visita.getFecha());
            jsonObj.put("estado", visita.getEstado());
            jsonObj.put("web_id", visita.getWebId());
            jsonObj.put("persona_web_id", visita.getPersona().getWebId());

            return jsonObj.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public Visita fromJsonObject(JSONObject visitaJson) throws Exception {
        Visita visita = new Visita();
        visita.setWebId(visitaJson.optInt("web_id"));
        visita.setPersona(PersonaDataAccess.get().findById(visitaJson.getLong("web_persona_id")));
        visita.setEstado(visitaJson.optInt("estado"));
        visita.setFecha(visitaJson.getLong("fecha"));
        visita.setDescripcion(visitaJson.optString("descripcion"));

        return visita;
    }

    @Override
    public JSONObject toJsonObject(Visita visita) {
        try {
            JSONObject jsonObj = new JSONObject();
            //jsonObj.put("id", this.getId());
            //jsonObj.put("ubicacion", visita.getUbicacion());
            jsonObj.put("descripcion", visita.getDescripcion());
            jsonObj.put("fecha", visita.getFecha());
            jsonObj.put("persona_id", visita.getPersona().getId());
            jsonObj.put("estado", visita.getEstado());
            jsonObj.put("web_id", visita.getWebId());

            return jsonObj;

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
