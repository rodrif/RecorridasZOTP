package com.example.facundo.recorridaszotp._1_Red;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.AreaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
import com.example.facundo.recorridaszotp._3_Domain.Configuracion;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.Scopes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class ObtenerToken extends AsyncTask<Void, Void, String> {
    String charset = "UTF-8";
    URL url = null;
    HttpsURLConnection conns = null;
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
            url = new URL(Utils.WEB_LOGIN);
            conns = (HttpsURLConnection) url.openConnection();
            conns.setRequestMethod("POST");
            conns.setDoOutput(true); // Triggers POST.
            conns.setRequestProperty("Accept-Charset", charset);
            conns.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

            String query = String.format("email=%s&password=%s",
                    URLEncoder.encode(Config.getInstance().getUserMail(), charset),
                    URLEncoder.encode(Config.getInstance().getUserPassword(), charset));

            //Envio datos
            OutputStream output = conns.getOutputStream();
            output.write(query.getBytes(charset));

            //Leo respuesta
            Log.v(Utils.APPTAG, "Response Code: " + conns.getResponseCode());
            inputStream = new BufferedInputStream(conns.getInputStream());
            respuesta = Utils.toString(inputStream);//FIXME Agregar rol id
            Config.getInstance().setAccessToken(conns.getHeaderField(Utils.ACCESS_TOKEN));
            Config.getInstance().setClient(conns.getHeaderField(Utils.CLIENT));
            Config.getInstance().setExpiry(conns.getHeaderField(Utils.EXPIRY));
            Config.getInstance().setUid(conns.getHeaderField(Utils.UID));
            Config.getInstance().setRol(this.getRolId(respuesta));

            Log.v(Utils.APPTAG, "Respuesta Token: " + respuesta);
            return Integer.toString(conns.getResponseCode());
            //this.activity.setToken(token);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GoogleAuthException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        if (this.activity != null) {
            progressDialog.dismiss();
        }
        if (result.equalsIgnoreCase(Integer.toString(Utils.LOGIN_OK_CODE))) {
            if (this.activity != null) {
                this.activity.loginOk();
                new Sincronizador(activity).execute();
            }
        } else {
            if (this.activity != null)
               Toast.makeText(this.activity,
                    "Usuario o contraseña invalida", Toast.LENGTH_SHORT).show();
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
        }
        return -1;
    }
}
