package com.example.facundo.recorridaszotp._1_Red;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by Facundo on 05/09/2015.
 */
public class RecepcionPersonas extends EnvioPost {
    private String respuesta;
    private AsyncDelegate delegate;

    public RecepcionPersonas() {
    }

    public RecepcionPersonas(AsyncDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    protected JSONArray cargarJson() {
        JSONArray datos = new JSONArray();
        return datos;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            this.respuesta = result;
        } catch (Exception ex) {
            Log.e("recorridaszotp", "respuestaJsonInvalida: " + ex.getMessage());
        }

        ActiveAndroid.beginTransaction();
        try {
            List<Persona> personas = JsonUtils.personasFromJsonString(result);

            if (PersonaDataAccess.acualizarDB(personas) != 0) {
                throw new Exception("FalloActualizarDB");
            } else {
                ActiveAndroid.setTransactionSuccessful();
                if (this.delegate != null) {
                    delegate.executionFinished(this.respuesta.toString());
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
