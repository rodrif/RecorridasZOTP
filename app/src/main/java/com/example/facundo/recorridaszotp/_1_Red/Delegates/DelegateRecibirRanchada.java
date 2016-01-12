package com.example.facundo.recorridaszotp._1_Red.Delegates;

import com.example.facundo.recorridaszotp._2_DataAccess.RanchadaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Ranchada;

/**
 * Created by Gonzalo on 12/01/2016.
 */
public class DelegateRecibirRanchada implements AsyncDelegate {
    AsyncDelegate delegate;

    public DelegateRecibirRanchada(AsyncDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void ejecutar(String result) throws Exception {
        RanchadaDataAccess.get().sincronizar(delegate);
    }
}
