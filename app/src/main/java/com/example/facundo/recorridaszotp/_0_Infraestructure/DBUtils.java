package com.example.facundo.recorridaszotp._0_Infraestructure;

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
        List<Persona> personas = DBUtils.getPersonasTest();

        return PersonaDataAccess.save(personas);
    }

    public static List<Persona> getPersonasTest() {
        List<Persona> personas = new ArrayList<Persona>();

        personas.add(new Persona("Alfredo", "Fernandez", Utils.EST_ACTUALIZADO, 1000));
        personas.add(new Persona("Facundo", "Rodriguez", Utils.EST_MODIFICADO, 2));
        personas.add(new Persona("Gonzalo", "Rodriguez", Utils.EST_ACTUALIZADO, 1003));
        personas.add(new Persona("Pepe", "Argento", Utils.EST_ACTUALIZADO, 1004));

        return personas;
    }

    public static void deleteDBData() {
        new Delete().from(Persona.class).execute();
    }


}
