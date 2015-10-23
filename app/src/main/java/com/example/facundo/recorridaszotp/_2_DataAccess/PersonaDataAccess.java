package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._1_Red.RecepcionPersonas;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.DelegateEnviarPersonas;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Query.PersonaQuery;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Facundo on 28/03/2015.
 */
public class PersonaDataAccess extends BasicDataAccess<Persona> {

    private static PersonaDataAccess ourInstance = new PersonaDataAccess();

    public static PersonaDataAccess get() {
        return ourInstance;
    }

    private PersonaDataAccess() {
    }

    public Class getClase() {
        return Persona.class;
    }

    public Persona find(PersonaQuery query) {
        return new Select()
                .from(Persona.class)
                .where("Nombre = ?", query.nombre)
                .executeSingle();
    }

    public int acualizarDB(List<Persona> personas) throws Exception {
        int resultado = -1;
        ActiveAndroid.beginTransaction();
        try {
            for (Persona persona : personas) {
                Persona p = new Select()
                        .from(Persona.class)
                        .where("WebId = ?", persona.getWebId())
                        .executeSingle();
                if (p != null && persona.getEstado() == Utils.EST_BORRADO) {
                    p.delete();
                } else if (p != null) {
                    p.mergeFromWeb(persona);
                    p.save();
                } else {
                    persona.save();
                }
            }
            ActiveAndroid.setTransactionSuccessful();
            resultado = 0;
        } finally {
            ActiveAndroid.endTransaction();
        }
        return resultado;
    }

    public void sincronizar(AsyncDelegate delegate) {
        sincronizar(delegate, null);
    }

    public void sincronizar(AsyncDelegate delegate, AsyncDelegate delegateRecepcionPersonas) {
        List<AsyncDelegate> delegatesRecepcionList = new ArrayList<AsyncDelegate>();
        if (delegateRecepcionPersonas != null) {
            delegatesRecepcionList.add(delegateRecepcionPersonas);
        }
        AsyncDelegate delegateEnviarPersonas = new DelegateEnviarPersonas(delegate);
        delegatesRecepcionList.add(delegateEnviarPersonas);
        RecepcionPersonas recepcionPersonas = new RecepcionPersonas(delegatesRecepcionList);
        recepcionPersonas.execute(Utils.WEB_RECIBIR_PERSONAS);
    }

}
