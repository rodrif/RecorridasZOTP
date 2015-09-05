package com.example.facundo.recorridaszotp._1_Red;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Facundo on 08/08/2015.
 */
public abstract class EnvioPost extends AsyncTask<String, Void, String> {

    protected abstract JSONArray cargarJson();

    @Override
    protected String doInBackground(String... params) {
        HttpClient httpClient = new DefaultHttpClient();

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

        return responseText;
    }

    @Override
    protected abstract void onPostExecute(String result);

}
