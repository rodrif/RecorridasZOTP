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
public class JsonUtils { //TODO renombrar a JsonUtilsPersona

    public static String toJSonAEnviar(Persona persona) {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("android_id", persona.getId());
            jsonObj.put("nombre", persona.getNombre());
            jsonObj.put("apellido", persona.getApellido());
            jsonObj.put("estado", persona.getEstado());
            jsonObj.put("webId", persona.getWebId());

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
            Persona persona = Persona.fromJsonObject(jsonArray.getJSONObject(i));
            personas.add(persona);
        }

        return personas;
    }

    public static String personasToJsonString(List<Persona> personas) throws Exception {
        ArrayList<String> stringArray = new ArrayList<String>();
        JSONArray jsonArray = new JSONArray();

        for (Persona persona : personas) {
            jsonArray.put(persona.toJsonObject());
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

}
