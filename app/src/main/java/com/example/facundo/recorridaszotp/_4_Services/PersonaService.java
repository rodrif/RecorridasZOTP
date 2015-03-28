package com.example.facundo.recorridaszotp._4_Services;

import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Query.PersonaQuery;

import java.util.List;

/**
 * Created by Facundo on 28/03/2015.
 */
public class PersonaService {

    public void Save(Persona persona) {
        persona.save();
    }

    public Persona Find(PersonaQuery query) {

        List<Persona> personas = Persona.find(Persona.class, "nombre = ?", query.nombre);

        if(personas.isEmpty())
            return null;

        return personas.get(0);
    }
}
