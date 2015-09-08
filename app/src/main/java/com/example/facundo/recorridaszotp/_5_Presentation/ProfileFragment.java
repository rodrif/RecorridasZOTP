package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.facundo.recorridaszotp.R;

/*  Fragment para seccion perfil */
public class ProfileFragment extends Fragment {

    public ProfileFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        return rootView;
    }
}
