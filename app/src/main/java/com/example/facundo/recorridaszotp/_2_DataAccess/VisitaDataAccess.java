package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import java.util.List;

/**
 * Created by Facundo on 03/10/2015.
 */
public class VisitaDataAccess extends BasicDataAccess<Visita> {

    private static VisitaDataAccess ourInstance = new VisitaDataAccess();

    public static VisitaDataAccess get() {
        return ourInstance;
    }

    private VisitaDataAccess() {
    }

    public Class getClase() {
        return Visita.class;
    }

}
