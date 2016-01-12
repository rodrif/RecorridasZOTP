package com.example.facundo.recorridaszotp._1_Red.Delegates;

import com.example.facundo.recorridaszotp._2_DataAccess.AreaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;

/**
 * Created by Gonzalo on 12/01/2016.
 */
public class DelegateSincronizarPersonas implements AsyncDelegate {
    AsyncDelegate delegate;

    public DelegateSincronizarPersonas(AsyncDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void ejecutar(String result) throws Exception {
        PersonaDataAccess.get().sincronizar(delegate);
    }
}
