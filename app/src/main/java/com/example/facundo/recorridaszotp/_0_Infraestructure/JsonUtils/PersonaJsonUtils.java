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
        return Persona.class;
    }

    @Override
    public String toJSonAEnviar(Persona persona) {
        String aEnviar = toJsonObject(persona).toString();
        return aEnviar;
    }

    @Override
    public Persona fromJsonObject(JSONObject personaJson) throws Exception {
        Persona persona = new Persona();
        persona.setWebId(personaJson.optInt("web_id"));
        persona.setNombre(personaJson.optString("nombre"));
        persona.setApellido(personaJson.optString("apellido"));
        persona.setEstado(personaJson.optInt("estado"));
        persona.setZonaByWebId(personaJson.optInt("web_zone_id"));
        persona.setFechaNacimiento(personaJson.optString("fecha_nacimiento"));
        persona.setObservaciones(personaJson.optString("descripcion"));
        persona.setDNI(personaJson.optString("dni"));
        persona.setTelefono(personaJson.optString("telefono"));
        persona.setFamiliaByWebId(personaJson.optInt("web_familia_id"));
        persona.setRanchadaByWebId(personaJson.optInt("web_ranchada_id"));

        return persona;
    }

    @Override
    public JSONObject toJsonObject(Persona persona) {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("android_id", persona.getId());
            jsonObj.put("web_id", persona.getWebId());
            jsonObj.put("nombre", persona.getNombre());
            jsonObj.put("apellido", persona.getApellido());
            jsonObj.put("estado", persona.getEstado());
            jsonObj.put("web_zone_id", persona.getZona().getWebId());
            jsonObj.put("fecha_nacimiento", persona.getFechaNacimiento());
            jsonObj.put("descripcion", persona.getObservaciones());
            jsonObj.put("dni", persona.getDNI());
            jsonObj.put("telefono", persona.getTelefono());
            jsonObj.put("web_familia_id", persona.getFamilia().getWebId());
            jsonObj.put("web_ranchada_id", persona.getRanchada().getWebId());

            return jsonObj;

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
