package com.example.facundo.recorridaszotp._0_Infraestructure.Handlers;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.example.facundo.recorridaszotp._5_Presentation.ListaVisitas;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;
import com.example.facundo.recorridaszotp._5_Presentation.VisitaFragment;
import com.example.facundo.recorridaszotp._7_Interfaces.iFragmentChanger;
import com.example.facundo.recorridaszotp._7_Interfaces.iVisitaHandler;

/**
 * Created by gonzalo on 23/05/16.
 */
public class VisitaHandler implements iVisitaHandler {

    @Override
    public void crearVisita(Persona persona, iFragmentChanger fragmentChanger) {
       Visita nuevaVisita = new Visita(persona);
       Visita ultimaVisita = VisitaDataAccess.get().findUltimaVisita(persona);
       if (ultimaVisita != null)
           nuevaVisita.setUbicacion(ultimaVisita.getUbicacion());
       Config.getInstance().setIsEditing(false);

       MainActivity.menuGuardar(true);
       MainActivity.visitaSeleccionada = nuevaVisita;
       VisitaFragment frag = new VisitaFragment();
       frag.actualizar();

       fragmentChanger.changeFragment(frag, Utils.FRAG_VISITA, true);

    }

    @Override
    public void mostrarVisita(Visita visita, iFragmentChanger fragmentChanger) {
        Config.getInstance().setIsEditing(true);
        MainActivity.menuGuardar(true);
        MainActivity.visitaSeleccionada = visita;
        VisitaFragment frag = new VisitaFragment();
        frag.actualizar();

        fragmentChanger.changeFragment(frag, Utils.FRAG_VISITA, true);
    }

    @Override
    public void listarVisitas(long personaId, iFragmentChanger fragmentChanger) {
        Bundle bundle = new Bundle();
        bundle.putLong("personaId", personaId);
        Fragment fragment = new ListaVisitas();
        fragment.setArguments(bundle);

        fragmentChanger.changeFragment(fragment, Utils.ULTIMAS_VISITAS, true);
    }

}
