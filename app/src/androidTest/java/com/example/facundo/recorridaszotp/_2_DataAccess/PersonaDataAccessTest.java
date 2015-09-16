package com.example.facundo.recorridaszotp._2_DataAccess;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.facundo.recorridaszotp._0_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.AsyncDelegate;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Query.PersonaQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class PersonaDataAccessTest extends AndroidTestCase {

    public void testFind() throws Exception {
        DBUtils.loadDefaultDB();
        Persona persona1 = new Persona("Juan2");
        PersonaDataAccess.save(persona1);
        PersonaQuery query = new PersonaQuery();
        query.nombre = "Juan2";
        Persona persona2 = PersonaDataAccess.find(query);

        assertEquals("Nombre de la persona buscada incorrecto", persona1.getNombre(), persona2.getNombre());
    }

    public void testFindPersonasASincronizar() throws Exception {
        List<Persona> personasTest = DBUtils.getPersonasTest();
        DBUtils.loadDefaultDB();

        List<Persona> personas = PersonaDataAccess.findPersonasASincronizar();

        assertEquals(1, personas.size());
        assertEquals(personasTest.get(1).toJSonValue(), personas.get(0).toJSonValue());
    }

    public void testAcualizarDB() throws Exception{
        DBUtils.loadDefaultDB();
        List<Persona> personasTest = new ArrayList<Persona>();
        personasTest.add(new Persona("NuevaPersona1", "apellido", Utils.EST_ACTUALIZADO, 1000));
        personasTest.add(new Persona("NuevaPersona2", "apellido", Utils.EST_ACTUALIZADO, 1001));
        personasTest.add(new Persona("PersonaModif3", "apellido", Utils.EST_ACTUALIZADO, 2));
        personasTest.add(new Persona("PersonaBorrada4", "apellido", Utils.EST_BORRADO, 4));
        PersonaDataAccess.acualizarDB(personasTest);

        Persona persona1 = PersonaDataAccess.findByWebId(1000);
        Persona persona2 = PersonaDataAccess.findByWebId(1001);
        Persona persona3 = PersonaDataAccess.findByWebId(2);
        Persona persona4 = PersonaDataAccess.findByWebId(4);

        assertEquals(DBUtils.getPersonasTest().size() + 1, PersonaDataAccess.getAll().size());
        assertNull(persona4);
        assertTrue(personasTest.get(0).equals(persona1));
        assertTrue(personasTest.get(1).equals(persona2));
        assertTrue(personasTest.get(2).equals(persona3));
    }

    public void testSincronizar() throws Exception {
        CountDownLatch signal = new CountDownLatch(1);
        DBUtils.loadDefaultDB();
        SincronizarDelegate delegate = new SincronizarDelegate(signal);
        PersonaDataAccess.sincronizar(delegate);

        if(!signal.await(Utils.MAX_INTENTOS + 5, TimeUnit.SECONDS)) {
            fail("no recibio respuesta del servidor");
        }
    }

    public class SincronizarDelegate implements AsyncDelegate {
        private CountDownLatch signal;

        public SincronizarDelegate(CountDownLatch signal) {
            this.signal = signal;
        }
        @Override
        public void executionFinished(String result) throws Exception {
            this.signal.countDown();
        }
    }
}