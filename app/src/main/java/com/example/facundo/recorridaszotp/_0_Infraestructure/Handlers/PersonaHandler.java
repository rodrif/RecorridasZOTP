package com.example.facundo.recorridaszotp._0_Infraestructure.Handlers;

import android.app.Fragment;
import android.app.FragmentTransaction;

import com.activeandroid.util.Log;
import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;
import com.example.facundo.recorridaszotp._5_Presentation.PersonaFragment;
import com.example.facundo.recorridaszotp._7_Interfaces.iFragmentChanger;
import com.example.facundo.recorridaszotp._7_Interfaces.iPersonaHandler;

/**
 * Created by gonzalo on 25/05/16.
 */
public class PersonaHandler implements iPersonaHandler {
    @Override
    public void mostrarPersona(Persona persona, iFragmentChanger fragmentChanger) {
        MainActivity.menuGuardar(true);
        MainActivity.personaSeleccionada = persona;
        Config.getInstance().setIsEditing(true);
        MainActivity.visitaSeleccionada = VisitaDataAccess.get().findUltimaVisita(persona);
        Fragment frag = new PersonaFragment();

        fragmentChanger.changeFragment(frag, Utils.FRAG_PERSONA, true);
    }

    @Override
    public void mostrarPersona(int personaId, iFragmentChanger fragmentChanger) {
        Persona persona = PersonaDataAccess.get().findByWebId(personaId);
        if (persona != null) {
            this.mostrarPersona(persona, fragmentChanger);
        } else {
            Log.d(Utils.APPTAG, "persona null en PersonaHandler::mostrarPersona");
        }
    }
}
