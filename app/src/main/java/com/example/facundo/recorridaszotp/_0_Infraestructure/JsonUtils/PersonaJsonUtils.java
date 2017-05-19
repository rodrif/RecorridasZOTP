package com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils;

import com.activeandroid.util.Log;
import com.example.facundo.recorridaszotp._0_Infraestructure.ExcepcionNoActualizoDB;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
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
    public Persona fromJsonObject(JSONObject personaJson) throws Exception {
        Persona persona = new Persona();
        if (personaJson.optInt("web_id") != -1) {
            persona.setWebId(personaJson.optInt("web_id"));
        } else {
            throw new ExcepcionNoActualizoDB();
        }
        persona.setNombre(personaJson.getString("nombre"));
        if (personaJson.optString("apellido") != "null") {
            persona.setApellido(personaJson.optString("apellido"));
        }
        persona.setEstado(personaJson.optInt("state_id"));
        persona.setZonaByWebId(personaJson.optInt("web_zone_id"));
        if (personaJson.optString("fecha_nacimiento") != "null") {
            persona.setFechaNacimiento(personaJson.optString("fecha_nacimiento"));
        }
        if (personaJson.optString("descripcion") != "null") {
            persona.setObservaciones(personaJson.optString("descripcion"));
        }
        if (personaJson.optString("dni") != "null") {
            persona.setDNI(personaJson.optString("dni"));
        }
        if (personaJson.optString("telefono") != "null") {
            persona.setTelefono(personaJson.optString("telefono"));
        }

        if (personaJson.optString("pantalon") != "null") {
            persona.setPantalon(personaJson.optString("pantalon"));
        }
        if (personaJson.optString("remera") != "null") {
            persona.setRemera(personaJson.optString("remera"));
        }
        if (personaJson.optString("zapatillas") != "null") {
            persona.setZapatillas(personaJson.optString("zapatillas"));
        }

        persona.setFamiliaByWebId(personaJson.optInt("web_familia_id"));
        persona.setRanchadaByWebId(personaJson.optInt("web_ranchada_id"));

        return persona;
    }

    @Override
    public JSONObject toJsonObject(Persona persona) throws Exception {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("android_id", persona.getId());
            jsonObj.put("web_id", persona.getWebId());
            jsonObj.put("nombre", persona.getNombre());
            jsonObj.put("apellido", persona.getApellido());
            jsonObj.put("estado", persona.getEstado());
            if (persona.getZona() != null) { // por si esta borrado
                jsonObj.put("web_zone_id", persona.getZona().getWebId());
            }
            jsonObj.put("fecha_nacimiento", persona.getFechaNacimiento());
            jsonObj.put("descripcion", persona.getObservaciones());
            jsonObj.put("dni", persona.getDNI());
            jsonObj.put("telefono", persona.getTelefono());
            jsonObj.put("remera", persona.getRemera());
            jsonObj.put("pantalon", persona.getPantalon());
            jsonObj.put("zapatillas", persona.getZapatillas());
            if (persona.getFamilia() != null) {
                jsonObj.put("web_familia_id", persona.getFamilia().getWebId());
            }
            if (persona.getRanchada() != null) {
                jsonObj.put("web_ranchada_id", persona.getRanchada().getWebId());
            }

            return jsonObj;

        } catch (Exception ex) {
            Log.e(Utils.APPTAG, "Fallo al crear PersonaJSON");
            throw ex;
        }
    }
}
