package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.facundo.recorridaszotp.R;


public class FormularioFragment extends Fragment {
    private String nombre = null;
    private String apellido = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            nombre = b.getString("nombre");
            apellido = b.getString("apellido");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_formulario, container, false);

        if (nombre != null) {
            EditText et = (EditText) v.findViewById(R.id.ETNombre);
            et.setText(nombre);
        }

        if (apellido != null) {
            EditText et = (EditText) v.findViewById(R.id.ETApellido);
            et.setText(apellido);
        }

        return v;
    }

}
