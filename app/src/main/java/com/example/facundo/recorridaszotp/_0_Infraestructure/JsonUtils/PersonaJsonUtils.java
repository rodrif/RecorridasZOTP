package com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils;

import com.example.facundo.recorridaszotp._3_Domain.Persona;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Facundo on 08/08/2015.
 */
public class PersonaJsonUtils extends BasicJsonUtil<Persona> {

    private static PersonaJsonUtils ourInstance = new PersonaJsonUtils();

    public static PersonaJsonUtils get() {
        return ourInstance;
    }

    private PersonaJsonUtils() {
    }

    @Override
    public Class getClase() {
        return PersonaJsonUtils.class;
    }

    @Override
    public String toJSonAEnviar(Persona persona) {
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

    @Override
    public Persona fromJsonObject(JSONObject personaJson) throws Exception {
        Persona persona = new Persona();
        persona.setNombre(personaJson.optString("nombre"));
        persona.setApellido(personaJson.optString("apellido"));
        persona.setEstado(personaJson.optInt("estado"));
        persona.setWebId(personaJson.optInt("web_id"));
        return persona;
    }

    @Override
    public JSONObject toJsonObject(Persona persona) {
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

}
