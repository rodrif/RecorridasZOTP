package com.example.facundo.recorridaszotp._1_Red;

import android.util.Log;
import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.PersonaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Configuracion;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Facundo on 05/09/2015.
 */
public class RecepcionPersonas extends EnvioPost {
    protected JSONObject respuesta;
    protected List<AsyncDelegate> delegate;

    public RecepcionPersonas() {
    }

    public RecepcionPersonas(List<AsyncDelegate> delegate) {
        this.delegate = delegate;
    }

    @Override
    protected JSONArray cargarJson() {
        JSONArray datos = new JSONArray();
        return datos;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(Utils.APPTAG, "RecepcionPersonas onPostExecute: " + result);
        try {
            this.respuesta = new JSONObject(result);
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, "RecepcionPersonas respuestaJsonInvalida: " + ex.getMessage());
        }
        ActiveAndroid.beginTransaction();

        try {
            List<Persona> personas = PersonaJsonUtils.get().fromJsonString(this.respuesta.getJSONArray("datos").toString());

            if (PersonaDataAccess.get().acualizarDB(personas) != 0) {
                throw new Exception("FalloActualizarDB");
            } else {
                Configuracion.guardar(Utils.UltFechaSincr, this.respuesta.getString("fecha").toString());
                ActiveAndroid.setTransactionSuccessful();

                AsyncDelegate unAsyncDelegate = null;
                for (Iterator<AsyncDelegate> it = this.delegate.iterator(); it.hasNext(); ) {
                    unAsyncDelegate = it.next();
                    unAsyncDelegate.executionFinished(this.respuesta.toString());
                }
            }
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, "ErrorRecibirPersonas: " + ex.getMessage());
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    public JSONObject getRespuesta() {
        return this.respuesta;
    }
}
