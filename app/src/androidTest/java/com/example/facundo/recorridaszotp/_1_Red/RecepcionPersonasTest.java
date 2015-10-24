package com.example.facundo.recorridaszotp._1_Red;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.facundo.recorridaszotp._0_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.PersonaJsonUtils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._1_Red.Mocks.RecepcionPersonasMock;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionPersonas;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Facundo on 05/09/2015.
 */
public class RecepcionPersonasTest extends AndroidTestCase implements AsyncDelegate {
    private CountDownLatch signal;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DBUtils.loadDefaultDB();
    }

    public void testRecepcionPersonas() throws Exception {
        this.signal = new CountDownLatch(1);

        List<Persona> personas = new ArrayList<Persona>();
        Persona persona1 = new Persona("Persona1Merged", "Persona1MergedAp", Utils.EST_ACTUALIZADO, 1000);
        Persona persona2 = new Persona("Juan2", "nuevaPersona2", Utils.EST_ACTUALIZADO, 2002);
        Persona persona3 = new Persona("personaWeb3Merge", "personaWeb3Ap", Utils.EST_ACTUALIZADO, 1004);
        Persona persona4 = new Persona("nom", "ap", Utils.EST_BORRADO, 1003);
        personas.add(persona1);
        personas.add(persona2);
        personas.add(persona3);
        personas.add(persona4);

        JSONObject respuestaJsonObject = new JSONObject();
        String personasJsonString = PersonaJsonUtils.get().toJsonString(personas);
        respuestaJsonObject.put("datos", new JSONArray(personasJsonString));
        respuestaJsonObject.put("fecha", "2015-09-26 08:28:41.368");

        List<AsyncDelegate> delegates = new ArrayList<AsyncDelegate>();
        delegates.add(this);
        RecepcionPersonas recepcionPersonas = new RecepcionPersonasMock(delegates, respuestaJsonObject);
        recepcionPersonas.execute("cualquiera");

        if (!signal.await(Utils.MAX_INTENTOS, TimeUnit.SECONDS)) {
            fail("fallo en recepcionPersonas");
        }

        assertEquals(DBUtils.getPersonasTest().size(), PersonaDataAccess.get().getAll().size());
        assertTrue(persona1.equals(PersonaDataAccess.get().findByWebId(1000)));
        assertNull(PersonaDataAccess.get().findByWebId(1003));
    }

    @Override
    public void executionFinished(String result) throws Exception {
        Log.d(Utils.APPTAG, "executionFinished: " + result);
        this.signal.countDown();
    }
}
