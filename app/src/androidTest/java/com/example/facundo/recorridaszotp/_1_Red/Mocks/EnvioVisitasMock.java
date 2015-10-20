package com.example.facundo.recorridaszotp._1_Red.Mocks;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.EnvioVisitas;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Facundo on 19/10/2015.
 */
public class EnvioVisitasMock extends EnvioVisitas {

    public EnvioVisitasMock(List<Visita> visitas) {
        super(visitas);
    }

    @Override
    protected String doInBackground(String... params) {
        return "respuestaEnvioVisitasMock";
    }

    @Override
    protected void onPostExecute(String result) {

    }
}
