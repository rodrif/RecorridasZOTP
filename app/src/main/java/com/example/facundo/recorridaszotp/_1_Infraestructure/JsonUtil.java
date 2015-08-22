package com.example.facundo.recorridaszotp._1_Infraestructure;

import com.example.facundo.recorridaszotp._3_Domain.Persona;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Facundo on 08/08/2015.
 */
public class JsonUtil {

    public static String toJSon(Persona persona) {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("android_id", persona.getId());
            jsonObj.put("nombre", persona.getNombre());

            return jsonObj.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
