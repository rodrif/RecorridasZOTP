package com.example.facundo.recorridaszotp._0_Infraestructure;

import android.util.Log;

import com.example.facundo.recorridaszotp._3_Domain.Persona;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Facundo on 08/08/2015.
 */
public class PersonaJsonUtils {

    public static String toJSonAEnviar(Persona persona) {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("android_id", persona.getId());
            jsonObj.put("nombre", persona.getNombre());
            jsonObj.put("apellido", persona.getApellido());
            jsonObj.put("estado", persona.getEstado());
            jsonObj.put("web_id", persona.getWebId());

            return jsonObj.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static List<Persona> personasFromJsonString(String jsonString) throws Exception {
        JSONArray jsonArray = new JSONArray(jsonString);
        List<Persona> personas = new ArrayList<Persona>();

        for (int i = 0; i < jsonArray.length(); ++i) {
            Persona persona = fromJsonObject(jsonArray.getJSONObject(i));
            personas.add(persona);
        }

        return personas;
    }

    public static Persona fromJsonObject(JSONObject personaJson) throws Exception {
        Persona persona = new Persona();
        persona.setNombre(personaJson.optString("nombre"));
        persona.setApellido(personaJson.optString("apellido"));
        persona.setEstado(personaJson.optInt("estado"));
        persona.setWebId(personaJson.optInt("web_id"));
        return persona;
    }

    public static String personasToJsonString(List<Persona> personas) throws Exception {
        ArrayList<String> stringArray = new ArrayList<String>();
        JSONArray jsonArray = new JSONArray();

        for (Persona persona : personas) {
            jsonArray.put(toJsonObject(persona));
        }

        for (int i = 0, count = jsonArray.length(); i < count; i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                stringArray.add(jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonArray.toString();
    }

    public static JSONObject toJsonObject(Persona persona) {
        try {
            JSONObject jsonObj = new JSONObject();
            //jsonObj.put("id", this.getId());
            jsonObj.put("nombre", persona.getNombre());
            jsonObj.put("apellido", persona.getApellido());
            jsonObj.put("estado", persona.getEstado());
            jsonObj.put("web_id", persona.getWebId());

            return jsonObj;

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String toJSonValue(Persona persona) {
        JSONObject jsonObj = toJsonObject(persona);

        if (jsonObj != null)
            return jsonObj.toString();
        return null;
    }

}
