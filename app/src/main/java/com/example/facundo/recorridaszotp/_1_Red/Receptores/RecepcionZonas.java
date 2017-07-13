package com.example.facundo.recorridaszotp._1_Red.Receptores;

import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.AreaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.ZonaJsonUtils;
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

    public RecepcionZonas() {
        super(Utils.WEB_RECIBIR_ZONAS, ZonaDataAccess.get(), ZonaJsonUtils.get());
    }
}
