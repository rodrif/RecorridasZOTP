package com.example.facundo.recorridaszotp._5_Presentation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
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
        String codigoNotificacion = getArguments().getString(Utils.CODIGO_NOTIFICACION);
        String idPersona = getArguments().getString(Utils.PERSONA_ID);
        String titulo = getArguments().getString(Utils.TITULO);
        String subtitulo = getArguments().getString(Utils.SUBTITULO);
        String descripcion = getArguments().getString(Utils.DESCRIPCION);
        Log.d(Utils.APPTAG, "Notificacion: codigo: " + codigoNotificacion +
            " idPersona: " + idPersona + " titulo: " + titulo +
        " subtitulo: " + subtitulo + " decripcion: " + descripcion);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        EditText TVTitle = (EditText) v.findViewById(R.id.TVNotificationTitle);
        EditText TVSubtitle = (EditText) v.findViewById(R.id.TVNotificationSubtitle);
        EditText TVDescription = (EditText) v.findViewById(R.id.TVNotificationDescription);

        TVTitle.setText(titulo);
        switch (codigoNotificacion) {
            case "1":
                Log.e(Utils.APPTAG, "llegue");
                TVTitle.setRight(R.drawable.clock);
                break;
            case "2":
                TVTitle.setRight(R.drawable.peligro);
                break;
            case "3":
                TVTitle.setRight(R.drawable.cumple);
                break;
        }
        TVSubtitle.setText(subtitulo);
        TVDescription.setText(descripcion);
        return v;
    }

    @Override
    public String toString() {
        return Utils.FRAG_NOTIFICACION;
    }
}
