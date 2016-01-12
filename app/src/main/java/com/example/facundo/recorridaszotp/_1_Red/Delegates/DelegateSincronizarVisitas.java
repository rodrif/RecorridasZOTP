package com.example.facundo.recorridaszotp._1_Red.Delegates;

import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;

/**
 * Created by Gonzalo on 12/01/2016.
 */
public class DelegateSincronizarVisitas implements AsyncDelegate {
    AsyncDelegate delegate;

    public DelegateSincronizarVisitas(AsyncDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void ejecutar(String result) throws Exception {
        VisitaDataAccess.get().sincronizar(delegate);
    }
}
