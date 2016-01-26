package com.example.facundo.recorridaszotp._1_Red.Mocks;

import com.example.facundo.recorridaszotp._1_Red.Enviadores.EnvioVisitas;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by Facundo on 19/10/2015.
 */
public class EnvioVisitasMock extends EnvioVisitas {

    public EnvioVisitasMock(List<Visita> visitas) {
        super(visitas);
    }

    public JSONArray cargarJson() throws Exception {
        return super.cargarJson();
    }

    @Override
    protected String doInBackground(String... params) {
        return "respuestaEnvioVisitasMock";
    }

    @Override
    protected void onPostExecute(String result) {

    }
}
