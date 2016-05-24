package com.example.facundo.recorridaszotp._0_Infraestructure.Handlers;

import android.app.FragmentTransaction;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.example.facundo.recorridaszotp._5_Presentation.VisitaFragment;
import com.example.facundo.recorridaszotp._7_Interfaces.iVisitaHandler;

/**
 * Created by gonzalo on 23/05/16.
 */
public class VisitaHandler implements iVisitaHandler {

    @Override
    public void mostrarVisita(Persona persona) {
       Visita nuevaVisita = new Visita(persona);
       Visita ultimaVisita = VisitaDataAccess.get().findUltimaVisita(persona);
       if (ultimaVisita != null)
           nuevaVisita.setUbicacion(ultimaVisita.getUbicacion());
       Config.getInstance().setIsEditing(false);

       menuGuardar(true);
       visitaSeleccionada = visita;
       VisitaFragment frag = new VisitaFragment();
       frag.actualizar();

       FragmentTransaction ft = getFragmentManager().beginTransaction();
       ft.addToBackStack(Utils.FRAG_VISITA);
       ft.replace(R.id.content_frame, frag, Utils.FRAG_VISITA);
       ft.commit();
    }
}
