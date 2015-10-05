package com.example.facundo.recorridaszotp._1_Red;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.Scopes;

import java.io.IOException;

/**
 * Created by GoRodriguez on 02/10/2015.
 */
public class ObtenerToken extends AsyncTask<Void, Void, Void> {
    private MainActivity activity;


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
            token = GoogleAuthUtil.getToken(this.activity, this.activity.getEmail(), Scopes.EMAIL);
            this.activity.setToken(token);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GoogleAuthException e) {
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
