package com.example.facundo.recorridaszotp._1_Red.Delegates;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Enviadores.EnvioVisitas;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;

/**
 * Created by Gonzalo on 12/01/2016.
 */
public class DelegateEnviarVisitas implements AsyncDelegate {
    AsyncDelegate delegate;

    public DelegateEnviarVisitas(AsyncDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void ejecutar(String result) throws Exception {
        EnvioVisitas envioVisitas = new EnvioVisitas(VisitaDataAccess.get().findASincronizar(), this.delegate);
        envioVisitas.execute(Utils.WEB_ENVIO_VISITAS);
    }
}
