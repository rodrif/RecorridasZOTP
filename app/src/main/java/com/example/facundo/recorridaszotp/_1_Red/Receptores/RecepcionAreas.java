package com.example.facundo.recorridaszotp._1_Red.Receptores;

import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.AreaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.BasicJsonUtil;
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
        super(AreaDataAccess.get(), AreaJsonUtils.get());
    }
}
