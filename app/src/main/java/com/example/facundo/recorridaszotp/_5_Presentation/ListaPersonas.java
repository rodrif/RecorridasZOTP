package com.example.facundo.recorridaszotp._5_Presentation;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
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

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
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

        navDrawerLayout.setDrawerListener(mDrawerToggle);
        toolbar.setTitle("Titulo toolbar");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                //  MostrarFragment(position);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                navDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}
