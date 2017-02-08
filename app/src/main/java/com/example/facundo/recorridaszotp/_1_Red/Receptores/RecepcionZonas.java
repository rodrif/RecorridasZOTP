package com.example.facundo.recorridaszotp._1_Red.Receptores;

import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.AreaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.ZonaJsonUtils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._2_DataAccess.AreaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.ZonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Zona;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Gonzalo on 09/01/2016.
 */
public class RecepcionZonas extends BasicRecepcion<Zona> {

    public RecepcionZonas(){
        this(new ArrayList<AsyncDelegate>());
    }

    public RecepcionZonas(AsyncDelegate delegate) {
        this(delegate != null ? Arrays.asList(delegate) : null);
    }

    public RecepcionZonas(List<AsyncDelegate> listaDelegates) {
        super(ZonaDataAccess.get(), ZonaJsonUtils.get(), listaDelegates);
    }
}
