package com.example.facundo.recorridaszotp._1_Infraestructure;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp._3_Domain.Persona;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Facundo on 08/08/2015.
 */
public class EnvioPersonas extends EnvioPost {

    private List<Persona> personas;
    private JSONObject respuesta;

    public EnvioPersonas(List<Persona> personas) {
        this.personas = personas;
    }

    @Override
    public JSONArray cargarJson() {
        JSONArray datos = new JSONArray();
        try {
            for (Persona persona : this.personas) {
                datos.put(new JSONObject(JsonUtil.toJSon(persona)));
            }
        } catch (JSONException ex) {
            Log.e("recorridaszotp", "JSONException");
            ex.printStackTrace();
        }

        return datos;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("recorridaszotp", "onPostExecute");
        try {
            this.respuesta = new JSONObject(result);
        } catch (Exception ex) {
            Log.e("recorridaszotp", "respuestaJsonInvalida: " + ex.getMessage());
        }

        ActiveAndroid.beginTransaction();
        try {
            for (Persona persona : this.personas) {
                persona.setWebId(this.respuesta.optInt(persona.getId().toString()));
                persona.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    public JSONObject getRespuesta() {
        return this.respuesta;
    }
}
