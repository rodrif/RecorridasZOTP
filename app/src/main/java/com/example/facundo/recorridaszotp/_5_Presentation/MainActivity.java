package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Handlers.PedidoHandler;
import com.example.facundo.recorridaszotp._0_Infraestructure.PersonaShare;
import com.example.facundo.recorridaszotp._1_Red.Notificaciones.RegistrationIntentService;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._0_Infraestructure.AdaptadorListaMenu;
import com.example.facundo.recorridaszotp._0_Infraestructure.popUp;
import com.example.facundo.recorridaszotp._1_Red.ObtenerToken;
import com.example.facundo.recorridaszotp._1_Red.Sincronizador;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.ItemLista;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Query.PersonaQuery;
import com.example.facundo.recorridaszotp._3_Domain.Roles;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.example.facundo.recorridaszotp._7_Interfaces.iFragmentChanger;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import io.fabric.sdk.android.Fabric;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        iFragmentChanger {
    //  MapsFragment.InterfaceMapa

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    /* Client used to interact with Google APIs. */
    public static GoogleApiClient mGoogleApiClient;

    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;

    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = true;

    //private String token;
    //private String email;
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
        navList.setOnItemClickListener(new AdaptadorOnItemClickListener(this));

        navItms = new ArrayList<ItemLista>();
        navItms.add(new ItemLista("Personas", R.drawable.ic_people_white_36dp));
        navItms.add(new ItemLista("Nueva Persona", R.drawable.ic_person_add_white_24dp));
        navItms.add(new ItemLista("Ultimas Visitas", R.drawable.ic_directions_walk_white_36dp));
        navItms.add(new ItemLista("Mapa", R.drawable.ic_map_white_36dp));
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
        // Build GoogleApiClient with access to basic profile
      /*  mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApiIfAvailable(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();*/
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
        switch (item.getItemId()) {
            case android.R.id.home:
                navDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_guardar: //Guardar
                //Busco que fragment se esta usando actualmente
                FragmentManager fm = getFragmentManager();
                int cant = fm.getBackStackEntryCount();
                FragmentManager.BackStackEntry bse = fm.getBackStackEntryAt(cant - 1);
                String tag = bse.getName();
                if (tag != null) {
                    switch (tag) {
                        case Utils.FRAG_PERSONA:
                            GuardarPersonaClickFormulario();
                            return true;
                        case Utils.FRAG_VISITA:
                            GuardarVisitaClickFormulario();
                            return true;
                        case Utils.FRAG_PEDIDO:
                            GuardarPedidoClickFormulario();
                            return true;
                    }
                }
            case R.id.action_borrar: //Borrar
                ((popUp) (getFragmentManager().findFragmentById(R.id.content_frame))).popUp();
                break;
            case R.id.action_compartir: //compartir
                if (personaSeleccionada != null) {
                    new PersonaShare(personaSeleccionada).share(this);
                }
                break;
            case R.id.action_pedidos: //pedidos
                long personaId = -1;
                if (personaSeleccionada != null) {
                    personaId = personaSeleccionada.getId();
                } else {
                    personaId = visitaSeleccionada.getPersona().getId();
                }
                new PedidoHandler().listarPedidos(personaId, this);
                break;
            case R.id.action_crear_pedido: //crear pedido
                Persona persona = null;
                if (personaSeleccionada != null) {
                    persona = personaSeleccionada;
                } else {
                    persona = visitaSeleccionada.getPersona();
                }
                new PedidoHandler().crearPedido(persona, this);
                break;
        }
        return true;
    }

    public void GuardarPedidoClickFormulario() {
        Log.d(Utils.APPTAG, "GuardarPedidoClickFormulario");
        PedidoFragment pedidoFragment = (PedidoFragment)getFragmentManager().findFragmentById(R.id.content_frame);
        pedidoFragment.savePedido();
    }

    public void GuardarVisitaClickFormulario() {
        Log.d(Utils.APPTAG, "GuardarVisitaClickFormulario");

        //Obtengo fecha y observaciones de la visita
        //La ubicacion se carga desde MapsFragment
        EditText eTFecha = (EditText) getFragmentManager()
                .findFragmentById(R.id.content_frame).getView().findViewById(R.id.ETFecha);
        EditText eTObservaciones = (EditText) getFragmentManager()
                .findFragmentById(R.id.content_frame).getView().findViewById(R.id.ETObservacioneVisita);

        if (visitaSeleccionada != null) {
            visitaSeleccionada.setFecha(eTFecha.getText().toString());
            visitaSeleccionada.setDescripcion(eTObservaciones.getText().toString());
            visitaSeleccionada.setEstado(Utils.EST_MODIFICADO);
            VisitaDataAccess.get().save(visitaSeleccionada);
            Toast unToast = Toast.makeText(this, "Visita a " + visitaSeleccionada.getPersona().getNombre()
                    + " guardada", Toast.LENGTH_SHORT);
            unToast.show();
            Answers.getInstance().logCustom(new CustomEvent("Visita Creada")
                    .putCustomAttribute("Nombre", visitaSeleccionada.getPersona().getNombre())
                    .putCustomAttribute("Apellido", visitaSeleccionada.getPersona().getApellido())
                    .putCustomAttribute("Zona", visitaSeleccionada.getPersona().getZona().getNombre())
                    .putCustomAttribute("Fecha", visitaSeleccionada.getFechaString())
                    .putCustomAttribute("Descripcion", visitaSeleccionada.getDescripcion()));
            Sincronizador sinc = new Sincronizador(this, false);
            sinc.execute();
        } else {
            Toast unToast = Toast.makeText(this, "Visita sin persona asociada", Toast.LENGTH_SHORT);
            unToast.show();
        }

        getFragmentManager().popBackStack(); //Si se guarda vuelve al fragment anterior
        getFragmentManager().popBackStack();
        clean();
    }

    public void GuardarPersonaClickFormulario() {
        Log.d(Utils.APPTAG, "GuardarPersonaClickFormulario");

        View vista = getFragmentManager().findFragmentById(R.id.content_frame).getView();

        EditText ETnombre = (EditText) vista.findViewById(R.id.ETNombre);
        EditText ETapellido = (EditText) vista.findViewById(R.id.ETApellido);
        EditText ETfechaNacimiento = (EditText) vista.findViewById(R.id.ETFechaNacimiento);
        EditText ETobservaciones = (EditText) vista.findViewById(R.id.ETObservaciones);
        EditText ETdni = (EditText) vista.findViewById(R.id.ETDni);
        EditText ETTelefono = (EditText) vista.findViewById(R.id.ETTelefono);
        EditText ETPantalon = (EditText) vista.findViewById(R.id.ETPantalon);
        EditText ETRemera = (EditText) vista.findViewById(R.id.ETRemera);
        EditText ETZapatillas = (EditText) vista.findViewById(R.id.ETZapatillas);
        Spinner sGrupoFamiliar = (Spinner) vista.findViewById(R.id.spinner_grupo_familiar);
        Spinner sZona = (Spinner) vista.findViewById(R.id.spinner_zona);
        Spinner sRanchada = (Spinner) vista.findViewById(R.id.spinner_ranchada);

        String nombre = ETnombre.getText().toString();
        String apellido = ETapellido.getText().toString();
        String dni = ETdni.getText().toString();
        String telefono = ETTelefono.getText().toString();
        String pantalon = ETPantalon.getText().toString();
        String remera = ETRemera.getText().toString();
        String zapatillas = ETZapatillas.getText().toString();
        String observaciones = ETobservaciones.getText().toString();
        String fechaNacimiento = ETfechaNacimiento.getText().toString();
        String grupoFamiliar = (String) sGrupoFamiliar.getSelectedItem();
        String zona = (String) sZona.getSelectedItem();
        String ranchada = (String) sRanchada.getSelectedItem();


        if (!nombre.equals("") && !zona.equals("Zona")) {
            if (personaSeleccionada != null) {// si habia persona seleccionada
                personaSeleccionada.setNombre(nombre);
                personaSeleccionada.setApellido(apellido);
                personaSeleccionada.setDNI(dni);
                personaSeleccionada.setTelefono(telefono);
                personaSeleccionada.setPantalon(pantalon);
                personaSeleccionada.setRemera(remera);
                personaSeleccionada.setZapatillas(zapatillas);
                personaSeleccionada.setObservaciones(observaciones);
                personaSeleccionada.setFechaNacimientoDesdeMob(fechaNacimiento);
                personaSeleccionada.setGrupoFamiliar(grupoFamiliar);
                personaSeleccionada.setZona(zona);
                personaSeleccionada.setRanchada(ranchada);
                personaSeleccionada.setEstado(Utils.EST_MODIFICADO);
                PersonaDataAccess.get().save(personaSeleccionada);
                if (visitaSeleccionada != null) {
                    visitaSeleccionada.setDescripcion("Primera Visita");
                    VisitaDataAccess.get().save(visitaSeleccionada);
                }

                //Chequea creación correcta
                PersonaQuery query = new PersonaQuery();
                query.nombre = nombre;

                Persona p = PersonaDataAccess.get().find(query);
                Toast unToast = Toast.makeText(this, " ", Toast.LENGTH_SHORT);
                if (p == null) {
                    unToast.setText("Error al grabar");
                } else {
                    unToast.setText("Se grabo: " + p.getNombre());
                }
                unToast.show();
                Answers.getInstance().logCustom(new CustomEvent("Persona Creada")
                        .putCustomAttribute("Nombre", personaSeleccionada.getNombre())
                        .putCustomAttribute("Apellido", personaSeleccionada.getApellido())
                        .putCustomAttribute("Zona", personaSeleccionada.getZona().getNombre()));
                Sincronizador sinc = new Sincronizador(this, false);
                sinc.execute();
                menuGuardar(false);
                getFragmentManager().popBackStack(); //Si se guarda vuelve al fragment anterior
                getFragmentManager().popBackStack();
                clean();
            }
        } else {
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            if (nombre.equals("")) {
                ETnombre.requestFocus();
                ETnombre.startAnimation(shake);
                ETnombre.setError("El nombre es obligatorio");
            } else {
                sZona.requestFocus();
                sZona.startAnimation(shake);
            }
        }
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

/*    public void signInClick(View v) {
        Toast.makeText(this,
                "Click en Sign in", Toast.LENGTH_SHORT).show();
        mGoogleApiClient.connect();
        Log.d("RZO", "Apretó SignIn");
    }*/

    private class AdaptadorOnItemClickListener implements AdapterView.OnItemClickListener {
        private Activity activity = null;

        public AdaptadorOnItemClickListener(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
            if (position == 0) {
                navDrawerLayout.closeDrawers();
                return;
            }
            boolean fragmentTransaction = false;
            Fragment fragment = null;
            String tag = "";

            clean();
            //Borra todos los fragments al hacer click en menu lateral
            FragmentManager fm = getFragmentManager();
            int cantidad = fm.getBackStackEntryCount();
            if (cantidad > 0) {
                fm.popBackStack(fm.getBackStackEntryAt(cantidad - 1).getId(),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            //FIXME refactor de replaceFragment
            switch (position) {
                case 1: //Personas
                    fragment = new ListaPersonas();
                    fragmentTransaction = true;
                    tag = Utils.LISTA_PERSONAS;
                    break;
                case 2: //Crear Persona
                    if (Roles.getInstance().hasPermission(Utils.PUEDE_CREAR_PERSONA)) {
                        personaSeleccionada = new Persona();
                        visitaSeleccionada = new Visita(personaSeleccionada);
                        fragment = new PersonaFragment();
                        Config.getInstance().setIsEditing(false);
                        fragmentTransaction = true;
                        tag = Utils.FRAG_PERSONA;
                    } else {
                        Toast.makeText(getApplicationContext(),
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
                case 5: //Salir
                    fragment = new LoginFragment();
                    fragmentTransaction = true;
                    tag = Utils.FRAG_LOGIN; //TODO refactor
                    Config.getInstance().logOut();
                    // Unsuscribe for notifications
                    Intent intent = new Intent(this.activity, RegistrationIntentService.class);
                    startService(intent);
                    clearAllFragments();
                    disableSideMenu();
                    Toast.makeText(getApplicationContext(),
                            "Saliendo...", Toast.LENGTH_SHORT).show();
                    break;
            }

            Sincronizador sinc = new Sincronizador(this.activity, false);
            sinc.execute();

            if (fragmentTransaction) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (tag != Utils.FRAG_LOGIN) {
                    ft.addToBackStack(tag);
                }
                ft.replace(R.id.content_frame, fragment, tag);
                ft.commit();
            }
            navDrawerLayout.closeDrawers();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
      //  ObtenerToken obtenerToken;
        Toast.makeText(this,
                "onConnected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,
                "onConnectedSuspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,
                "onConnectionFailed" + connectionResult, Toast.LENGTH_SHORT).show();
        Log.d("RZO", "onConnectionFailed:" + connectionResult);

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Toast.makeText(this,
                            "Could not resolve ConnectionResult" + e, Toast.LENGTH_SHORT).show();
                    Log.e("RZO", "Could not resolve ConnectionResult.", e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            }
        }
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

    public void loginOk() {
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

    private void enableSideMenu() {
        navList = (ListView) findViewById(R.id.nav_list);
        navList.setOnItemClickListener(new AdaptadorOnItemClickListener(this));
        navList.setEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void disableSideMenu() {
        navList = (ListView) findViewById(R.id.nav_list);
        navList.setEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void clearAllFragments() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
