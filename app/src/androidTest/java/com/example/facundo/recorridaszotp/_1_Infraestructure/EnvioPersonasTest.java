package com.example.facundo.recorridaszotp._1_Infraestructure;

import android.os.AsyncTask;
import android.test.AndroidTestCase;
import android.util.Log;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Facundo on 08/08/2015.
 */
public class EnvioPersonasTest extends AndroidTestCase {

    public void testEnvioPersonas() throws Exception {
        int intentos = 0;
        List<Persona> personas = new ArrayList<Persona>();
        Persona persona1 = new Persona("Juan15");
        persona1.save();
        Persona persona2 = new Persona("Juan16");
        persona2.save();

        personas.add(persona1);
        personas.add(persona2);

        EnvioPersonas enviador = new EnvioPersonas(personas);
        enviador.execute(Utils.WEB_INSERTAR);

        while(!(enviador.getStatus() == AsyncTask.Status.FINISHED) ) {
            Thread.sleep(1000);
            intentos++;
            if(intentos > Utils.MAX_INTENTOS)
                fail("max intentos fail");
        }

        JSONObject jsonObject = enviador.getRespuesta();

        assertTrue("fallo en la respuesta del servidor", jsonObject.optInt(persona1.getId().toString()) > 0);
        assertTrue("fallo en la respuesta del servidor", jsonObject.optInt(persona2.getId().toString()) > 0);
    }

}
