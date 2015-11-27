package com.example.facundo.recorridaszotp._2_DataAccess;

import android.test.AndroidTestCase;
import com.example.facundo.recorridaszotp._0_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Query.VisitaQuery;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import java.util.List;


/**
 * Created by Facundo on 03/10/2015.
 */
public class VisitaDataAccessTest extends AndroidTestCase { //TODO

    public void testGuardarVisita() throws Exception {
        DBUtils.loadDefaultDB();
        Persona persona1 = new Persona("Juan2");
        PersonaDataAccess.get().save(persona1);
        Visita visita = new Visita(persona1);
        visita.setDescripcion("TestObservaciones");
        visita.setLatitud(0.5);
        visita.setLongitud(0.88);
        VisitaDataAccess.get().save(visita);

        VisitaQuery query = new VisitaQuery();
        query.observaciones = "TestObservaciones";

        Visita visita2 = VisitaDataAccess.get().find(query);
        assertEquals("Visita grabada incorrectamente(Descripcion)", visita.getDescripcion(), visita2.getDescripcion());
        assertEquals("Visita grabada incorrectamente(Fecha)", visita.getFecha(), visita2.getFecha());
        assertEquals("Visita grabada incorrectamente(Latitud)", visita.getLatitud(), visita2.getLatitud());
        assertEquals("Visita grabada incorrectamente(Longitud)", visita.getLongitud(), visita2.getLongitud());
    }
}
