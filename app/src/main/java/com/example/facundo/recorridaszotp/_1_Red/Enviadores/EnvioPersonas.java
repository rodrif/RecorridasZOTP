package com.example.facundo.recorridaszotp._1_Red.Enviadores;

import android.util.Log;
import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp._0_Infraestructure.ExcepcionNoActualizoDB;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.PersonaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._3_Domain.Configuracion;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import org.json.JSONObject;
import java.util.List;

/**
 * Created by Facundo on 08/08/2015.
 */
public class EnvioPersonas extends BasicEnvio<Persona> {
    private AsyncDelegate delegate;

    public EnvioPersonas(List<Persona> personas) {
        this(personas, null);
    }

    public EnvioPersonas(List<Persona> personas, AsyncDelegate delegate) {
        super(PersonaJsonUtils.get(), personas);
        this.delegate = delegate;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(Utils.APPTAG, "EnvioPersonas::onPostExecute result: " + result);
        try {
            this.respuesta = new JSONObject(result);
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, this.getClass().getSimpleName() + " Envio personas respuestaJsonInvalida: " + ex.getMessage());
            // TODO: relanzar ex
        }

        ActiveAndroid.beginTransaction();
        try {
            for (Persona persona : this.ts) {
                persona.setWebId(this.respuesta.getJSONObject("datos").optInt(persona.getId().toString()));
                if (persona.getEstado() != Utils.EST_BORRADO) {
                    persona.setEstado(Utils.EST_ACTUALIZADO);
                    persona.save();
                }
            }
            Configuracion.guardar(getUltimaFechaMod(), this.respuesta.getString("fecha").toString());

            ActiveAndroid.setTransactionSuccessful();
            if (this.delegate != null) {
                delegate.ejecutar(this.respuesta.toString());
            }
        } catch (ExcepcionNoActualizoDB excepcionNoActualizoDB) {
            Log.e(Utils.APPTAG, this.getClass().getSimpleName() + " fallo No Actualizo DB Personas: "
                    + excepcionNoActualizoDB.getMessage());
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, this.getClass().getSimpleName() + " falloEnviarPersonas: " + ex.getMessage());
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

}
