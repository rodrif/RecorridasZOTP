package com.example.facundo.recorridaszotp._1_Red.Delegates;

import com.example.facundo.recorridaszotp._2_DataAccess.AreaDataAccess;

/**
 * Created by Gonzalo on 12/01/2016.
 */
public class DelegateRecibirAreas implements AsyncDelegate {
    AsyncDelegate delegate;

    public DelegateRecibirAreas(AsyncDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void ejecutar(String result) throws Exception {
        AreaDataAccess.get().sincronizar(delegate);
    }
}
