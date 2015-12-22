package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.AdaptadorListaPersonas;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._0_Infraestructure.onSelectedItemListener;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import java.util.List;

public class ListaPersonas extends Fragment {
    private onSelectedItemListener clicklistener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View vista = inflater.inflate(R.layout.fragment_lista_personas, container, false);

        final List<Persona> listaPersonas = PersonaDataAccess.get().getAll();

        AdaptadorListaPersonas adaptador =
                new AdaptadorListaPersonas(getActivity().getApplicationContext(), listaPersonas);

        ListView lViewPersonas = (ListView) vista.findViewById(R.id.lista_personas);
        lViewPersonas.setAdapter(adaptador);
        lViewPersonas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (clicklistener == null) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Listener null", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    clicklistener.mostrarPersona(listaPersonas.get(position));
                    return true;
                }
            }
        });

        lViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (clicklistener == null) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Listener null", Toast.LENGTH_SHORT).show();
                } else {
                    Visita nuevaVisita = new Visita(listaPersonas.get(position));
                    Visita ultimaVisita = VisitaDataAccess.get()
                            .findUltimaVisita(listaPersonas.get(position));
                    if (ultimaVisita != null)
                        nuevaVisita.setUbicacion(ultimaVisita.getUbicacion());
                    clicklistener.mostrarVisita(nuevaVisita);
                }
            }
        });

        return vista;
    }

    @Override
    public void onAttach(Activity activity) { //No anda el onAttach(Context context) can API < 23
        super.onAttach(activity);
        clicklistener = (onSelectedItemListener) activity;
        ((MainActivity)activity).getAppbar().setTitle(Utils.LISTA_PERSONAS);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        clicklistener = null;
        ((MainActivity)getActivity()).getAppbar().setTitle(Utils.HOME);
    }
}
