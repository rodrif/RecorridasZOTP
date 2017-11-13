package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._1_Red.Notificaciones.RegistrationIntentService;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._0_Infraestructure.AdaptadorListaMenu;
import com.example.facundo.recorridaszotp._1_Red.ObtenerToken;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
import com.example.facundo.recorridaszotp._2_DataAccess.ZonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Filtros;
import com.example.facundo.recorridaszotp._3_Domain.ItemLista;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.example.facundo.recorridaszotp._3_Domain.Zona;
import com.example.facundo.recorridaszotp._5_Presentation.UISaver.PersonaSaver;
import com.example.facundo.recorridaszotp._5_Presentation.UISaver.VisitaSaver;
import com.example.facundo.recorridaszotp._7_Interfaces.iFragmentChanger;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements iFragmentChanger {

    private DrawerLayout navDrawerLayout;
    private ListView navList;
    private Toolbar appbar;
    private ArrayList<ItemLista> navItms;
    AdaptadorListaMenu navAdapter;
    public static Persona personaSeleccionada = null;
    public static Visita visitaSeleccionada = null;
    public static boolean versionError = false;
    private static Menu menu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(Utils.PREFS_NAME, MODE_PRIVATE);
        Config.getInstance().setIsLoginOk(settings.getBoolean(Utils.USER_IS_LOGIN, false));
        Config.getInstance().setUserMail(settings.getString(Utils.USER_EMAIL, ""));
        Config.getInstance().setUserPassword(settings.getString(Utils.USER_PASSWORD, ""));
        Config.getInstance().setRol(settings.getInt(Utils.USER_ROL_ID, -1));
        //No hace falta el area ya que el area se guarda en la tabla Configuracion
        setContentView(R.layout.activity_main);
        appbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(appbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer2);
        getSupportActionBar().setHomeActionContentDescription("toolbarMenu");
        getSupportActionBar().setTitle(Utils.HOME);

        //Drawer layout
        navDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navList = (ListView) findViewById(R.id.nav_list);
        //Declaramos el header el cual sera el layout de header.xml
        View header = getLayoutInflater().inflate(R.layout.header, null);
        //Establecemos header
        navList.addHeaderView(header);
        navList.setOnItemClickListener(new AdaptadorMenuLateral(this));
        navItms = new ArrayList<ItemLista>();
        navItms.add(new ItemLista("Personas", R.drawable.ic_people_white_36dp));
        navItms.add(new ItemLista("Nueva Persona", R.drawable.ic_person_add_white_24dp));
        navItms.add(new ItemLista("Ultimas Visitas", R.drawable.ic_directions_walk_white_36dp));
        navItms.add(new ItemLista("Mapa", R.drawable.ic_map_white_36dp));
        navItms.add(new ItemLista("Filtros", R.drawable.ic_filter_list_white_36dp));
        navItms.add(new ItemLista("Salir", R.drawable.ic_highlight_off_white_36dp));
        navAdapter = new AdaptadorListaMenu(this, navItms);
        navList.setAdapter(navAdapter);
        if(Config.getInstance().isLoginOk()) {
            enableSideMenu();
            this.replaceFragment(new HomeFragment(),false, null);
            new ObtenerToken(null).execute();
            Bundle bundleNotificacion = getIntent().getExtras();
            if(bundleNotificacion != null) {
                String cod = (String) bundleNotificacion.get(Utils.CODIGO_NOTIFICACION);
                if (cod != null && !cod.equalsIgnoreCase("")) {
                    this.replaceFragment(new NotificationFragment(), true, bundleNotificacion);
                }
            }
        } else {
            this.replaceFragment(new LoginFragment(), false, null);
        }
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack, Bundle bundle) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (addToBackStack) {
            ft.addToBackStack(fragment.toString());
        }
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        ft.replace(R.id.content_frame, fragment, fragment.toString());
        ft.commit();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MainActivity.menu = menu;
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MainActivity.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuGuardar(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        super.onOptionsItemSelected(item);
        return MenuSuperior.seleccionar(item, this);
    }

    public void GuardarPedidoClickFormulario() {
        Log.d(Utils.APPTAG, "GuardarPedidoClickFormulario");
        PedidoFragment pedidoFragment = (PedidoFragment)getFragmentManager().findFragmentById(R.id.content_frame);
        pedidoFragment.savePedido();
    }

    public void GuardarVisitaClickFormulario() {
        Log.d(Utils.APPTAG, "GuardarVisitaClickFormulario");
        VisitaSaver.save(this);
    }

    public void GuardarPersonaClickFormulario() {
        Log.d(Utils.APPTAG, "GuardarPersonaClickFormulario");
        PersonaSaver.save(this);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void changeFragment(Fragment frag, String fragLabel, boolean addToBackStack) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (addToBackStack) {
            ft.addToBackStack(fragLabel);
        }
        ft.replace(R.id.content_frame, frag, fragLabel);
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
        this.savePreferences();
    }

    private void savePreferences() {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(Utils.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Utils.USER_EMAIL, Config.getInstance().getUserMail());
        editor.putString(Utils.USER_PASSWORD, Config.getInstance().getUserPassword());
        editor.putBoolean(Utils.USER_IS_LOGIN, Config.getInstance().isLoginOk());
        editor.putInt(Utils.USER_ROL_ID, Config.getInstance().getRol());

        // Commit the edits!
        editor.commit();
    }

    public static void menuGuardar(boolean boolGuardar) {
        menuGuardar(boolGuardar, false, false);
    }

    public static void menuGuardar(boolean boolGuardar, boolean boolCompartir, boolean boolPedidos) {
        if (menu != null) {
            menu.setGroupVisible(R.id.grupo_guardar_persona, boolGuardar);
            menu.setGroupVisible(R.id.grupo_compartir, boolCompartir);
            menu.setGroupVisible(R.id.grupo_pedidos, boolPedidos);
        }
    }

    public static void clean() {
        personaSeleccionada = null;
        visitaSeleccionada = null;
        Config.getInstance().setIsEditing(false);
    }

    public Toolbar getAppbar() {
        return appbar;
    }

    public void onClickLogin(View view) {
        View vista = getFragmentManager().findFragmentById(R.id.content_frame).getView();
        EditText ETEmail = (EditText) vista.findViewById(R.id.ETEmail);
        EditText ETPassword = (EditText) vista.findViewById(R.id.ETPassword);
        Config.getInstance().setUserMail(ETEmail.getText().toString());
        Config.getInstance().setUserPassword(ETPassword.getText().toString());
        new ObtenerToken(this).execute();
    }

    public void onFiltroSave(View view) {
        View vista = getFragmentManager().findFragmentById(R.id.content_frame).getView();
        Spinner sZona = (Spinner) vista.findViewById(R.id.spinner_filtroZona);
        long zonaId = -1;
        if (sZona.getSelectedItemId() != 0) {
            String zonaString = (String) sZona.getSelectedItem();
            Zona unaZona = ZonaDataAccess.get().find(zonaString);
            zonaId = unaZona.getId();
        }
        Filtros filtros = Filtros.get();
        filtros.setZonaId(zonaId);
        filtros.save();
        clearAllFragments();
    }

    public void loginOk() {
        if (Config.getInstance().getRol() == Utils.ROL_INVITADO) {
            Log.e(Utils.APPTAG, "Rol invitado, sin acceso");
            Toast.makeText(this, "Sin acceso, pida acceso al administrador", Toast.LENGTH_LONG).show();
        } else {
            this.enableSideMenu();
            Config.getInstance().setLoginOk();
            savePreferences();
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment homeFragment = new HomeFragment();
            ft.replace(R.id.content_frame, homeFragment, Utils.FRAG_HOME);
            ft.commit();
        }

    }

    public DrawerLayout getNavDrawerLayout() {
        return navDrawerLayout;
    }

    private void enableSideMenu() {
        navList = (ListView) findViewById(R.id.nav_list);
        navList.setOnItemClickListener(new AdaptadorMenuLateral(this));
        navList.setEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void disableSideMenu() {
        navList = (ListView) findViewById(R.id.nav_list);
        navList.setEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public void clearAllFragments() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
