package com.example.facundo.recorridaszotp._1_Red;

import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.PersonaJsonUtils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Facundo on 05/09/2015.
 */
public class RecepcionPersonas extends BasicRecepcion<Persona> {

    public RecepcionPersonas() {
        this(new ArrayList<AsyncDelegate>());
    }

    public RecepcionPersonas(AsyncDelegate delegate) {
        this(delegate != null ? Arrays.asList(delegate): null);
    }

    public RecepcionPersonas(List<AsyncDelegate> delegate) {
        super(PersonaDataAccess.get(), PersonaJsonUtils.get(), delegate);
    }

    @Override
    protected JSONArray cargarJson() {
        JSONArray datos = new JSONArray();
        return datos;
    }

}
