package com.example.facundo.recorridaszotp._1_Red;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.facundo.recorridaszotp._0_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.DataBaseTest;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils;
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
        CountDownLatch signal = new CountDownLatch(1);
        DBUtils.loadDefaultDB();

        List<Persona> personas = new ArrayList<Persona>();
        Persona persona1 = new Persona("Persona1Merged", "Persona1MergedAp", Utils.EST_ACTUALIZADO, 1);
        Persona persona2 = new Persona("Juan2", "nuevaPersona2");
        personas.add(persona1);
        personas.add(persona2);

        String respuestaWeb = JsonUtils.personasToJsonString(personas);

        RecepcionPersonas recepcionPersonas = new RecepcionPersonasMock(this, respuestaWeb);
        recepcionPersonas.execute("Juan2");

        if(!signal.await(Utils.MAX_INTENTOS + 5, TimeUnit.SECONDS)) {
            fail("fallo en recepcionPersonas");
        }

        assertTrue(persona1.equals(PersonaDataAccess.findByWebId(1)));
    }

    @Override
    public void executionFinished(String result) throws Exception {
        Log.d(Utils.APPTAG, "executionFinished: " + result);
        this.signal.countDown();
    }
}
