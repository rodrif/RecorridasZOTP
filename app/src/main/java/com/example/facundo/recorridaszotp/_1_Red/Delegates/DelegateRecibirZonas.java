package com.example.facundo.recorridaszotp._1_Red.Delegates;

import com.example.facundo.recorridaszotp._2_DataAccess.ZonaDataAccess;

/**
 * Created by Gonzalo on 09/01/2016.
 */
public class DelegateRecibirZonas implements AsyncDelegate {
    AsyncDelegate delegate;

    public DelegateRecibirZonas(AsyncDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void ejecutar(String result) throws Exception {
        ZonaDataAccess.get().sincronizar(delegate);
    }
}
