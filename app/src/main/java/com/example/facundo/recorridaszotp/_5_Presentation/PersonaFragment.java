package com.example.facundo.recorridaszotp._5_Presentation;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.facundo.recorridaszotp.R;

import java.util.ArrayList;
import java.util.List;


public class PersonaFragment extends Fragment {
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

        View v = inflater.inflate(R.layout.fragment_persona, container, false);
        if (nombre != null) {
            EditText et = (EditText) v.findViewById(R.id.ETNombre);
            et.setText(nombre);
        }

        if (apellido != null) {
            EditText et = (EditText) v.findViewById(R.id.ETApellido);
            et.setText(apellido);
        }

        Spinner sGrupoFamiliar = (Spinner)v.findViewById(R.id.spinner_grupo_familiar);
        final List<String> datos = new ArrayList<String>();
        datos.add("Familia 1");
        datos.add("Familia 2");
        datos.add("Familia 3");
        datos.add("Familia 4");

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, datos);

        adaptador.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        sGrupoFamiliar.setAdapter(adaptador);
       return v;
    }

}
