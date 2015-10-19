package com.example.facundo.recorridaszotp._1_Red;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.PersonaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Configuracion;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._5_Presentation.ListaPersonas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Facundo on 05/09/2015.
 */
public class RecepcionPersonas extends EnvioPost {
    protected JSONObject respuesta;
    protected List<AsyncDelegate> delegate;
    //  protected AsyncDelegate segundoDelegate;
    //   private Activity activity = null;

    public RecepcionPersonas() {
    }

    public RecepcionPersonas(List<AsyncDelegate> delegate) {
        this.delegate = delegate;
    }

 /*    //TODO refactor a list<delegate>
   public RecepcionPersonas(AsyncDelegate delegate, AsyncDelegate segundoDelegate) {
        this(delegate);
        this.segundoDelegate = segundoDelegate;
    }*/

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
            List<Persona> personas = PersonaJsonUtils.personasFromJsonString(this.respuesta.getJSONArray("datos").toString());

            if (PersonaDataAccess.acualizarDB(personas) != 0) {
                throw new Exception("FalloActualizarDB");
            } else {
                Configuracion.guardar(Utils.UltFechaSincr, this.respuesta.getString("fecha").toString());
                ActiveAndroid.setTransactionSuccessful();

                AsyncDelegate unAsyncDelegate = null;
                for (Iterator<AsyncDelegate> it = this.delegate.iterator(); it.hasNext(); ) {
                    unAsyncDelegate = it.next();
                    unAsyncDelegate.executionFinished(this.respuesta.toString());
                }
      /*          if (this.delegate != null) {
                    delegate.executionFinished(this.respuesta.toString());
                }
                if (this.segundoDelegate != null) {
                    segundoDelegate.executionFinished(this.respuesta.toString());
                }*/
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
