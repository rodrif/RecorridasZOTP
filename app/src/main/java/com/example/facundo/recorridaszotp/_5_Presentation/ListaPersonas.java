package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._1_Infraestructure.AdaptadorListaMenu;
import com.example.facundo.recorridaszotp._1_Infraestructure.AdaptadorListaPersonas;
import com.example.facundo.recorridaszotp._1_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.ItemLista;
import com.example.facundo.recorridaszotp._3_Domain.Persona;

import java.util.ArrayList;
import java.util.List;

public class ListaPersonas extends Fragment {
    private DrawerLayout navDrawerLayout;
    private ListView navList;
    private ArrayList<ItemLista> navItms;
    AdaptadorListaMenu navAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View vista = inflater.inflate(R.layout.fragment_lista_personas, container, false);

        DBUtils.loadDefaultDB(); //TODO Borrar
        List<Persona> listaPersonas = PersonaDataAccess.getAll();

        AdaptadorListaPersonas adaptador =
                new AdaptadorListaPersonas(getActivity().getApplicationContext(), listaPersonas);

        ListView lViewPersonas = (ListView) vista.findViewById(R.id.lista_personas);
        lViewPersonas.setAdapter(adaptador);
        lViewPersonas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> a, View view, int pos, long id) {
                Toast.makeText(getActivity().getApplicationContext(),
                        "Long Click", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return vista;
    }
}
