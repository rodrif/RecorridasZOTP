package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.google.android.gms.common.SignInButton;

public class HomeFragment extends Fragment {
    private SignInButton signInButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        TextView textView = (TextView) v.findViewById(R.id.TVSaludo);
        textView.setText(Utils.SALUDO_INICIAL);

     /*   signInButton = (SignInButton)v.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mGoogleApiClient.connect();
            }
        });*/
        return v;
    }
}
