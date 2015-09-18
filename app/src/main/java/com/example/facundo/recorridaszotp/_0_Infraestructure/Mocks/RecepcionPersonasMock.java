package com.example.facundo.recorridaszotp._0_Infraestructure.Mocks;

import android.util.Log;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.AsyncDelegate;
import com.example.facundo.recorridaszotp._1_Red.RecepcionPersonas;

/**
 * Created by Facundo on 14/09/2015.
 */
public class RecepcionPersonasMock extends RecepcionPersonas {


    public RecepcionPersonasMock(AsyncDelegate delegate, String respuesta) {
        this.delegate = delegate;
        this.respuesta = respuesta;
    }

    @Override
    protected String doInBackground(String... params) {
        return this.getRespuesta();
    }

}
