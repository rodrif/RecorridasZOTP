package com.example.facundo.recorridaszotp._1_Red.Receptores;

import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.FamiliaJsonUtils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._2_DataAccess.FamiliaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Familia;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Gonzalo on 09/01/2016.
 */
public class RecepcionFamilias extends BasicRecepcion<Familia> {

    public RecepcionFamilias(){
        this(new ArrayList<AsyncDelegate>());
    }

    public RecepcionFamilias(AsyncDelegate delegate) {
        this(delegate != null ? Arrays.asList(delegate) : null);
    }

    public RecepcionFamilias(List<AsyncDelegate> listaDelegates) {
        super(FamiliaDataAccess.get(), FamiliaJsonUtils.get(), listaDelegates);
    }

    @Override
    protected JSONArray cargarJson() {
        JSONArray datos = new JSONArray();
        return datos;
    }
}
