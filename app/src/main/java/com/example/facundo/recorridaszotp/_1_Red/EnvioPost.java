package com.example.facundo.recorridaszotp._1_Red;

import android.os.AsyncTask;
import android.util.Log;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
import com.example.facundo.recorridaszotp._3_Domain.Configuracion;
//import com.google.android.gms.auth.GoogleAuthUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;


/**
 * Created by Facundo on 08/08/2015.
 */
public abstract class EnvioPost {

    protected abstract JSONArray cargarJson() throws Exception;
    protected abstract String getUltimaFechaMod();

    public void execute(String webUrl) {
        String charset = "UTF-8";
        //Construimos el objeto cliente en formato JSON
        JSONArray datos = null;
        try {
            datos = this.cargarJson();
            Log.d(Utils.APPTAG, "Se envio JSON: " + datos.toString());
            String ultFechaSincronizacion = Configuracion.get(getUltimaFechaMod());
            String query = String.format("datos=%s", URLEncoder.encode(datos.toString(), charset));
            query += String.format("&version=%s", URLEncoder.encode(Utils.VERSION, charset));
            if (ultFechaSincronizacion != null) {
                query += String.format("&fecha=%s", URLEncoder.encode(ultFechaSincronizacion, charset));
            }
            this.onPostExecute(new EnvioPostBase(webUrl, query).execute());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onPostExecute(String result) {
        if(result == Integer.toString(Utils.INVALID_TOKEN)) {
            Log.e(Utils.APPTAG, "Invalid token...obteniendo token");
            new ObtenerToken(null).execute();
        }
    };

}
