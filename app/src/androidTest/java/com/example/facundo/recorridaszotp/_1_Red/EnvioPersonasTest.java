package com.example.facundo.recorridaszotp._1_Red;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
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

    public void testEnvioPersonas() throws Exception {
        signal = new CountDownLatch(1);
        List<Persona> personas = new ArrayList<Persona>();
        Persona persona1 = new Persona("Juan15");
        persona1.save();
        Persona persona2 = new Persona("Juan16");
        persona2.save();
        Persona persona3 = new Persona("Juan3", "ap3", Utils.EST_MODIFICADO, 1);
        persona3.save();

        personas.add(persona1);
        personas.add(persona2);
        personas.add(persona3);

        EnvioPersonas enviador = new EnvioPersonas(personas, this);
        enviador.execute(Utils.WEB_INSERTAR);

        if(!this.signal.await(Utils.MAX_INTENTOS, TimeUnit.SECONDS)) {
            fail("no recibio respuesta del servidor");
        }

        JSONObject jsonObject = enviador.getRespuesta();

        assertTrue("fallo en la respuesta del servidor", jsonObject.optInt(persona1.getId().toString()) > 0);
        assertTrue("fallo en la respuesta del servidor", jsonObject.optInt(persona2.getId().toString()) > 0);
        assertTrue("fallo en la respuesta del servidor", jsonObject.optInt(persona3.getId().toString()) == 1);

    }

    @Override
    public void executionFinished(String result) {
        signal.countDown();
    }
}
