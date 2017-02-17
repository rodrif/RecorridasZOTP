package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by gonzalo on 16/02/17.
 */
public class ExceptionHandler extends Fragment {

    static public void makeExceptionVersionAlert(Context context) {
        AlertDialog.Builder messageBox = new AlertDialog.Builder(context);
        messageBox.setTitle("Error");
        messageBox.setMessage("Por favor actualice la versión de la aplicación");
        messageBox.setCancelable(false);
        messageBox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        messageBox.show();
    }
}
