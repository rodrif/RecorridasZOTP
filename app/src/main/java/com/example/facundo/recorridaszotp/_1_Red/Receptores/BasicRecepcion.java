package com.example.facundo.recorridaszotp._1_Red.Receptores;

import android.util.Log;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.BasicJsonUtil;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._1_Red.EnvioPost;
import com.example.facundo.recorridaszotp._2_DataAccess.BasicDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Configuracion;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Facundo on 23/10/2015.
 */
public abstract class BasicRecepcion<T extends Model> extends EnvioPost {
    protected JSONObject respuesta;
    protected List<AsyncDelegate> delegates;
    private BasicJsonUtil<T> basicJsonUtil;
    private BasicDataAccess<T> basicDataAccess;

    public BasicRecepcion(BasicDataAccess<T> basicDataAccess, BasicJsonUtil<T> basicJsonUtil, AsyncDelegate delegate) {
        this(basicDataAccess, basicJsonUtil, delegate != null ? Arrays.asList(delegate) : null);
    }

    public BasicRecepcion(BasicDataAccess<T> basicDataAccess, BasicJsonUtil<T> basicJsonUtil, List<AsyncDelegate> delegates) {
        this.basicDataAccess = basicDataAccess;
        this.basicJsonUtil = basicJsonUtil;
        this.delegates = delegates;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(Utils.APPTAG, this.getClass().getSimpleName() + " onPostExecute: " + result);
        try {
            this.respuesta = new JSONObject(result);
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, this.getClass().getSimpleName() + " respuestaJsonInvalida: " + ex.getMessage());
        }
        ActiveAndroid.beginTransaction();

        try {
            List<T> ts = basicJsonUtil.fromJsonString(this.respuesta.getJSONArray("datos").toString());

            if (basicDataAccess.acualizarDB(ts) != 0) {
                throw new Exception(this.getClass().getSimpleName() + " FalloActualizarDB");
            }

            Configuracion.guardar(getUltimaFechaMod(), this.respuesta.getString("fecha").toString());
            ActiveAndroid.setTransactionSuccessful();

            if (delegates != null) {
                AsyncDelegate unAsyncDelegate = null;
                for (Iterator<AsyncDelegate> it = this.delegates.iterator(); it.hasNext(); ) {
                    unAsyncDelegate = it.next();
                    unAsyncDelegate.executionFinished(this.respuesta.toString());
                }
            }

        } catch (Exception ex) {
            Log.e(Utils.APPTAG, this.getClass().getSimpleName() + " ErrorRecibirPersonas: " + ex.getMessage());
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    @Override
    protected String getUltimaFechaMod() {
        return Utils.UltFechaSincr + basicJsonUtil.getClase().getSimpleName();
    }

    public JSONObject getRespuesta() {
        return this.respuesta;
    }

}
