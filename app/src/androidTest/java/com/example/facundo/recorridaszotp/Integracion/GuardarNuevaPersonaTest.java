package com.example.facundo.recorridaszotp.Integracion;

import android.test.AndroidTestCase;

import com.example.facundo.recorridaszotp._0_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.PersonaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._1_Red.RecepcionPersonas;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Facundo on 26/09/2015.
 */
public class GuardarNuevaPersonaTest extends AndroidTestCase {

    public void testGuardarNuevaPersona() throws Exception {
        DBUtils.deleteDBData();
        CountDownLatch signal = new CountDownLatch(1);
        AsyncDelegate delegate = new SincronizarDelegate(signal);

        Persona persona = new Persona("personaTest", "apelido", Utils.EST_NUEVO);
        persona.save();

        PersonaDataAccess.sincronizar(delegate);

        if (!signal.await(Utils.MAX_INTENTOS + 5, TimeUnit.SECONDS)) {
            fail("fallo en GuardarNuevaPersonaTest");
        }

        signal = new CountDownLatch(1);
        delegate = new SincronizarDelegate(signal);
        RecepcionPersonas recepcion = new RecepcionPersonas(delegate);
        recepcion.execute(Utils.WEB_RECIBIR_PERSONAS);

        if (!signal.await(Utils.MAX_INTENTOS + 500, TimeUnit.SECONDS)) {
            fail("fallo en GuardarNuevaPersonaTest2");
        }

        List<Persona> respuestaPersonas = PersonaJsonUtils.personasFromJsonString(recepcion.getRespuesta().getJSONArray("datos").toString());

        assertEquals("testGuardarNuevaPersona fallo cantidad personas", 1, respuestaPersonas.size());
        assertTrue(PersonaDataAccess.findById(persona.getId()).equals(respuestaPersonas.get(0)));
    }

    private class SincronizarDelegate implements AsyncDelegate {
        CountDownLatch signal;

        public SincronizarDelegate(CountDownLatch signal) {
            this.signal = signal;
        }

        @Override
        public void executionFinished(String result) throws Exception {
            this.signal.countDown();
        }
    }

}