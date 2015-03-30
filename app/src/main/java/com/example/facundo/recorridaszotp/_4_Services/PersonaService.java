package com.example.facundo.recorridaszotp._4_Services;

import com.activeandroid.query.Select;
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

        return new Select()
                .from(Persona.class)
                .where("Nombre = ?", query.nombre)
                .orderBy("id desc")
                .executeSingle();
    }
}
