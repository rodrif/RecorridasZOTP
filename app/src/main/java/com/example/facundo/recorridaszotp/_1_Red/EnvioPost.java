package com.example.facundo.recorridaszotp._1_Red;

import android.os.AsyncTask;
import android.util.Log;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Configuracion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;


/**
 * Created by Facundo on 08/08/2015.
 */
public abstract class EnvioPost extends AsyncTask<String, Void, String> {

    protected abstract JSONArray cargarJson();

    @Override
    protected String doInBackground(String... params) {
        String charset = "UTF-8";
        URL url = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        String respuesta = null;

        try {
            url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true); // Triggers POST.
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

            //Construimos el objeto cliente en formato JSON
            JSONArray datos = this.cargarJson();
            String ultFechaSincronizacion = Configuracion.get(Utils.UltFechaSincr);

            String query = String.format("datos=%s", URLEncoder.encode(datos.toString(), charset));
            if (ultFechaSincronizacion != null) {
                query += String.format("&fecha=%s", URLEncoder.encode(ultFechaSincronizacion, charset));
            }
            //Envio datos
            OutputStream output = conn.getOutputStream();
            output.write(query.getBytes(charset));

            //Leo respuesta
            Log.d(Utils.APPTAG, "Response Code: " + conn.getResponseCode());
            inputStream = new BufferedInputStream(conn.getInputStream());

            respuesta = Utils.toString(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respuesta;
    }

    @Override
    protected abstract void onPostExecute(String result);

}
