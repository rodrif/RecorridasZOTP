package com.example.facundo.recorridaszotp._5_Presentation;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.AdaptadorListaVisitas;
import com.example.facundo.recorridaszotp._0_Infraestructure.Handlers.VisitaHandler;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._0_Infraestructure.onSelectedItemListener;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.example.facundo.recorridaszotp._7_Interfaces.iFragmentChanger;
import com.example.facundo.recorridaszotp._7_Interfaces.iVisitaHandler;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Gonzalo on 20/11/2015.
 */
public class ListaVisitas extends Fragment {
    private List<Visita> listaVisitas;
    private iFragmentChanger fragmentChanger;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View vista = inflater.inflate(R.layout.fragment_lista_visitas, container, false);
        long personaId = -1;
        Bundle bundle = getArguments();
        if (bundle != null) {
            personaId = bundle.getLong("personaId", -1);
        }

        getVisitas(personaId);
        final iVisitaHandler visitaHandler = getVisitaHandler();

        AdaptadorListaVisitas adaptador =
                new AdaptadorListaVisitas(getActivity().getApplicationContext(), listaVisitas);
        ListView lViewVisitas = (ListView) vista.findViewById(R.id.lista_visitas);
        lViewVisitas.setAdapter(adaptador);
        lViewVisitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                visitaHandler.mostrarVisita(listaVisitas.get(position), fragmentChanger);
            }
        });

        return vista;
    }

    private iVisitaHandler getVisitaHandler() {
        return new VisitaHandler();
    }

    private void getVisitas(long personaId) {
        listaVisitas = personaId == -1 ? VisitaDataAccess.get().getAllOKOrderFecha()
                : VisitaDataAccess.get().getAllOKOrderFecha(personaId);
    }

    @Override
    public void onAttach(Activity activity) { //No anda el onAttach(Context context) can API < 23
        super.onAttach(activity);
        fragmentChanger = (iFragmentChanger) activity;
        ((MainActivity) activity).getAppbar().setTitle(Utils.ULTIMAS_VISITAS);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentChanger = null;
        ((MainActivity) getActivity()).getAppbar().setTitle(Utils.HOME);
    }
}
