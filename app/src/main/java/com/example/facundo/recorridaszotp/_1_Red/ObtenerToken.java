package com.example.facundo.recorridaszotp._1_Red;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by GoRodriguez on 02/10/2015.
 */
public class ObtenerToken extends AsyncTask<Void, Void, String> {
    String charset = "UTF-8";
    URL url = null;
    HttpURLConnection conns = null;
    InputStream inputStream = null;
    String respuesta = null;
    private MainActivity activity = null;
    private ProgressDialog progressDialog;

    public ObtenerToken(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        if (this.activity != null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("Ingresando al sistema...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        String token = "";
        try {
            String query = String.format(
                    "email=%s&password=%s",
                    URLEncoder.encode(Config.getInstance().getUserMail(), charset),
                    URLEncoder.encode(Config.getInstance().getUserPassword(), charset)
            );
            EnvioPostBase envioPostBase = new EnvioPostBase(Utils.WEB_LOGIN, query);
            respuesta = envioPostBase.execute();
            Config.getInstance().setRol(this.getRolId(respuesta));
            Config.getInstance().setArea(this.getAreaId(respuesta));
            Log.v(Utils.APPTAG, "Respuesta Token: " + respuesta);
            return Integer.toString(envioPostBase.getLastReturnCode());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Log.e(Utils.APPTAG, e.toString());
            return "sinConexion";
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(Utils.APPTAG, e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Utils.APPTAG, e.toString());
        }
        return "-1";
    }

    @Override
    protected void onPostExecute(String result) {
        Log.e(Utils.APPTAG, result);
        if (this.activity != null) {
            progressDialog.dismiss();
        }
        if (result.equalsIgnoreCase(Integer.toString(Utils.LOGIN_OK_CODE))) {
            if (this.activity != null) {
                this.activity.loginOk();
                new Sincronizador(activity).execute();
            }
        } else {
            if (this.activity != null) {
                String mensaje = "";
                if(result == "sinConexion") {
                    mensaje = "Sin conexión a internet";
                } else {
                    mensaje = "Usuario o contraseña invalida";
                }
                Toast.makeText(this.activity, mensaje, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private int getRolId(String jsonString) {
        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);
            JSONObject data = json.getJSONObject("data");
            return data.getInt("rol_id");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(Utils.APPTAG, e.toString());
        }
        return -1;
    }

    private int getAreaId(String jsonString) {
        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);
            JSONObject data = json.getJSONObject("data");
            return data.getInt("area_id");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(Utils.APPTAG, e.toString());
        }
        return -1;
    }
}
