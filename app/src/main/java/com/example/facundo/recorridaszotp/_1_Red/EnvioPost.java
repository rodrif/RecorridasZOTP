package com.example.facundo.recorridaszotp._1_Red;

import android.os.AsyncTask;
import android.util.Log;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Configuracion;

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
public abstract class EnvioPost extends AsyncTask<String, Void, String> {

    protected abstract JSONArray cargarJson();

    @Override
    protected String doInBackground(String... params) {
        String charset = "UTF-8";
        URL url = null;
        HttpURLConnection conn = null; // TODO Borrar
        HttpsURLConnection conns = null; //Para conexion segura
        InputStream inputStream = null;
        String respuesta = null;

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Conexion segura TODO Crear certificado de pagina web.
        CertificateFactory cf = null;
        InputStream certificateInput = null;
        Certificate certificate = null;
        KeyStore keyStore = null;
        TrustManagerFactory tmf = null;
        String keyStoreType;
        SSLContext context = null;

        // Load CAs from an InputStream
        // (could be from a resource or ByteArrayInputStream or ...) // TODO Hacer por resource
        try {
            cf = CertificateFactory.getInstance("X.509");
            certificateInput = new BufferedInputStream(new FileInputStream("load-der.crt"));
            certificate = cf.generateCertificate(certificateInput);
            System.out.println("Certificate=" + ((X509Certificate) certificate).getSubjectDN());
            certificateInput.close();

            // Create a KeyStore containing our trusted CAs
            keyStoreType = KeyStore.getDefaultType();
            keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", certificate);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Create an SSLContext that uses our TrustManager
            context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);

        } catch (Exception e) {
            e.printStackTrace();
        }
////////////////////////////////////////////////////////////////////////////////////////////
        try {
            url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();        //TODO Borrar
            //conns = (HttpsURLConnection) url.openConnection();    //TODO Agregar
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
