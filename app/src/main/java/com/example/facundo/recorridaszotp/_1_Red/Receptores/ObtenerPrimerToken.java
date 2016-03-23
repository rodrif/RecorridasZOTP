package com.example.facundo.recorridaszotp._1_Red.Receptores;

import android.util.Base64;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by gonzalo on 22/03/16.
 */
public class ObtenerPrimerToken {

    static public void getToken() {
        //FIXME get Token
        URL myURL = null;
        try {
            myURL = new URL(Utils.WEB_LOGIN);
        HttpURLConnection myURLConnection = (HttpURLConnection)myURL.openConnection();
        String userCredentials = "username:password";
//        String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
 //       myURLConnection.setRequestProperty ("Authorization", basicAuth);
        myURLConnection.setRequestMethod("POST");
        myURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        myURLConnection.setRequestProperty("Content-Length", "" + Integer.toString(postData.getBytes().length));
        myURLConnection.setRequestProperty("Content-Language", "en-US");
        myURLConnection.setUseCaches(false);
        myURLConnection.setDoInput(true);
        myURLConnection.setDoOutput(true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
