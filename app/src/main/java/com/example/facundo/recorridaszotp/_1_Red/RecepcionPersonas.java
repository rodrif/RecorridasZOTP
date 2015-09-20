package com.example.facundo.recorridaszotp._1_Red;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp._0_Infraestructure.PersonaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Facundo on 05/09/2015.
 */
public class RecepcionPersonas extends EnvioPost {
    protected String respuesta;
    protected AsyncDelegate delegate;

    public RecepcionPersonas() {
    }

    public RecepcionPersonas(AsyncDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    protected JSONArray cargarJson() {
        JSONArray datos = new JSONArray();
////TODO guardar ultSincronizacion android db
//        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//        try {
//            JSONObject jsonObj = new JSONObject();
//            jsonObj.put("fecha", currentDateTimeString);
//            datos.put(jsonObj);
//        } catch (JSONException ex) {
//            Log.e(Utils.APPTAG, "JSONException");
//            ex.printStackTrace();
//        }

        return datos;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(Utils.APPTAG, "RecepcionPersonas onPostExecute: " + result);
        try {
            this.respuesta = result;
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, "respuestaJsonInvalida: " + ex.getMessage());
        }
        ActiveAndroid.beginTransaction();
        try {
            List<Persona> personas = PersonaJsonUtils.personasFromJsonString(result);

            if (PersonaDataAccess.acualizarDB(personas) != 0) {
                throw new Exception("FalloActualizarDB");
            } else {
                ActiveAndroid.setTransactionSuccessful();
                if (this.delegate != null) {
                    delegate.executionFinished(this.respuesta);
                }
            }
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, "ErrorRecibirPersonas: " + ex.getMessage());
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    public String getRespuesta() {
        return this.respuesta;
    }
}
