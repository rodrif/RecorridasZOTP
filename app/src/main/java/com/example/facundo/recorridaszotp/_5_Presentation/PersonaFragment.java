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

import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.DatePickerFragment;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.RanchadaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.ZonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Ranchada;
import com.example.facundo.recorridaszotp._3_Domain.Zona;

import java.util.ArrayList;
import java.util.Iterator;
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

        //Grupo Familiar
        Spinner sGrupoFamiliar = (Spinner) v.findViewById(R.id.spinner_grupo_familiar);
        final List<String> familiasString = new ArrayList<String>();
        familiasString.add("Familia 1");
        familiasString.add("Familia 2");
        familiasString.add("Familia 3");
        familiasString.add("Familia 4");
        ArrayAdapter<String> adaptadorFamilia =
                new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, familiasString);

        adaptadorFamilia.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        sGrupoFamiliar.setAdapter(adaptadorFamilia);

        //Zona
        DBUtils.loadDefaultDB();//FIXME borrar, solo para prueba
        Spinner sZona = (Spinner) v.findViewById(R.id.spinner_zona);

        final List<Zona> lZonas = ZonaDataAccess.get().getAll();//TODO revisar, puede ser null sin internet
        final List<String> zonasString = new ArrayList<String>();
        for (Zona zona : lZonas) {
            zonasString.add(zona.getNombre());
        }
        ArrayAdapter<String> adaptadorZona =
                new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, zonasString);
        sZona.setAdapter(adaptadorZona);

        //Ranchada
        Spinner sRanchada = (Spinner) v.findViewById(R.id.spinner_ranchada);

        final List<Ranchada> lRanchada = RanchadaDataAccess.get().getAll();//TODO revisar, puede ser null sin internet
        final List<String> ranchadasString = new ArrayList<String>();
        for (Ranchada ranchada : lRanchada) {
            zonasString.add(ranchada.getNombre());
        }
        ArrayAdapter<String> adaptadorRanchada =
                new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, ranchadasString);
        sRanchada.setAdapter(adaptadorRanchada);

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
