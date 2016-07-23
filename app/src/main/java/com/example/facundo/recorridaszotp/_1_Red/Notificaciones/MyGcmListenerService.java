/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.facundo.recorridaszotp._1_Red.Notificaciones;

        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.media.RingtoneManager;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v4.app.NotificationCompat;
        import android.util.Log;

        import com.example.facundo.recorridaszotp.R;
        import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
        import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;
        import com.google.android.gms.gcm.GcmListenerService;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = Utils.APPTAG + "MyGcmLis";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String title = data.getString(Utils.TITULO);
        String subtitle = data.getString(Utils.SUBTITULO);
        String description = data.getString(Utils.DESCRIPCION);
        String codigoNotificacion = data.getString(Utils.CODIGO_NOTIFICACION, "");
        String idPersona = data.getString(Utils.PERSONA_ID, "");

        Log.e(Utils.APPTAG, "Notificacion1: codigo: " + codigoNotificacion +
                " idPersona: " + idPersona + " titulo: " + title +
                " subtitulo: " + subtitle + " decripcion: " + description);


        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */

        sendNotification(codigoNotificacion, idPersona, title, subtitle, description);
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String codigoNotificacion, String idPersona, String title, String subtitle, String description) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Utils.CODIGO_NOTIFICACION, codigoNotificacion);
        intent.putExtra(Utils.PERSONA_ID, idPersona);
        intent.putExtra(Utils.TITULO, title);
        intent.putExtra(Utils.SUBTITULO, subtitle);
        intent.putExtra(Utils.DESCRIPCION, description);
        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis()
                , intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logosisinfondo);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(subtitle)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setLargeIcon(bitmap);
        } else {
            notificationBuilder.setSmallIcon(R.drawable.logosisinfondo);
        }


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int) System.currentTimeMillis() /* ID of notification */, notificationBuilder.build());
    }
}
