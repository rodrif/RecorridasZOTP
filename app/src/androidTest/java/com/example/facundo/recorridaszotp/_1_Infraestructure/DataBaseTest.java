package com.example.facundo.recorridaszotp._1_Infraestructure;

import android.test.AndroidTestCase;
import android.util.Log;

import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._3_Domain.Persona;

/**
 * Created by Facundo on 22/08/2015.
 */
public class DataBaseTest extends AndroidTestCase {

    public void testGuardarPersona() throws Exception {
        Persona persona = new Persona();
        persona.setNombre("Juan");
        persona.setApellido("Perez");

        persona.save();

        Persona result = new Select()
                .from(Persona.class)
                .where("Id = ?", persona.getId())
                .executeSingle();

        assertEquals(persona.getNombre(), result.getNombre());
        assertEquals(persona.getApellido(), result.getApellido());
    }
}
