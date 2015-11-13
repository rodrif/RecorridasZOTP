package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.facundo.recorridaszotp.R;

public class VisitaFragment extends Fragment {
    private String fecha = null;
    private String observaciones = null;

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
        return v;
    }

}
