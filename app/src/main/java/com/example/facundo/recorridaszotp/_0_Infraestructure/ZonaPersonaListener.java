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
        this.personaFragment.getMapFragmentPersona().getMapAsync(this.personaFragment);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
