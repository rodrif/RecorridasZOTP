package com.example.facundo.recorridaszotp._5_Presentation;

import android.content.ClipData;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class ListaPersonas extends ActionBarActivity {
    private DrawerLayout navDrawerLayout;
    private ListView navList;
    private ArrayList<ItemLista> navItms;
    AdaptadorListaMenu navAdapter;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personas);

        DBUtils.loadDefaultDB(); //TODO Borrar
        List<Persona> listaPersonas = PersonaDataAccess.getAll();

        AdaptadorListaPersonas adaptador =
                new AdaptadorListaPersonas(getApplicationContext(), listaPersonas);

        ListView lViewPersonas = (ListView) findViewById(R.id.listaPersonas);

        lViewPersonas.setAdapter(adaptador);
        lViewPersonas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> a, View view, int pos, long id) {
                Toast.makeText(getApplicationContext(),
                        "Long Click", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //Drawer layout
        navDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navList = (ListView) findViewById(R.id.lista);
        //Declaramos el header el caul sera el layout de header.xml
        View header = getLayoutInflater().inflate(R.layout.header, null);
        //Establecemos header
        navList.addHeaderView(header);

        navItms = new ArrayList<ItemLista>();
        navItms.add(new ItemLista("Mapa"));
        navItms.add(new ItemLista("Segundo elemento"));
        navAdapter = new AdaptadorListaMenu(this, navItms);
        navList.setAdapter(navAdapter);

        //Declaramos el mDrawerToggle y las imgs a utilizar
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                navDrawerLayout,         /* DrawerLayout object */
                               ///R.drawable.ic_drawer,  /* Icono de navegacion*/
                R.string.app_name,  /* "open drawer" description */
                R.string.hello_world  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                Log.e("Cerrado completo", "!!");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                Log.e("Apertura completa", "!!");
            }
        };

        // Establecemos que mDrawerToggle declarado anteriormente sea el DrawerListener
        navDrawerLayout.setDrawerListener(mDrawerToggle);
        //Establecemos que el ActionBar muestre el Boton Home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Establecemos la accion al clickear sobre cualquier item del menu.
        //De la misma forma que hariamos en una app comun con un listview.
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                //  MostrarFragment(position);
            }
        });
    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_personas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
