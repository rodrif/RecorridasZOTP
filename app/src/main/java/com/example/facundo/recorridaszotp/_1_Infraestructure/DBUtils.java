package com.example.facundo.recorridaszotp._1_Infraestructure;

import android.content.ClipData;

import com.activeandroid.query.Delete;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Facundo on 22/08/2015.
 */
public class DBUtils {

    public static int loadDefaultDB() {
        DBUtils.deleteDBData();
        List<Persona> personas = new ArrayList<Persona>();

        personas.add(new Persona("Alfredo", "Fernandez"));
        personas.add(new Persona("Facundo", "Rodriguez"));
        personas.add(new Persona("Gonzalo", "Rodriguez"));
        personas.add(new Persona("Pepe", "Argento"));

        return PersonaDataAccess.save(personas);
    }

    private static void deleteDBData() {
        new Delete().from(Persona.class).execute();
    }
}
