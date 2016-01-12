package com.example.facundo.recorridaszotp._1_Red.Delegates;

import com.example.facundo.recorridaszotp._2_DataAccess.FamiliaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Familia;

/**
 * Created by Gonzalo on 12/01/2016.
 */
public class DelegateRecibirFamilia implements AsyncDelegate {
    AsyncDelegate delegate;

    public DelegateRecibirFamilia(AsyncDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void ejecutar(String result) throws Exception {
        FamiliaDataAccess.get().sincronizar(delegate);
    }
}
