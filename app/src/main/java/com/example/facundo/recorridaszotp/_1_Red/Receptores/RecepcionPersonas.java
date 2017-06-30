package com.example.facundo.recorridaszotp._1_Red.Receptores;

import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.PersonaJsonUtils;
import com.example.facundo.recorridaszotp._1_Red.Receptores.BasicRecepcion;
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
        super(PersonaDataAccess.get(), PersonaJsonUtils.get());
    }
}
