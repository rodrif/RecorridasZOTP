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
    private int num = -1;
    private String nombre = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            String nom = getArguments().getString("nombre");
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

        return v;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setNombre(String nombre) {
        EditText et = (EditText) this.getActivity().findViewById(R.id.ETNombre);
        et.setText(nombre);
    }
}
