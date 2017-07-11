package com.example.facundo.recorridaszotp._1_Red;

import android.util.Log;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;

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

public class EnvioPostBase {

    private String webUrl;
    private String query;

    public EnvioPostBase(String webUrl, String query) {
        this.webUrl = webUrl;
        this.query = query;
    }

    public String execute() {
        String charset = "UTF-8";
        URL url = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        String respuesta = null;

        try {
            url = new URL(this.webUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true); // Triggers POST.
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            conn.setRequestProperty(Utils.ACCESS_TOKEN, Config.getInstance().getAccessToken());
            conn.setRequestProperty(Utils.CLIENT, Config.getInstance().getClient());
            conn.setRequestProperty(Utils.UID, Config.getInstance().getUid());
            conn.setRequestProperty(Utils.TOKEN_TYPE, Config.getInstance().getTokenType());
            conn.setRequestProperty(Utils.EXPIRY, Config.getInstance().getExpiry());

            //Envio datos
            OutputStream output = conn.getOutputStream();
            output.write(this.query.getBytes(charset));

            if(conn.getResponseCode() == Utils.INVALID_TOKEN){
                return Integer.toString(Utils.INVALID_TOKEN);
            }

            //Leo respuesta
            Log.v(Utils.APPTAG, "Response Code: " + conn.getResponseCode());
            inputStream = new BufferedInputStream(conn.getInputStream());
            if(conn.getHeaderField(Utils.ACCESS_TOKEN) != null)
                Config.getInstance().setAccessToken(conn.getHeaderField(Utils.ACCESS_TOKEN));
            if(conn.getHeaderField(Utils.CLIENT) != null)
                Config.getInstance().setClient(conn.getHeaderField(Utils.CLIENT));
            if(conn.getHeaderField(Utils.EXPIRY) != null)
                Config.getInstance().setExpiry(conn.getHeaderField(Utils.EXPIRY));
            if(conn.getHeaderField(Utils.UID) != null)
                Config.getInstance().setUid(conn.getHeaderField(Utils.UID));
            respuesta = Utils.toString(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            //TODO deberia lanzar exception
        }
        return respuesta;
    }
}