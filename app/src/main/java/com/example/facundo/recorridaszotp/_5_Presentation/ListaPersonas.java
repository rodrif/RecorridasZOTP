package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.AdaptadorListaPersonas;
import com.example.facundo.recorridaszotp._0_Infraestructure.Handlers.PersonaHandler;
import com.example.facundo.recorridaszotp._0_Infraestructure.Handlers.VisitaHandler;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._7_Interfaces.iFragmentChanger;
import com.example.facundo.recorridaszotp._7_Interfaces.iPersonaHandler;
import com.example.facundo.recorridaszotp._7_Interfaces.iVisitaHandler;

import java.util.List;

public class ListaPersonas extends Fragment {
    private iFragmentChanger fragmentChanger;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View vista = inflater.inflate(R.layout.fragment_lista_personas, container, false);

        final List<Persona> listaPersonas = getPersonas();
        final iVisitaHandler visitaHandler = getVisitaHandler();
        final iPersonaHandler personaHandler = getPersonaHandler();

        AdaptadorListaPersonas adaptador =
                new AdaptadorListaPersonas(getActivity().getApplicationContext(), listaPersonas);

        ListView lViewPersonas = (ListView) vista.findViewById(R.id.lista_personas);
        lViewPersonas.setAdapter(adaptador);
        lViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity().getApplicationContext(), view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.crear_visita:
                                 visitaHandler.crearVisita(listaPersonas.get(position), fragmentChanger);
                                break;
                            case R.id.editar_persona:
                                personaHandler.mostrarPersona(listaPersonas.get(position), fragmentChanger);
                                break;
                            case R.id.ver_visitas:
                                long personaId = listaPersonas.get(position).getId();
                                Bundle bundle = new Bundle();
                                bundle.putLong("personaId", personaId);
                                Fragment fragment = new ListaVisitas();
                                fragment.setArguments(bundle);
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.addToBackStack(Utils.FRAG_VISITA);
                                ft.replace(R.id.content_frame, fragment, Utils.FRAG_VISITA);
                                ft.commit();
                                break;
                            }
                            return true;
                        }
                    });

                    popup.show();//showing popup menu
            }
        });

        return vista;
    }

    private iPersonaHandler getPersonaHandler() {
        return new PersonaHandler();
    }

    private iVisitaHandler getVisitaHandler() {
        return new VisitaHandler();
    }

    protected List<Persona> getPersonas() {
        return PersonaDataAccess.get().getAllOK();
    }

    @Override
    public void onAttach(Activity activity) { //No anda el onAttach(Context context) can API < 23
        super.onAttach(activity);
        fragmentChanger = (iFragmentChanger) activity;
        ((MainActivity)activity).getAppbar().setTitle(Utils.LISTA_PERSONAS);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentChanger = null;
        ((MainActivity)getActivity()).getAppbar().setTitle(Utils.HOME);
    }
}
