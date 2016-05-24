package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
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
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._0_Infraestructure.onSelectedItemListener;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
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

        final List<Persona> listaPersonas = getPersonas();

        AdaptadorListaPersonas adaptador =
                new AdaptadorListaPersonas(getActivity().getApplicationContext(), listaPersonas);

        ListView lViewPersonas = (ListView) vista.findViewById(R.id.lista_personas);
        lViewPersonas.setAdapter(adaptador);
        lViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (clicklistener == null) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Listener null", Toast.LENGTH_SHORT).show();
                } else {
                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(getActivity().getApplicationContext(), view);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.crear_visita:
                                    Visita nuevaVisita = new Visita(listaPersonas.get(position));
                                    Visita ultimaVisita = VisitaDataAccess.get()
                                            .findUltimaVisita(listaPersonas.get(position));
                                    if (ultimaVisita != null)
                                        nuevaVisita.setUbicacion(ultimaVisita.getUbicacion());
                                    Config.getInstance().setIsEditing(false);
                                    clicklistener.mostrarVisita(nuevaVisita);
                                    break;
                                case R.id.editar_persona:
                                    if (clicklistener == null) {
                                        Toast.makeText(getActivity().getApplicationContext(),
                                                "Listener null", Toast.LENGTH_SHORT).show();
                                    } else {
                                        clicklistener.mostrarPersona(listaPersonas.get(position));
                                    }
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
            }
        });

        return vista;
    }

    protected List<Persona> getPersonas() {
        return PersonaDataAccess.get().getAllOK();
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
