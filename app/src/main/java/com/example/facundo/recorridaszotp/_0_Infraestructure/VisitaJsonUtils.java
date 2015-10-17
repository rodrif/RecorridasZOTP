package com.example.facundo.recorridaszotp._0_Infraestructure;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Facundo on 03/10/2015.
 */
public class VisitaJsonUtils {

    public static List<Visita> personasFromJsonString(String jsonString) throws Exception {
        JSONArray jsonArray = new JSONArray(jsonString);
        List<Visita> visitas = new ArrayList<Visita>();

        for (int i = 0; i < jsonArray.length(); ++i) {
            Visita visita = fromJsonObject(jsonArray.getJSONObject(i));
            visitas.add(visita);
        }

        return visitas;
    }

    public static Visita fromJsonObject(JSONObject visitaJson) throws Exception {
        Visita visita = new Visita();
        visita.setWebId(visitaJson.optInt("web_id"));
        visita.setPersona(PersonaDataAccess.findById(visitaJson.getLong("web_persona_id")));
        visita.setEstado(visitaJson.optInt("estado"));
        visita.setFecha(visitaJson.getLong("fecha"));
        visita.setDescripcion(visitaJson.optString("descripcion"));

        return visita;
    }
}
