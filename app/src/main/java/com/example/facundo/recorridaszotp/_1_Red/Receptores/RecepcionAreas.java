package com.example.facundo.recorridaszotp._1_Red.Receptores;

import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.AreaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.BasicJsonUtil;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._2_DataAccess.AreaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.BasicDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Area;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Gonzalo on 07/01/2016.
 */
public class RecepcionAreas extends BasicRecepcion<Area> {

    public RecepcionAreas() {
        this(new ArrayList<AsyncDelegate>());
    }

    public RecepcionAreas(AsyncDelegate delegate) {
        this(delegate != null ? Arrays.asList(delegate) : null);
    }

    public RecepcionAreas(List<AsyncDelegate> listaDelegates) {
        super(AreaDataAccess.get(), AreaJsonUtils.get(), listaDelegates);
    }

    @Override
    protected JSONArray cargarJson() {
        JSONArray datos = new JSONArray();
        return datos;
    }
}
