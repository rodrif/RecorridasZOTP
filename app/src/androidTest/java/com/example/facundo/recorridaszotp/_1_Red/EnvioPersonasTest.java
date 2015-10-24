package com.example.facundo.recorridaszotp._1_Red;

import android.test.AndroidTestCase;

import com.example.facundo.recorridaszotp._0_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._1_Red.Enviadores.EnvioPersonas;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Facundo on 08/08/2015.
 */
public class EnvioPersonasTest extends AndroidTestCase implements AsyncDelegate {
    private CountDownLatch signal;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DBUtils.loadDefaultDB();
    }

    public void testEnvioPersonas() throws Exception {
        signal = new CountDownLatch(1);
        List<Persona> personas = new ArrayList<Persona>();
        Persona persona1 = new Persona("envioPersonas1");
        persona1.save();
        Persona persona2 = new Persona("envioPersonas2");
        persona2.save();
        Persona persona3 = new Persona("envioPersonas3", "ap3", Utils.EST_MODIFICADO, 1);
        persona3.save();

        personas.add(persona1);
        personas.add(persona2);
        personas.add(persona3);

        EnvioPersonas enviador = new EnvioPersonas(personas, this);
        enviador.execute(Utils.WEB_INSERTAR);

        if(!this.signal.await(Utils.MAX_INTENTOS, TimeUnit.SECONDS)) {
            fail("no recibio respuesta del servidor");
        }

        JSONObject datos = enviador.getRespuesta().getJSONObject("datos");

        assertTrue("fallo en la respuesta del servidor", datos.optInt(persona1.getId().toString()) > 0);
        assertTrue("fallo en la respuesta del servidor", datos.optInt(persona2.getId().toString()) > 0);
        assertTrue("fallo en la respuesta del servidor", datos.optInt(persona3.getId().toString()) == 1);
        assertEquals("mal guardado en la bd, envio personas", PersonaDataAccess.get().findByWebId(datos.optInt(persona1.getId().toString())).getId(), persona1.getId());
        assertEquals("mal guardado en la bd, envio personas", PersonaDataAccess.get().findByWebId(datos.optInt(persona2.getId().toString())).getId(), persona2.getId());
        assertEquals("mal guardado en la bd, envio personas", PersonaDataAccess.get().findByWebId(datos.optInt(persona3.getId().toString())).getId(), persona3.getId());
    }

    @Override
    public void executionFinished(String result) {
        signal.countDown();
    }
}
