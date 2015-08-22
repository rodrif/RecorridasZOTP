package com.example.facundo.recorridaszotp._2_DataAccess;

import android.test.AndroidTestCase;

import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Query.PersonaQuery;

public class PersonaDataAccessTest extends AndroidTestCase {

    public void testFind() throws Exception {
        Persona persona1 = new Persona("Juan2");
        PersonaDataAccess.save(persona1);

        PersonaQuery query = new PersonaQuery();
        query.nombre = "Juan2";

        Persona persona2 = PersonaDataAccess.find(query);

        assertEquals("Nombre de la persona buscada incorrecto", persona1.getNombre(), persona2.getNombre());
    }
}