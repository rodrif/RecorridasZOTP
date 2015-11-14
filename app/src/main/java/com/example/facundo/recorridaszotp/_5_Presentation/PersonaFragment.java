package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.DatePickerFragment;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;

import java.util.ArrayList;
import java.util.List;


public class PersonaFragment extends Fragment {
    private String nombre = null;
    private String apellido = null;
    private EditText etFechaNacimiento = null;

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

        ImageButton ib = (ImageButton) v.findViewById(R.id.bFechaNacimiento);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPicker();
            }
        });

        etFechaNacimiento = (EditText) v.findViewById(R.id.ETFechaNacimiento);

        if (nombre != null) {
            EditText et = (EditText) v.findViewById(R.id.ETNombre);
            et.setText(nombre);
        }

        if (apellido != null) {
            EditText et = (EditText) v.findViewById(R.id.ETApellido);
            et.setText(apellido);
        }

        Spinner sGrupoFamiliar = (Spinner) v.findViewById(R.id.spinner_grupo_familiar);
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

    private void showDataPicker() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), Utils.DATE_PICKER_PERSONA);
    }

    public void cargarFecha(int day, int month, int year) {
        etFechaNacimiento.setText(Integer.toString(day) + "/" +
                Integer.toString(month) + "/" + Integer.toString(year));
    }

}
