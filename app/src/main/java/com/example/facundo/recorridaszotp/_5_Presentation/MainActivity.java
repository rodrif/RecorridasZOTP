package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._0_Infraestructure.AdaptadorListaMenu;
import com.example.facundo.recorridaszotp._0_Infraestructure.onSelectedItemListener;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.ItemLista;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Query.PersonaQuery;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements onSelectedItemListener {
    private DrawerLayout navDrawerLayout;
    private ListView navList;
    private Toolbar appbar;
    private ArrayList<ItemLista> navItms;
    AdaptadorListaMenu navAdapter;
    private Persona personaSeleccionada = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Drawer layout
        navDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navList = (ListView) findViewById(R.id.nav_list);
        //Declaramos el header el caul sera el layout de header.xml
        View header = getLayoutInflater().inflate(R.layout.header, null);
        //Establecemos header
        navList.addHeaderView(header);

        navItms = new ArrayList<ItemLista>();
        navItms.add(new ItemLista("Fragment 1", R.drawable.abc_ic_menu_cut_mtrl_alpha));
        navItms.add(new ItemLista("Personas", R.drawable.ic_action_user));
        navItms.add(new ItemLista("Mapa", R.drawable.ic_action_place));
        navItms.add(new ItemLista("Perfil", R.drawable.abc_ic_menu_share_mtrl_alpha));
        navItms.add(new ItemLista("Formulario", R.drawable.abc_ic_voice_search_api_mtrl_alpha));
        navItms.add(new ItemLista("Sincronizar", R.drawable.cast_ic_notification_connecting));
        navItms.add(new ItemLista("Mostrar Persona", R.drawable.ic_action_user));
        navAdapter = new AdaptadorListaMenu(this, navItms);
        navList.setAdapter(navAdapter);
        setSupportActionBar(appbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navList = (ListView) findViewById(R.id.nav_list);
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                //  MostrarFragment(position);
                boolean fragmentTransaction = false;
                Fragment fragment = null;

                switch (position) {
                    case 1:
                        fragment = new Fragment1();
                        fragmentTransaction = true;
                        break;
                    case 2:
                        fragment = new ListaPersonas();
                        fragmentTransaction = true;
                        break;
                    case 3:
                        fragment = new MapsFragment();
                        fragmentTransaction = true;
                        break;
                    case 4:
                        fragment = new ProfileFragment();
                        fragmentTransaction = true;
                        break;
                    case 5:
                        fragment = new FormularioFragment();
                        fragmentTransaction = true;
                        break;
                    case 6:
                        PersonaDataAccess.sincronizar(null);
                        Toast.makeText(getApplicationContext(),
                                "Sincronizando...", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        fragment = new MostrarPersonaFragment();
                        fragmentTransaction = true;
                        break;
                }

                if (fragmentTransaction) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }

                navDrawerLayout.closeDrawers();

            }
        });

        Fragment fragmentHome = new HomeFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragmentHome);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                navDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void GuardarPersonaClickFormulario(View v) {
        Log.d(Utils.APPTAG, "GuardarPersonaClickFormulario");

        EditText ETnombre = (EditText) getFragmentManager()
                .findFragmentById(R.id.content_frame).getView().findViewById(R.id.ETNombre);
        EditText ETapellido = (EditText) getFragmentManager()
                .findFragmentById(R.id.content_frame).getView().findViewById(R.id.ETApellido);

        String nombre = ETnombre.getText().toString();
        String apellido = ETapellido.getText().toString();

        if (!nombre.equals("")) {
            if (personaSeleccionada != null) {// si habia persona seleccionada
                personaSeleccionada.setNombre(nombre);
                personaSeleccionada.setApellido(apellido);
                personaSeleccionada.setEstado(Utils.EST_MODIFICADO);
                PersonaDataAccess.save(personaSeleccionada);
            } else { // persona nueva
                Persona persona = new Persona(nombre, apellido, Utils.EST_NUEVO);
                PersonaDataAccess.save(persona);
            }
        } else {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            ETnombre.requestFocus();
            ETnombre.startAnimation(shake);
            ETnombre.setError("El nombre es obligatorio");
            //Toast.makeText(this, "Nombre es obligatorio", Toast.LENGTH_SHORT).show();
        }

        //Chequea creacion correcta
        PersonaQuery query = new PersonaQuery();
        query.nombre = nombre;

        Persona p = PersonaDataAccess.find(query);
        Toast unToast = Toast.makeText(this, " ", Toast.LENGTH_SHORT);
        if (p == null) {
            unToast.setText("Error al grabar");
        } else {
            unToast.setText("Se grabo: " + p.getNombre());
        }
        unToast.show();
    }

    @Override
    public void mostrarPersona(Persona persona) {
        personaSeleccionada = persona;
        Fragment frag = new MostrarPersonaFragment();

        Bundle args = new Bundle();
        args.putString("nombre", persona.getNombre());
        args.putString("apellido", persona.getApellido());
        frag.setArguments(args);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, frag);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
