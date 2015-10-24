package com.example.facundo.recorridaszotp._1_Red.Mocks;

import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._1_Red.RecepcionPersonas;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Facundo on 14/09/2015.
 */
public class RecepcionPersonasMock extends RecepcionPersonas {

    public RecepcionPersonasMock(List<AsyncDelegate> delegates, JSONObject respuesta) {
        this.delegates = delegates;
        this.respuesta = respuesta;
    }

    @Override
    protected String doInBackground(String... params) {
        return this.getRespuesta().toString();
    }

}
