package com.example.facundo.recorridaszotp._4_Services;

import android.test.AndroidTestCase;

import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Query.PersonaQuery;

import junit.framework.TestCase;

import dalvik.annotation.TestTargetClass;

public class PersonaServiceTest extends AndroidTestCase {

    public void testSave() throws Exception {
        Persona persona = new Persona("Juan");

        new PersonaService().Save(persona);
    }

    public void testFind() throws Exception {
        Persona persona1 = new Persona("Juan2");
        new PersonaService().Save(persona1);

        PersonaQuery query = new PersonaQuery();
        query.nombre = "Juan2";

        Persona persona2 = new PersonaService().Find(query);

        assertEquals(persona1.getNombre(), persona2.getNombre());
    }
}