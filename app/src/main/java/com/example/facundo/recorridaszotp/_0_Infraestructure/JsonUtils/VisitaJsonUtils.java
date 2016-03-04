package com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils;

import com.activeandroid.util.Log;
import com.example.facundo.recorridaszotp._0_Infraestructure.ExcepcionNoActualizoDB;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
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
    public String toJSonAEnviar(Visita visita) throws JSONException {
        String aEnviar = toJsonObject(visita).toString();
        return aEnviar;
    }

    @Override
    public Visita fromJsonObject(JSONObject visitaJson) throws Exception {
        Visita visita = new Visita();
        if (visitaJson.optInt("web_id") != -1) {
            visita.setWebId(visitaJson.optInt("web_id"));
        } else {
            throw new ExcepcionNoActualizoDB();
        }

        visita.setPersona(PersonaDataAccess.get().findByWebId(visitaJson.optInt("web_person_id")));
        visita.setEstado(visitaJson.optInt("estado"));
        visita.setFecha(visitaJson.getLong("fecha"));
        if (visitaJson.optString("descripcion") != "null") {
            visita.setDescripcion(visitaJson.optString("descripcion"));
        }

        visita.setLatitud(visitaJson.optDouble("latitud"));
        visita.setLongitud(visitaJson.optDouble("longitud"));

        return visita;
    }

    @Override
    public JSONObject toJsonObject(Visita visita) throws JSONException {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("android_id", visita.getId());
            jsonObj.put("descripcion", visita.getDescripcion());
            jsonObj.put("fecha", visita.getFecha());
            jsonObj.put("estado", visita.getEstado());
            jsonObj.put("web_id", visita.getWebId());
            Persona persona = visita.getPersona();
            if (persona != null)
                jsonObj.put("web_person_id", persona.getWebId());
            jsonObj.put("latitud", visita.getLatitud());
            jsonObj.put("longitud", visita.getLongitud());

            return jsonObj;

        } catch (JSONException ex) {
            Log.e(Utils.APPTAG, "VisitaJsonUtils::toJsonObject Error al crear PersonaJSON" + ex.toString());
            throw ex;
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, "VisitaJsonUtils::toJsonObject Error: " + ex.toString());
            throw ex;
        }
    }
}
