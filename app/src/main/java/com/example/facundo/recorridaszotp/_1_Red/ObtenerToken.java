package com.example.facundo.recorridaszotp._1_Red;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;

import java.io.IOException;

/**
 * Created by GoRodriguez on 02/10/2015.
 */
public class ObtenerToken extends AsyncTask<String, Void, String> {
    private Activity activity;
    private String token;
    private String email;

    public ObtenerToken(Activity activity, String email, String token) {
        this.activity = activity;
        this.email = email;
        this.token = token;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {
        String email = params[0];
        String token = "";
        try {
            token = GoogleAuthUtil.getToken(this.activity, email, Utils.SCOPE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GoogleAuthException e) {
            e.printStackTrace();
        }
        return token;
    }

    @Override
    protected void onPostExecute(String result) {
        this.token = result;
    }
}
