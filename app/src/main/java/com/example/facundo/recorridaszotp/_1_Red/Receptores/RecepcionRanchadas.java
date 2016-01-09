package com.example.facundo.recorridaszotp._1_Red.Receptores;

import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.RanchadaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.ZonaJsonUtils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._2_DataAccess.RanchadaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.ZonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Ranchada;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Gonzalo on 09/01/2016.
 */
public class RecepcionRanchadas extends BasicRecepcion<Ranchada> {
    public RecepcionRanchadas(){
        this(new ArrayList<AsyncDelegate>());
    }

    public RecepcionRanchadas(AsyncDelegate delegate) {
        this(delegate != null ? Arrays.asList(delegate) : null);
    }

    public RecepcionRanchadas(List<AsyncDelegate> listaDelegates) {
        super(RanchadaDataAccess.get(), RanchadaJsonUtils.get(), listaDelegates);
    }

    @Override
    protected JSONArray cargarJson() {
        JSONArray datos = new JSONArray();
        return datos;
    }
}
