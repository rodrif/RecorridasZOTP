package com.example.facundo.recorridaszotp._1_Red.Receptores;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.BasicJsonUtil;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.EnvioPost;
import com.example.facundo.recorridaszotp._2_DataAccess.BasicDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Configuracion;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Facundo on 23/10/2015.
 */
public class BasicRecepcion<T extends Model> extends EnvioPost {
    protected JSONObject respuesta;
    private BasicJsonUtil<T> basicJsonUtil;
    private BasicDataAccess<T> basicDataAccess;
    private boolean versionError = false;

    public BasicRecepcion(BasicDataAccess<T> basicDataAccess, BasicJsonUtil<T> basicJsonUtil) {
        this.basicDataAccess = basicDataAccess;
        this.basicJsonUtil = basicJsonUtil;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(Utils.APPTAG, this.getClass().getSimpleName() + " onPostExecute: " + result);
        try {
            this.respuesta = new JSONObject(result);
            if (this.respuesta.has("errores")) {
                MainActivity.versionError = true;
                return;
            }
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, this.getClass().getSimpleName() + " respuestaJsonInvalida: " + ex.getMessage());
            return;
        }
        ActiveAndroid.beginTransaction();

        try {
            List<T> ts = basicJsonUtil.fromJsonString(this.respuesta.getJSONArray("datos").toString());

            if (basicDataAccess.acualizarDB(ts) != 0) {
                throw new Exception(this.getClass().getSimpleName() + " FalloActualizarDB");
            }

            Configuracion.guardar(getUltimaFechaMod(), this.respuesta.getString("fecha").toString());
            ActiveAndroid.setTransactionSuccessful();
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, this.getClass().getSimpleName() + " ErrorRecibir: " + ex.getMessage());
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    @Override
    protected String getUltimaFechaMod() {
        return Utils.UltFechaSincr + basicJsonUtil.getClase().getSimpleName();
    }

    @Override
    protected JSONArray cargarJson() {
        return new JSONArray();
    }

    public JSONObject getRespuesta() {
        return this.respuesta;
    }

}
