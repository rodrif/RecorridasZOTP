package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionPersonas;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Area;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Query.PersonaQuery;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.example.facundo.recorridaszotp._3_Domain.Zona;

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
                } else if (persona.getEstado() != Utils.EST_BORRADO) {
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

    public boolean hayConEstadoModificado() {
        List<Persona> lista = new Select()
                .from(Persona.class)
                .where("Estado = ?", Utils.EST_MODIFICADO)
                .execute();

        if (lista.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean hayConEstadoBorrado() {
        List<Persona> lista = new Select()
                .from(Persona.class)
                .where("Estado = ?", Utils.EST_BORRADO)
                .execute();

        if (lista.isEmpty()) {
            return false;
        }
        return true;
    }

    public void deleteLogico(Persona persona) {
        VisitaDataAccess.get().deleteLogico(persona);
        PedidoDataAccess.get().deleteLogico(persona);
        persona.setEstado(Utils.EST_BORRADO);
        persona.save();
    }

    public List<Persona> getAllOK() {
        return new Select()
                .from(Persona.class)
                .innerJoin(Zona.class)
                .on("Zona = Zonas.Id")
                .orderBy("Zonas.Nombre ASC, Personas.Nombre ASC")
                .where("Personas.Estado != ?", Utils.EST_BORRADO)
                .execute();
    }

    public List<Persona> getAllOKPorAreaYZona() {
        Area area = new Select()
                .from(Area.class)
                .where("WebId = ?", Config.getInstance().getArea())
                .executeSingle();

        Select select = new Select()
                .from(Persona.class)
                .innerJoin(Zona.class)
                .on("Zona = Zonas.Id")
                .orderBy("Zonas.Nombre ASC, Personas.Nombre ASC")
                .where("Personas.Estado != ?", Utils.EST_BORRADO)
                .where("Area = ?", area.getId());

        select.where("Zonas.Id = ?", 2);
        return select.execute();
    }
}
