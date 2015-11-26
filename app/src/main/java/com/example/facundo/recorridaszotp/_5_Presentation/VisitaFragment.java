package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.DatePickerFragment;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import java.util.Calendar;

public class VisitaFragment extends Fragment {
    private static View vista;
    private EditText etFecha = null;
    private EditText etObservaciones = null;
    private double latitud = Double.NaN;
    private double longitud = Double.NaN;
    //private boolean editando = false;
    private MapsFragment mapsFragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        View v = inflater.inflate(R.layout.fragment_visita, container, false);
        if (vista != null) {
            ViewGroup parent = (ViewGroup) vista.getParent();
            if (parent != null)
                parent.removeView(vista);
        }
        try {
            vista = inflater.inflate(R.layout.fragment_visita, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is  */
        }

        ImageButton ib = (ImageButton) vista.findViewById(R.id.bFecha);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPicker();
            }
        });

        etFecha = (EditText) vista.findViewById(R.id.ETFecha);
        etObservaciones = (EditText) vista.findViewById(R.id.ETObservacioneVisita);

/*         if (MainActivity.visitaSeleccionada.getFechaString() != null)
            etFecha.setText(MainActivity.visitaSeleccionada.getFechaString());
        else
            cargarFechaActual();

        if (MainActivity.visitaSeleccionada.getDescripcion() != null) {
            ((EditText) vista.findViewById(R.id.ETObservacioneVisita)).setText(MainActivity.visitaSeleccionada.getDescripcion());
        }*/
        actualizar();

        mapsFragment = (MapsFragment) getFragmentManager().findFragmentById(R.id.mapsFragment);
        if (mapsFragment != null)
            mapsFragment.actualizarMapa();

        return vista;
    }

    private void showDataPicker() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), Utils.DATE_PICKER_VISITA);
    }

    public void cargarFechaActual() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        this.cargarFecha(day, month, year);
    }

    public void cargarFecha(int day, int month, int year) {
        etFecha.setText(Integer.toString(day) + "/" +
                Integer.toString(month) + "/" + Integer.toString(year));
    }

 /*   public boolean estaEditando() {
        return editando;
    }*/

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void actualizar() {
        if (etFecha != null) {
            if (MainActivity.visitaSeleccionada.getFechaString() != null)
                etFecha.setText(MainActivity.visitaSeleccionada.getFechaString());
            else
                cargarFechaActual();

            if (MainActivity.visitaSeleccionada.getDescripcion() != null) {
                ((EditText) vista.findViewById(R.id.ETObservacioneVisita)).setText(MainActivity.visitaSeleccionada.getDescripcion());
            }
        }
    }
}