package com.example.facundo.recorridaszotp._0_Infraestructure;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.facundo.recorridaszotp._2_DataAccess.FamiliaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.RanchadaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Familia;
import com.example.facundo.recorridaszotp._3_Domain.Ranchada;
import com.example.facundo.recorridaszotp._5_Presentation.PersonaFragment;

import java.util.List;

/**
 * Created by gonzalo on 22/08/16.
 */
public class ZonaPersonaListener implements AdapterView.OnItemSelectedListener {
    private PersonaFragment personaFragment;

    public ZonaPersonaListener(PersonaFragment personaFragment) {
        this.personaFragment = personaFragment;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String stringZona = (String)parent.getItemAtPosition(position);
        if (this.personaFragment.getAdaptadorRanchada() != null) {
            this.personaFragment.getAdaptadorRanchada().clear();
            List<Ranchada> ranchadas = RanchadaDataAccess.get().filtrarPorZona(stringZona);
            this.personaFragment.getAdaptadorRanchada().add("Ranchada");
            if (ranchadas != null)
                for (Ranchada ranchada : ranchadas) {
                    this.personaFragment.getAdaptadorRanchada().add(ranchada.getNombre());
                }
            this.personaFragment.actualizarRanchada();
        }
        if (this.personaFragment.getAdaptadorFamilia() != null) {
            this.personaFragment.getAdaptadorFamilia().clear();
            List<Familia> familias = FamiliaDataAccess.get().filtrarPorZona(stringZona);
            this.personaFragment.getAdaptadorFamilia().add("Familia");
            if (familias != null)
                for (Familia familia : familias) {
                    this.personaFragment.getAdaptadorFamilia().add(familia.getNombre());
                }
            this.personaFragment.actualizarFamilia();
        }
        this.personaFragment.setCentrarEnZona(true);
        this.personaFragment.getMapFragmentPersona().getMapAsync(this.personaFragment);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
