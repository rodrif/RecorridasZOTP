package com.example.facundo.recorridaszotp._1_Red;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp._0_Infraestructure.VisitaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Facundo on 17/10/2015.
 */
public class EnvioVisitas extends EnvioPost { // TODO falta unit test, ver si se puede hacer BasicEnvioPost
    private List<Visita> visitas;
    private JSONObject respuesta;
    private AsyncDelegate delegate;

    public EnvioVisitas(List<Visita> visitas) {
        this.visitas = visitas;
    }

    public EnvioVisitas(List<Visita> visitas, AsyncDelegate delegate) {
        this(visitas);
        this.delegate = delegate;
    }

    @Override
    protected JSONArray cargarJson() {
        JSONArray datos = new JSONArray();
        try {
            for (Visita visita : this.visitas) {
                datos.put(new JSONObject(VisitaJsonUtils.toJSonAEnviar(visita)));
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
            Log.e(Utils.APPTAG, "Envio visitas respuestaJsonInvalida: " + ex.getMessage());
        }

        ActiveAndroid.beginTransaction();
        try {
            for (Visita visita : this.visitas) {
                visita.setWebId(this.respuesta.getJSONObject("datos").optInt(visita.getId().toString()));
                visita.save();
            }
            ActiveAndroid.setTransactionSuccessful();
            if (this.delegate != null) {
                delegate.executionFinished(this.respuesta.toString());
            }
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, "falloEnviarVisitas: " + ex.getMessage());
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    public JSONObject getRespuesta() {
        return this.respuesta;
    }
}
