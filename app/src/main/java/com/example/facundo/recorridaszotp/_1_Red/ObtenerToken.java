package com.example.facundo.recorridaszotp._1_Red;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Configuracion;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.Scopes;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by GoRodriguez on 02/10/2015.
 */
public class ObtenerToken extends AsyncTask<Void, Void, Void> {
    private MainActivity activity;
    String charset = "UTF-8";
    URL url = null;
    HttpURLConnection conn = null; // TODO Borrar
    HttpsURLConnection conns = null; //Para conexion segura
    InputStream inputStream = null;
    String respuesta = null;

    public ObtenerToken(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Void doInBackground(Void... params) {
        String token = "";
        try {
            url = new URL(Utils.WEB_LOGIN);
            conn = (HttpURLConnection) url.openConnection();        //TODO Borrar
            //conns = (HttpsURLConnection) url.openConnection();    //TODO Agregar
            conn.setRequestMethod("POST");
            conn.setDoOutput(true); // Triggers POST.
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

            String query = String.format("email=%s&password=%s",
                    URLEncoder.encode("rodrif89@gmail.com", charset),
                    URLEncoder.encode("123456789", charset));

            //Envio datos
            OutputStream output = conn.getOutputStream();
            output.write(query.getBytes(charset));

            //Leo respuesta
            Log.v(Utils.APPTAG, "Response Code: " + conn.getResponseCode());
            inputStream = new BufferedInputStream(conn.getInputStream());
            String sToken = conn.getHeaderField("Access-Token");
            respuesta = Utils.toString(inputStream);
            Log.v(Utils.APPTAG, "Respuesta Token: " + respuesta);
            //this.activity.setToken(token);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GoogleAuthException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Toast.makeText(this.activity,
                "Token: " + this.activity.getToken(), Toast.LENGTH_SHORT).show();
    }
}
