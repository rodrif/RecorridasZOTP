package com.example.facundo.recorridaszotp._1_Red;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.LoginEvent;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.AreaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
import com.example.facundo.recorridaszotp._3_Domain.Configuracion;
import com.example.facundo.recorridaszotp._3_Domain.Roles;
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
            url = new URL(Utils.WEB_LOGIN);
            conns = (HttpURLConnection) url.openConnection();
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
            respuesta = Utils.toString(inputStream);
            Config.getInstance().setAccessToken(conns.getHeaderField(Utils.ACCESS_TOKEN));
            Config.getInstance().setClient(conns.getHeaderField(Utils.CLIENT));
            Config.getInstance().setExpiry(conns.getHeaderField(Utils.EXPIRY));
            Config.getInstance().setUid(conns.getHeaderField(Utils.UID));
            Config.getInstance().setRol(this.getRolId(respuesta));
            Config.getInstance().setArea(this.getAreaId(respuesta));
            this.logUserCrashlytics(respuesta);

            Log.v(Utils.APPTAG, "Respuesta Token: " + respuesta);
            return Integer.toString(conns.getResponseCode());
            //this.activity.setToken(token);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Log.e(Utils.APPTAG, e.toString());
            return "sinConexion";
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(Utils.APPTAG, e.toString());
        } catch (GoogleAuthException e) {
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

    private void logUserCrashlytics(String respuesta) {
        try {
            JSONObject json = new JSONObject(respuesta);
            JSONObject datos = json.getJSONObject("data");
            Crashlytics.setUserIdentifier(datos.getString("uid"));
            Crashlytics.setUserName(datos.optString("name", "") + " " +
                            datos.optString("apellido", "")
            );
            Crashlytics.setString("rol", Roles.getInstance()
                    .getRoleName(Integer.parseInt(datos.getString("rol_id"))));
            Crashlytics.setString("area", AreaDataAccess.get()
                    .getName(Integer.parseInt(datos.getString("area_id"))));
        } catch (JSONException e) {
            e.printStackTrace();
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
