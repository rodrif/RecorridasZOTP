package com.example.facundo.recorridaszotp._1_Red.Enviadores;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp._0_Infraestructure.ExcepcionNoActualizoDB;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.VisitaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Configuracion;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Facundo on 17/10/2015.
 */
public class EnvioVisitas extends BasicEnvio<Visita> {

    public EnvioVisitas(List<Visita> visitas) {
        super(Utils.WEB_ENVIO_VISITAS, VisitaJsonUtils.get(), visitas);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d(Utils.APPTAG, "EnvioVisitas::onPostExecute result: " + result);
        try {
            this.respuesta = new JSONObject(result);
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, "Envio visitas respuestaJsonInvalida: " + ex.getMessage());
            return;
        }

        ActiveAndroid.beginTransaction();
        try {
            for (Visita visita : this.ts) {
                if (this.respuesta.getJSONObject("datos").optInt(visita.getId().toString()) > 0 ) {
                    visita.setWebId(this.respuesta.getJSONObject("datos").optInt(visita.getId().toString()));
                } else {
                    Log.e(Utils.APPTAG, "Error: webId negativo en EnvioVisita::onPostExecute");
                    continue;
                }
                if (visita.getEstado() != Utils.EST_BORRADO) {
                    visita.setEstado(Utils.EST_ACTUALIZADO);
                    visita.save();
                } else {
                    visita.delete();
                }
            }
            Configuracion.guardar(getUltimaFechaMod(), this.respuesta.getString("fecha").toString());
            ActiveAndroid.setTransactionSuccessful();
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, "falloEnviarVisitas: " + ex.getMessage());
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

}
