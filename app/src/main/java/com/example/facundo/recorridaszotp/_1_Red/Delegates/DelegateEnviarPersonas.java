package com.example.facundo.recorridaszotp._1_Red.Delegates;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._1_Red.EnvioPersonas;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;

/**
 * Created by Facundo on 05/09/2015.
 */
public class DelegateEnviarPersonas implements AsyncDelegate {
    AsyncDelegate delegate;

    public DelegateEnviarPersonas() {
    }

    public DelegateEnviarPersonas(AsyncDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void executionFinished(String result) throws Exception {
        EnvioPersonas envioPersonas = new EnvioPersonas(PersonaDataAccess.get().findASincronizar(), this.delegate);
        envioPersonas.execute(Utils.WEB_INSERTAR);
    }
}