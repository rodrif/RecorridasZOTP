package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Notificaciones.RegistrationIntentService;
import com.example.facundo.recorridaszotp._1_Red.Sincronizador;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Roles;
import com.example.facundo.recorridaszotp._3_Domain.Visita;

/**
 * Created by gonzalo on 08/11/17.
 */
public class AdaptadorMenuLateral implements AdapterView.OnItemClickListener {
    private MainActivity activity = null;

    public AdaptadorMenuLateral(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
        if (position == 0) {
            this.activity.getNavDrawerLayout().closeDrawers();
            return;
        }
        boolean fragmentTransaction = false;
        Fragment fragment = null;
        String tag = "";

        this.activity.clean();
        //Borra todos los fragments al hacer click en menu lateral
        this.activity.clearAllFragments();

        //FIXME refactor de replaceFragment
        switch (position) {
            case 1: //Personas
                fragment = new ListaPersonas();
                fragmentTransaction = true;
                tag = Utils.LISTA_PERSONAS;
                break;
            case 2: //Crear Persona
                if (Roles.getInstance().hasPermission(Utils.PUEDE_CREAR_PERSONA)) {
                    this.activity.personaSeleccionada = new Persona();
                    this.activity.visitaSeleccionada = new Visita(this.activity.personaSeleccionada);
                    fragment = new PersonaFragment();
                    Config.getInstance().setIsEditing(false);
                    fragmentTransaction = true;
                    tag = Utils.FRAG_PERSONA;
                } else {
                    Toast.makeText(this.activity.getApplicationContext(),
                            "No tiene permisos para crear personas", Toast.LENGTH_SHORT).show();
                }
                break;
            case 3: //Ultimas Visitas
                fragment = new ListaVisitas();
                fragmentTransaction = true;
                tag = Utils.FRAG_VISITA;
                break;
            case 4: //Mapa
                fragment = new MapaFragment();
                fragmentTransaction = true;
                tag = Utils.FRAG_MAPA;
                break;
            case 5: //Filtros
                fragment = new FiltrosFragment();
                fragmentTransaction = true;
                tag = Utils.FRAG_FILTROS;
                break;
            case 6: //Salir
                fragment = new LoginFragment();
                fragmentTransaction = true;
                tag = Utils.FRAG_LOGIN; //TODO refactor
                Config.getInstance().logOut();
                // Unsuscribe for notifications
                Intent intent = new Intent(this.activity, RegistrationIntentService.class);
                this.activity.startService(intent);
                this.activity.clearAllFragments();
                this.activity.disableSideMenu();
                Toast.makeText(this.activity.getApplicationContext(),
                        "Saliendo...", Toast.LENGTH_SHORT).show();
                break;
        }

        Sincronizador sinc = new Sincronizador(this.activity, false);
        sinc.execute();

        if (fragmentTransaction) {
            FragmentTransaction ft = this.activity.getFragmentManager().beginTransaction();
            if (tag != Utils.FRAG_LOGIN) {
                ft.addToBackStack(tag);
            }
            ft.replace(R.id.content_frame, fragment, tag);
            ft.commit();
        }
        this.activity.getNavDrawerLayout().closeDrawers();
    }
}