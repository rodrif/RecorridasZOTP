package com.example.facundo.recorridaszotp._2_DataAccess;

import android.test.AndroidTestCase;

import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Query.PersonaQuery;

public class PersonaDataAccessTest extends AndroidTestCase {

    public void testSave() throws Exception {
        Persona persona = new Persona("Juan");

        new PersonaDataAccess().save(persona);
    }

    public void testFind() throws Exception {
        Persona persona1 = new Persona("Juan2");
        new PersonaDataAccess().save(persona1);

        PersonaQuery query = new PersonaQuery();
        query.nombre = "Juan2";

        Persona persona2 = new PersonaDataAccess().find(query);

        assertEquals(persona1.getNombre(), persona2.getNombre());
    }
}