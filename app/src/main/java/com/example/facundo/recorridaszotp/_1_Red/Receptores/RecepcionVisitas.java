package com.example.facundo.recorridaszotp._1_Red.Receptores;

import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.PersonaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.VisitaJsonUtils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Facundo on 26/10/2015.
 */
public class RecepcionVisitas extends BasicRecepcion<Visita> {

    public RecepcionVisitas() {
        this(new ArrayList<AsyncDelegate>());
    }

    public RecepcionVisitas(AsyncDelegate delegate) {
        this(delegate != null ? Arrays.asList(delegate): null);
    }

    public RecepcionVisitas(List<AsyncDelegate> delegate) {
        super(VisitaDataAccess.get(), VisitaJsonUtils.get(), delegate);
    }

    @Override
    protected JSONArray cargarJson() {
        JSONArray datos = new JSONArray();
        return datos;
    }
}
