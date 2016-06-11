package com.example.facundo.recorridaszotp._5_Presentation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;

public class NotificationFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String titulo = getArguments().getString(Utils.TITULO);
        String subtitulo = getArguments().getString(Utils.SUBTITULO);
        String descripcion = getArguments().getString(Utils.DESCRIPCION);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        TextView TVTitle = (TextView) v.findViewById(R.id.TVNotificationTitle);
        TextView TVSubtitle = (TextView) v.findViewById(R.id.TVNotificationSubtitle);
        TextView TVDescription = (TextView) v.findViewById(R.id.TVNotificationDescription);

        TVTitle.setText(titulo);
        TVSubtitle.setText(subtitulo);
        TVDescription.setText(descripcion);
        return v;
    }
}
