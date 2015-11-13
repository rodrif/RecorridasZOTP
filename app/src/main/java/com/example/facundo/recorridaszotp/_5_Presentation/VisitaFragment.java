package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.DatePickerFragment;

import java.util.Calendar;

public class VisitaFragment extends Fragment {
    private String fecha = null;
    private String observaciones = null;
    private EditText etFecha = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            fecha = b.getString("fecha");
            observaciones = b.getString("observaciones");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_visita, container, false);

        ImageButton ib = (ImageButton) v.findViewById(R.id.bFecha);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show2();
            }
        });

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        etFecha = (EditText) v.findViewById(R.id.ETFecha);

        cargarFecha(Integer.toString(day) + "/" +
                Integer.toString(month) + "/" + Integer.toString(year));
        return v;
    }

    private void show2() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "DatePicker");
    }

    public void cargarFecha(String fecha) {
        etFecha.setText(fecha);
    }
}