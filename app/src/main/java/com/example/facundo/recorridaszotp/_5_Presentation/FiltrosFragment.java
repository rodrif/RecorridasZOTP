package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.activeandroid.util.Log;
import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.ZonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Filtros;
import com.example.facundo.recorridaszotp._3_Domain.Zona;

import java.util.ArrayList;
import java.util.List;

import static com.example.facundo.recorridaszotp._3_Domain.Filtros.get;

/**
 * Created by gonzalo on 01/11/17.
 */

public class FiltrosFragment extends Fragment {

    private Spinner sZona = null;
    ArrayAdapter<String> adaptadorZona = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View vista = inflater.inflate(R.layout.fragment_filtros, container, false);

        sZona = (Spinner) vista.findViewById(R.id.spinner_filtroZona);
        final List<Zona> lZonas = ZonaDataAccess.get().getAllOKFiltradoPorArea();
        final List<String> zonasString = new ArrayList<String>();
        zonasString.add("Zona");
        for (Zona zona : lZonas) {
            zonasString.add(zona.getNombre());
        }
        adaptadorZona = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, zonasString);
        adaptadorZona.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        sZona.setAdapter(adaptadorZona);
        actualizarZona();
        this.setMenu();
        return vista;
    }

    private void actualizarZona() {
        long zonaId = Filtros.get().getZonaId();
        if (zonaId != -1) {
            Zona zonaSeleccionada = ZonaDataAccess.get().findById(zonaId);
            this.sZona.setSelection(adaptadorZona.getPosition(zonaSeleccionada.getNombre()));
        }
    }

    private void setMenu() {
        ((MainActivity)getActivity()).getAppbar().setTitle(Utils.FILTROS);
        MainActivity.menuGuardar(false);
    }



}
