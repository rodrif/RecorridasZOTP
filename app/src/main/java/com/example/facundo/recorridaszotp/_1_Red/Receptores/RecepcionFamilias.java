package com.example.facundo.recorridaszotp._1_Red.Receptores;

import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.FamiliaJsonUtils;
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

    public RecepcionFamilias() {
        super(FamiliaDataAccess.get(), FamiliaJsonUtils.get());
    }
}
