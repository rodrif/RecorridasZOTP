package com.example.facundo.recorridaszotp._1_Red;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp._0_Infraestructure.PersonaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
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
    private AsyncDelegate delegate;

    public EnvioPersonas(List<Persona> personas) {
        this.personas = personas;
    }

    public EnvioPersonas(List<Persona> personas, AsyncDelegate delegate) {
        this(personas);
        this.delegate = delegate;
    }

    @Override
    protected JSONArray cargarJson() {
        JSONArray datos = new JSONArray();
        try {
            for (Persona persona : this.personas) {
                datos.put(new JSONObject(PersonaJsonUtils.toJSonAEnviar(persona)));
            }
        } catch (JSONException ex) {
            Log.e(Utils.APPTAG, "JSONException");
            ex.printStackTrace();
        }

        return datos;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            this.respuesta = new JSONObject(result);
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, "respuestaJsonInvalida: " + ex.getMessage());
        }

        ActiveAndroid.beginTransaction();
        try {
            for (Persona persona : this.personas) {
                persona.setWebId(this.respuesta.getJSONObject("datos").optInt(persona.getId().toString()));
                persona.save();
            }
            ActiveAndroid.setTransactionSuccessful();
            if (this.delegate != null) {
                delegate.executionFinished(this.respuesta.toString());
            }
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, "falloEnviarPersonas: " + ex.getMessage());
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    public JSONObject getRespuesta() {
        return this.respuesta;
    }
}
