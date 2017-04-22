package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.activeandroid.util.SQLiteUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._1_Red.Delegates.DelegateEnviarVisitas;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionVisitas;
import com.example.facundo.recorridaszotp._3_Domain.Area;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Query.VisitaQuery;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.example.facundo.recorridaszotp._3_Domain.Zona;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

/**
 * Created by Facundo on 03/10/2015.
 */
public class VisitaDataAccess extends BasicDataAccess<Visita> {

    private static VisitaDataAccess ourInstance = new VisitaDataAccess();

    public static VisitaDataAccess get() {
        return ourInstance;
    }

    private VisitaDataAccess() {
    }

    public Class getClase() {
        return Visita.class;
    }

    @Override
    public int acualizarDB(List<Visita> visitas) throws Exception {
        int resultado = -1;
        ActiveAndroid.beginTransaction();
        try {
            for (Visita visita : visitas) {
                Visita v = new Select()
                        .from(Visita.class)
                        .where("WebId = ?", visita.getWebId())
                        .executeSingle();
                if (v != null && visita.getEstado() == Utils.EST_BORRADO) {
                    v.delete();
                } else if (v != null) {
                    v.mergeFromWeb(visita);
                    v.save();
                } else if (visita.getEstado() != Utils.EST_BORRADO) {
                    visita.save();
                }
            }
            ActiveAndroid.setTransactionSuccessful();
            resultado = 0;
        } finally {
            ActiveAndroid.endTransaction();
        }
        return resultado;
    }

    public Visita find(VisitaQuery query) {
        if (query != null)
            if (query.observaciones != null)
                return new Select()
                        .from(Visita.class)
                        .where("Descripcion = ?", query.observaciones)
                        .executeSingle();

        return null;
    }

    public Visita findUltimaVisita(Persona persona) {
        if (persona.getId() != null)
            return new Select()
                    .from(Visita.class)
                    .where("Persona = ?", persona.getId())
                    .where("Estado != ?", Utils.EST_BORRADO)
                    .orderBy("Fecha DESC")
                    .executeSingle();
        else
            return null;
    }

    public List<Visita> findUltimasVisita() {
        List<Visita> resultado = SQLiteUtils.rawQuery(Visita.class,
                "SELECT * FROM Visitas " +
                        "WHERE Estado != ? " +
                        "GROUP BY Persona " +
                        "HAVING Fecha >= MAX(Fecha)", new String[] {"3"});
        return resultado;
    }

    public Visita find(Marker marker) {
        return new Select()
                .from(Visita.class)
                .where("Latitud = ?", marker.getPosition().latitude)
                .where("Longitud = ?", marker.getPosition().longitude)
                .executeSingle();
    }

    public void deleteLogico(Visita visita) {
        visita.setEstado(Utils.EST_BORRADO);
        visita.save();
    }

    public List<Visita> findTodasVisitas(Persona persona) {
        if (persona.getId() != null)
            return new Select()
                    .from(Visita.class)
                    .where("Persona = ?", persona.getId())
                    .where("Estado != ?", Utils.EST_BORRADO)
                    .orderBy("Fecha DESC")
                    .execute();
        else
            return null;
    }

    public void deleteLogico(Persona persona) {
        List<Visita> listaVisita = VisitaDataAccess.get().findTodasVisitas(persona);
        for (Visita unaVisita : listaVisita) {
            VisitaDataAccess.get().deleteLogico(unaVisita);
        }
    }

    public List<Visita> getAllOKOrderFecha() {
        return new Select()
                .from(Visita.class)
                .orderBy("Fecha DESC")
                .where("Estado != ?", Utils.EST_BORRADO)
                .execute();
    }

    public List<Visita> getAllOKPorAreaOrderFecha() {
        Area area = new Select()
                .from(Area.class)
                .where("WebId = ?", Config.getInstance().getArea())
                .executeSingle();

        return new Select()
                .from(Visita.class)
                .innerJoin(Persona.class)
                .on("Persona = Personas.Id")
                .innerJoin(Zona.class)
                .on("Zona = Zonas.Id")
                .orderBy("Fecha DESC")
                .where("Visitas.Estado != ?", Utils.EST_BORRADO)
                .where("Area = ?", area.getId())
                .execute();
    }

    public List<Visita> getAllOKOrderFecha(long personaId) {
        return new Select()
                .from(Visita.class)
                .orderBy("Fecha DESC")
                .where("Estado != ?", Utils.EST_BORRADO)
                .where("Persona = ?", personaId)
                .execute();
    }

    public void sincronizar(AsyncDelegate delegate) {
        AsyncDelegate delegateEnviarVisitas = new DelegateEnviarVisitas(delegate);
        RecepcionVisitas recepcionVisitas = new RecepcionVisitas(delegateEnviarVisitas);
        recepcionVisitas.execute(Utils.WEB_RECIBIR_VISITAS);
    }

    public boolean hayConEstadoModificado() {
        List<Visita> lista = new Select()
                .from(Visita.class)
                .where("Estado = ?", Utils.EST_MODIFICADO)
                .execute();

        if (lista.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean hayConEstadoBorrado() {
        List<Visita> lista = new Select()
                .from(Visita.class)
                .where("Estado = ?", Utils.EST_BORRADO)
                .execute();

        if (lista.isEmpty()) {
            return false;
        }
        return true;
    }
}
