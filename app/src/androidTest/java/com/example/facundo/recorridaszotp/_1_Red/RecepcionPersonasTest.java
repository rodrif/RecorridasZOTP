package com.example.facundo.recorridaszotp._1_Red;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.facundo.recorridaszotp._0_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.PersonaJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Mocks.RecepcionPersonasMock;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Facundo on 05/09/2015.
 */
public class RecepcionPersonasTest extends AndroidTestCase implements AsyncDelegate {
    private CountDownLatch signal;

    public void testRecepcionPersonas() throws Exception {
        this.signal = new CountDownLatch(1);
        DBUtils.loadDefaultDB();

        List<Persona> personas = new ArrayList<Persona>();
        Persona persona1 = new Persona("Persona1Merged", "Persona1MergedAp", Utils.EST_ACTUALIZADO, 1);
        Persona persona2 = new Persona("Juan2", "nuevaPersona2", Utils.EST_ACTUALIZADO, 2);
        Persona persona3 = new Persona("personaWeb3", "personaWeb3Ap", Utils.EST_ACTUALIZADO, 300);
        Persona persona4 = new Persona("nom", "ap", Utils.EST_BORRADO, 3);
        personas.add(persona1);
        personas.add(persona2);
        personas.add(persona3);
        personas.add(persona4);

        String respuestaWeb = PersonaJsonUtils.personasToJsonString(personas);
        RecepcionPersonas recepcionPersonas = new RecepcionPersonasMock(this, respuestaWeb);
        recepcionPersonas.execute("cualquiera");

        if (!signal.await(Utils.MAX_INTENTOS, TimeUnit.SECONDS)) {
            fail("fallo en recepcionPersonas");
        }

        assertEquals(DBUtils.getPersonasTest().size(), PersonaDataAccess.getAll().size());
        assertTrue(persona1.equals(PersonaDataAccess.findByWebId(1)));
        assertNull(PersonaDataAccess.findByWebId(3));
    }

    @Override
    public void executionFinished(String result) throws Exception {
        Log.d(Utils.APPTAG, "executionFinished: " + result);
        this.signal.countDown();
    }
}
