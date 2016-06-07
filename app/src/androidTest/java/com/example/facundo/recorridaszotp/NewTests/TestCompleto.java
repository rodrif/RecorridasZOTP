package com.example.facundo.recorridaszotp.NewTests;

/**
 * Created by gonzalo on 05/06/16.
 */
import com.example.facundo.recorridaszotp.NewTests.Persona.BorrarPersona;
import com.example.facundo.recorridaszotp.NewTests.Persona.CrearPersona;
import com.example.facundo.recorridaszotp.NewTests.Persona.ModificarPersona;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   LoginTest.class,
   CrearPersona.class,
   ModificarPersona.class,
   BorrarPersona.class
})

public class TestCompleto {
    // the class remains empty,
    // used only as a holder for the above annotations
}
