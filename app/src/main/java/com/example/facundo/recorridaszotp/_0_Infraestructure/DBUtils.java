package com.example.facundo.recorridaszotp._0_Infraestructure;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Configuration;
import com.activeandroid.Model;
import com.activeandroid.TableInfo;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.example.facundo.recorridaszotp._2_DataAccess.GrupoFamiliarDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.RanchadaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.ZonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Area;
import com.example.facundo.recorridaszotp._3_Domain.Configuracion;
import com.example.facundo.recorridaszotp._3_Domain.GrupoFamiliar;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Ranchada;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.example.facundo.recorridaszotp._3_Domain.Zona;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Facundo on 22/08/2015.
 */
public class DBUtils {

    public static int loadDefaultDB() {
        DBUtils.deleteDBData();
        List<Persona> personas = DBUtils.getPersonasTest();
        List<Visita> visitas = DBUtils.getVisitasTest(personas);
        DBUtils.getZonaTest();
        DBUtils.getRanchadaTest();
        PersonaDataAccess.get().save(personas);

        return VisitaDataAccess.get().save(visitas);
    }

    public static List<Persona> getPersonasTest() {
        List<Persona> personas = new ArrayList<Persona>();

        personas.add(new Persona("Alfredo", "Fernandez", Utils.EST_ACTUALIZADO, 1000));
        personas.add(new Persona("Facundo", "Rodriguez", Utils.EST_MODIFICADO, 2));
        personas.add(new Persona("Gonzalo", "Rodriguez", Utils.EST_ACTUALIZADO, 1003));
        personas.add(new Persona("Pepe", "Argento", Utils.EST_ACTUALIZADO, 1004));

        return personas;
    }

    private static List<Visita> getVisitasTest(List<Persona> personas) {
        List<Visita> visitas = new ArrayList<Visita>();

        visitas.add(new Visita(personas.get(0), 1425990960000L, "desc1"));
        visitas.add(new Visita(personas.get(0), 1425992960000L));
        visitas.add(new Visita(personas.get(1), 1425990950000L, "desc3"));

        return visitas;
    }

    public static List<Zona> getZonaTest() {
        List<Zona> zonas = ZonaDataAccess.get().getAll();
        if (zonas.size() == 0) {
            zonas.add(new Zona("Zona"));
            zonas.add(new Zona("Haedo"));
            zonas.add(new Zona("Liniers"));
            zonas.add(new Zona("Ramos Mejia"));

            ZonaDataAccess.get().save(zonas);
        }
        return zonas;
    }

    public static List<Ranchada> getRanchadaTest() {
        List<Ranchada> ranchadas = RanchadaDataAccess.get().getAll();
        if (ranchadas.size() == 0) {
            ranchadas.add(new Ranchada("Ranchada"));
            ranchadas.add(new Ranchada("Estación Haedo"));
            ranchadas.add(new Ranchada("Estación Liniers"));

            RanchadaDataAccess.get().save(ranchadas);
        }
        return ranchadas;
    }

    public static List<GrupoFamiliar> getGrupoFamiliarTest() {
        List<GrupoFamiliar> gruposFamiliares = GrupoFamiliarDataAccess.get().getAll();
        if (gruposFamiliares.size() == 0) {
            gruposFamiliares.add(new GrupoFamiliar("Grupo Familiar"));
            gruposFamiliares.add(new GrupoFamiliar("Familia 1"));
            gruposFamiliares.add(new GrupoFamiliar("Familia 2"));
            gruposFamiliares.add(new GrupoFamiliar("Familia 3"));
            gruposFamiliares.add(new GrupoFamiliar("Familia 4"));
            gruposFamiliares.add(new GrupoFamiliar("Familia 5"));

            GrupoFamiliarDataAccess.get().save(gruposFamiliares);
        }
        return gruposFamiliares;
    }

    public static void deleteDBData() {
        new Delete().from(Configuracion.class).execute();
        new Delete().from(Visita.class).execute();
        new Delete().from(Persona.class).execute();
        new Delete().from(Zona.class).execute();
        new Delete().from(Ranchada.class).execute();
    }


}
