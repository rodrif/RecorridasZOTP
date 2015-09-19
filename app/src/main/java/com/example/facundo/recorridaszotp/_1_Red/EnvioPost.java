package com.example.facundo.recorridaszotp._1_Red;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by Facundo on 08/08/2015.
 */
public abstract class EnvioPost extends AsyncTask<String, Void, String> {

    protected abstract JSONArray cargarJson();

    @Override
    protected String doInBackground(String... params) {
        // *************************************************************
        String charset = "UTF-8";
        URL url = null;
        HttpURLConnection conn = null;
        InputStream respuesta = null;

        try {
            url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST"); // Sacar si no anda
            conn.setDoOutput(true); // Triggers POST.
            conn.setRequestProperty("Accept-Charset", charset);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

            //Construimos el objeto cliente en formato JSON
            JSONArray datos = null;
            String responseText = null;

            datos = this.cargarJson();
            Log.d("recorridaszotp", "cargarJson fin");
            String query = String.format("datos=%s", URLEncoder.encode(datos.toString(), charset));

            //Envio datos
            OutputStream output = conn.getOutputStream();
            output.write(query.getBytes(charset));

            //Leo respuesta
            System.out.println("Response Code: " + conn.getResponseCode());
            respuesta = new BufferedInputStream(conn.getInputStream());
            System.out.println("Respuesta: " + respuesta.toString());
            //String response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
            //System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // **************************************************************
    /*    HttpClient httpClient = new DefaultHttpClient();

        HttpPost post = new HttpPost(params[0]);
        post.setHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        //Construimos el objeto cliente en formato JSON
        JSONArray datos = null;
        String responseText = null;

        try {
            datos = this.cargarJson();
            Log.d("recorridaszotp", "cargarJson fin");
            nameValuePairs.add(new BasicNameValuePair("datos", datos.toString()));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse resp = httpClient.execute(post);
            responseText = EntityUtils.toString(resp.getEntity());
            Log.d("recorridaszotp", "responseText: " + responseText);
        } catch (Exception ex) {
            Log.e("recorridaszotp", "Fallo al envia post a: " + params[0], ex);
        }

        return responseText;*/
        return respuesta.toString();
    }

    @Override
    protected abstract void onPostExecute(String result);

}
