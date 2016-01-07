package com.example.facundo.recorridaszotp._1_Red.Delegates;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Enviadores.EnvioAreas;
import com.example.facundo.recorridaszotp._1_Red.Enviadores.EnvioPersonas;
import com.example.facundo.recorridaszotp._2_DataAccess.AreaDataAccess;

/**
 * Created by Gonzalo on 05/01/2016.
 */
public class DelegateEnviarAreas implements AsyncDelegate {
    AsyncDelegate delegate;

    public DelegateEnviarAreas() {
    }

    public DelegateEnviarAreas(AsyncDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void ejecutar(String result) throws Exception {
        EnvioAreas envioAreas = new EnvioAreas(AreaDataAccess.get().findASincronizar(), this.delegate);
        envioAreas.execute(Utils.WEB_ENVIO_AREA);
    }
}
