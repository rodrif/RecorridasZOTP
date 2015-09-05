package com.example.facundo.recorridaszotp._1_Red;

import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;

import java.util.List;

/**
 * Created by Facundo on 05/09/2015.
 */
public class DelegateActualizarDB implements AsyncDelegate{
    AsyncDelegate delegate;

    public DelegateActualizarDB() {}

    public DelegateActualizarDB(AsyncDelegate delegate) { this.delegate = delegate; }

    @Override
    public void executionFinished(String result) throws Exception {
        List<Persona> personas = JsonUtils.personasFromJsonString(result);

        if (PersonaDataAccess.acualizarDB(personas) != 0) {
            throw new Exception("FalloActualizarDB");
        }
        if (this.delegate != null) {
            delegate.executionFinished("");
        }
    }
}
