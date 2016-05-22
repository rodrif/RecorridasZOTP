package com.example.facundo.recorridaszotp;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;

/**
 * Created by nkali on 21/05/16.
 */
public class NotificationHandler extends IntentService {

    public NotificationHandler() {
        super("NotificationHandler");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(Utils.APPTAG, "Notificacion handler");
        Utils.SALUDO_INICIAL = "saludo modificado";
    }
}
