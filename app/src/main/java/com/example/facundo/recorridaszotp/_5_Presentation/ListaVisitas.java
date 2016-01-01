package com.example.facundo.recorridaszotp._5_Presentation;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.AdaptadorListaVisitas;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._0_Infraestructure.onSelectedItemListener;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
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
    private onSelectedItemListener clicklistener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View vista = inflater.inflate(R.layout.fragment_lista_visitas, container, false);

        final List<Visita> listaVisitas = VisitaDataAccess.get().getAllOK();

        AdaptadorListaVisitas adaptador =
                new AdaptadorListaVisitas(getActivity().getApplicationContext(), listaVisitas);

        ListView lViewVisitas = (ListView) vista.findViewById(R.id.lista_visitas);
        lViewVisitas.setAdapter(adaptador);
        lViewVisitas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (clicklistener == null) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Listener null", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    clicklistener.mostrarPersona(listaVisitas.get(position).getPersona());
                    return true;
                }
            }
        });

        lViewVisitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (clicklistener == null) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Listener null", Toast.LENGTH_SHORT).show();
                } else {
                    clicklistener.mostrarVisita(listaVisitas.get(position));
                }
            }
        });

        return vista;
    }

    @Override
    public void onAttach(Activity activity) { //No anda el onAttach(Context context) can API < 23
        super.onAttach(activity);
        clicklistener = (onSelectedItemListener) activity;
        ((MainActivity) activity).getAppbar().setTitle(Utils.ULTIMAS_VISITAS);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        clicklistener = null;
        ((MainActivity) getActivity()).getAppbar().setTitle(Utils.HOME);
    }
}
