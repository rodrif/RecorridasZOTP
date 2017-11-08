package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.DatePickerFragment;
import com.example.facundo.recorridaszotp._0_Infraestructure.Geolocalizador;
import com.example.facundo.recorridaszotp._0_Infraestructure.GeolocalizadorInverso;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._0_Infraestructure.ZonaDrawer;
import com.example.facundo.recorridaszotp._0_Infraestructure.ZonaPersonaListener;
import com.example.facundo.recorridaszotp._0_Infraestructure.popUp;
import com.example.facundo.recorridaszotp._1_Red.Sincronizador;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
import com.example.facundo.recorridaszotp._2_DataAccess.FamiliaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.RanchadaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.ZonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Familia;
import com.example.facundo.recorridaszotp._3_Domain.Ranchada;
import com.example.facundo.recorridaszotp._3_Domain.Roles;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.example.facundo.recorridaszotp._3_Domain.Zona;
import com.example.facundo.recorridaszotp._7_Interfaces.iMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PersonaFragment extends Fragment implements OnMapReadyCallback, popUp, iMapFragment {
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private final int RESULT_OK = -1;
    private static View vista;
    private EditText etFechaNacimiento = null;
    private EditText etNombre = null;
    private EditText etApellido = null;
    private EditText etObservaciones;
    private EditText etDNI;
    private EditText etTelefono;
    private ImageButton ibSpeak = null;
    private Button bSearch = null;
    private Spinner sZona = null;
    private EditText etPantalon = null;
    private EditText etRemera = null;
    private EditText etZapatillas = null;
    private EditText etUbicacion = null;
    private ImageButton bFechaNacimiento = null;
    private MapFragment mapFragmentPersona = null;
    private ArrayAdapter<String> adaptadorFamilia = null;
    ArrayAdapter<String> adaptadorZona = null;
    private ArrayAdapter<String> adaptadorRanchada = null;
    private LatLng geocoderLatLng = null;
    AlertDialog.Builder dialogoBorrar = null;
    private List<View> viewDatos = new ArrayList<View>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        View v = inflater.inflate(R.layout.fragment_persona, container, false);
        if (vista != null) {
            ViewGroup parent = (ViewGroup) vista.getParent();
            if (parent != null)
                parent.removeView(vista);
        }
        try {
            vista = inflater.inflate(R.layout.fragment_persona, container, false);
        } catch (InflateException e) {

        }

        ImageButton ib = (ImageButton) vista.findViewById(R.id.bFechaNacimiento);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPicker();
            }
        });

        etNombre = (EditText) vista.findViewById(R.id.ETNombre);
        viewDatos.add(etNombre);
        etApellido = (EditText) vista.findViewById(R.id.ETApellido);
        viewDatos.add(etApellido);
        etFechaNacimiento = (EditText) vista.findViewById(R.id.ETFechaNacimiento);
        viewDatos.add(etFechaNacimiento);
        etPantalon = (EditText) vista.findViewById(R.id.ETPantalon);
        viewDatos.add(etPantalon);
        etRemera = (EditText) vista.findViewById(R.id.ETRemera);
        viewDatos.add(etRemera);
        etZapatillas = (EditText) vista.findViewById(R.id.ETZapatillas);
        viewDatos.add(etZapatillas);
        etObservaciones = (EditText) vista.findViewById(R.id.ETObservaciones);
        viewDatos.add(etObservaciones);
        ibSpeak = (ImageButton) vista.findViewById(R.id.buttonSpeakPerson);
        viewDatos.add(ibSpeak);
        bSearch = (Button) vista.findViewById(R.id.buttonSearch);
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchClick(etUbicacion.getText().toString());
            }
        });
        etDNI = (EditText) vista.findViewById(R.id.ETDni);
        viewDatos.add(etDNI);
        etTelefono = (EditText) vista.findViewById(R.id.ETTelefono);
        viewDatos.add(etTelefono);
        etUbicacion = (EditText) vista.findViewById(R.id.ETUbicacion);
        viewDatos.add(etUbicacion);
        etUbicacion.setText("");
        bFechaNacimiento = (ImageButton)vista.findViewById(R.id.bFechaNacimiento);
        viewDatos.add(bFechaNacimiento);

        //Para compatibilidad
        mapFragmentPersona = (MapFragment) (getChildFragmentManager().findFragmentById(R.id.mapPersona));
        if (mapFragmentPersona == null) {
            mapFragmentPersona = (MapFragment) (getFragmentManager().findFragmentById(R.id.mapPersona));
        }
        mapFragmentPersona.getMapAsync(this);

        ibSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        //Zona
        sZona = (Spinner) vista.findViewById(R.id.spinner_zona);
        viewDatos.add(sZona);
        final List<Zona> lZonas = ZonaDataAccess.get().getAllOKFiltradoPorArea();
        final List<String> zonasString = new ArrayList<String>();
        zonasString.add("Zona");
        for (Zona zona : lZonas) {
            zonasString.add(zona.getNombre());
        }
        adaptadorZona = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, zonasString);
        adaptadorZona.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        sZona.setAdapter(adaptadorZona);
        actualizarZona(); //Para actualizar sZona.getSelectedItem()
        actualizar();
        dialogoBorrar = new AlertDialog.Builder(

                getActivity()

        );
        dialogoBorrar.setTitle("Importante");
        dialogoBorrar.setMessage("¿ Seguro quiere eliminar ?");
        dialogoBorrar.setCancelable(false);
        dialogoBorrar.setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialogo1, int id) {
                    if(MainActivity.personaSeleccionada.getId() != null) {
                        PersonaDataAccess.get().deleteLogico(MainActivity.personaSeleccionada);
                        if (MainActivity.personaSeleccionada.getEstado() == Utils.EST_BORRADO) {
                            Toast.makeText(getActivity(),
                                    "Se elimino a " + MainActivity.personaSeleccionada.getNombre()
                                            + " exitosamente", Toast.LENGTH_SHORT).show();
                            Sincronizador sinc = new Sincronizador(getActivity(), false);
                            sinc.execute();
                            MainActivity.clean();
                            getActivity().onBackPressed();
                            getActivity().onBackPressed();
                        } else {
                            Log.e(Utils.APPTAG, "Error al hacer borrado logico de personaId: " +
                                MainActivity.personaSeleccionada.getId() +
                                "en PersonaFragment::PositiveButton::onClick");
                        }
                    } else {
                        Toast.makeText(getActivity(),
                            "No se puede borrar una persona no creada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        );
        dialogoBorrar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()

                {
                    public void onClick(DialogInterface dialogo1, int id) {
                        // cancelar();
                    }
                }

        );
        this.setMenu();
        return vista;
    }

    public void onSearchClick(String direccion) {
        Log.d(Utils.APPTAG, "onSearchClick" + direccion);
        new Geolocalizador(direccion, getActivity(), this).execute();
    }

    private void showDataPicker() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), Utils.DATE_PICKER_PERSONA);
    }

    public void cargarFecha(int day, int month, int year) {
        etFechaNacimiento.setText(Integer.toString(day) + "/" +
                Integer.toString(month) + "/" + Integer.toString(year));
    }

    public void actualizar() {
        if (Config.getInstance().isEditing()) {
            bSearch.setVisibility(View.GONE);
            etUbicacion.setEnabled(false);
        } else {
            bSearch.setVisibility(View.VISIBLE);
            etUbicacion.setEnabled(true);
        }
        if (etNombre != null) {
            if (MainActivity.personaSeleccionada != null) {
                if (MainActivity.personaSeleccionada.getNombre() != null)
                    etNombre.setText(MainActivity.personaSeleccionada.getNombre());
                else
                    etNombre.setText("");

                if (MainActivity.personaSeleccionada.getApellido() != null)
                    etApellido.setText(MainActivity.personaSeleccionada.getApellido());
                else
                    etApellido.setText("");

                if (MainActivity.personaSeleccionada.getObservaciones() != null)
                    etObservaciones.setText(MainActivity.personaSeleccionada.getObservaciones());
                else
                    etObservaciones.setText("");

                if (MainActivity.personaSeleccionada.getDNI() != null)
                    etDNI.setText(MainActivity.personaSeleccionada.getDNI());
                else
                    etDNI.setText("");

                if (MainActivity.personaSeleccionada.getRemera() != null)
                    etRemera.setText(MainActivity.personaSeleccionada.getRemera());
                else
                    etRemera.setText("");

                if (MainActivity.personaSeleccionada.getPantalon() != null)
                    etPantalon.setText(MainActivity.personaSeleccionada.getPantalon());
                else
                    etPantalon.setText("");

                if (MainActivity.personaSeleccionada.getZapatillas() != null)
                    etZapatillas.setText(MainActivity.personaSeleccionada.getZapatillas());
                else
                    etZapatillas.setText("");
                actualizarZona();
                if (MainActivity.personaSeleccionada.getFechaNacimiento() != null) {
                    etFechaNacimiento.setText(MainActivity.personaSeleccionada.getFechaNacimientoMostrar());
                } else {
                    etFechaNacimiento.setText("");
                }

                if (MainActivity.personaSeleccionada.getTelefono() != null) {
                    etTelefono.setText(MainActivity.personaSeleccionada.getTelefono());
                } else {
                    etTelefono.setText("");
                }
                this.aplicarPermisos();
            }
        }
    }

    private void aplicarPermisos() {
        boolean editable = !Config.getInstance().isEditing()
                || Roles.getInstance().hasPermission(Utils.PUEDE_EDITAR_PERSONA);
        int visibilidad = Roles.getInstance().hasPermission(Utils.PUEDE_VER_DATOS_PERSONALES) || editable
                ? View.VISIBLE
                : View.GONE;
        for (View vista : viewDatos) {
            vista.setVisibility(visibilidad);
            vista.setEnabled(editable);
        }
        etNombre.setVisibility(View.VISIBLE);
        etApellido.setVisibility(View.VISIBLE);
        etUbicacion.setVisibility(View.VISIBLE);
        sZona.setVisibility(View.VISIBLE);
    }

    private void actualizarZona() {
        if (MainActivity.personaSeleccionada.getZona() != null) {
            sZona.setSelection(adaptadorZona.getPosition(
                    MainActivity.personaSeleccionada.getZona().getNombre()));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.clear();
        LatLng ubicacion = null;
        ZonaDrawer.draw(googleMap, this.sZona.getSelectedItem().toString());
        if (this.geocoderLatLng != null) {
            ubicacion = this.geocoderLatLng;
            this.geocoderLatLng = null;
        } else {
            Visita visita = VisitaDataAccess.get().findUltimaVisita(MainActivity.personaSeleccionada);
            if (visita != null && visita.getUbicacion() != null) {
                ubicacion = visita.getUbicacion();
            }
        }
        centrarMapa(googleMap, ubicacion);
        if (ubicacion != null) {
            googleMap.addMarker(new MarkerOptions().position(ubicacion));
            new GeolocalizadorInverso(this.etUbicacion, ubicacion, getActivity()).execute();
        }
        this.setMapListeners(googleMap, this.etUbicacion);

        if (sZona.getOnItemSelectedListener() != null) {
            ZonaDrawer.centrarEnZona(googleMap, sZona.getSelectedItem().toString());
        } else {
            //Para que no se llame al iniciar el fragment
            sZona.setOnItemSelectedListener(new ZonaPersonaListener(this));
        }
    }

    private void setMapListeners(final GoogleMap googleMap, final EditText etUbicacion) {
        try {
            googleMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            Log.e(Utils.APPTAG, "My location enabled security exception");
        }

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (!Config.getInstance().isEditing()) {
                    GeolocalizadorInverso geolocalizadorInverso = new GeolocalizadorInverso(etUbicacion, latLng, getActivity());
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(latLng));
                    MainActivity.visitaSeleccionada.setUbicacion(latLng);
                    geolocalizadorInverso.execute();
                }
            }
        });
    }

    private LatLng getDefaultUbicacion() {
    //FIXME getDefaultUbicacion() se podria obtener del Area o Zona
        return new LatLng(-34.5803526,-58.4360354);
    }

    private void centrarMapa(GoogleMap googleMap, LatLng ubicacion) {
        if (ubicacion == null) {
            ubicacion = getDefaultUbicacion();
        }
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(ubicacion, Utils.ZOOM_STANDAR));
    }

    private void centrarMapa(GoogleMap googleMap,Location myLocation) {
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(),
                myLocation.getLongitude()), Utils.ZOOM_STANDAR));
    }

    @Override
    public void onDetach() {
        //Para que no se llame al iniciar el fragment
        sZona.setOnItemSelectedListener(null);
        super.onDetach();
    }

    @Override
    public void popUp() {
        if (Roles.getInstance().hasPermission(Utils.PUEDE_BORRAR_PERSONA)) {
            if (dialogoBorrar != null)
                dialogoBorrar.show();
        } else {
            Toast unToast = Toast.makeText(getActivity(), "No tiene permisos para borrar a esta persona", Toast.LENGTH_SHORT);
            unToast.show();
        }
    }

    private void setMenu() {
        ((MainActivity)getActivity()).getAppbar().setTitle(Utils.PERSONA);
        if (MainActivity.personaSeleccionada.getWebId() != -1) {
            MainActivity.menuGuardar(true, true, true);
        } else {
            MainActivity.menuGuardar(true);
        }
    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Escuchando...");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(this.getActivity(),
                    "Speech not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (this.etObservaciones != null) {
                        this.etObservaciones.setText(result.get(0));
                    }
                }
                break;
            }
        }
    }

    @Override
    public String toString() {
        return Utils.FRAG_PERSONA;
    }

    public ArrayAdapter<String> getAdaptadorRanchada() {
        return adaptadorRanchada;
    }

    public ArrayAdapter<String> getAdaptadorFamilia() {
        return adaptadorFamilia;
    }

    public MapFragment getMapFragmentPersona() {
        return mapFragmentPersona;
    }

    public void setAdaptadorRanchada(ArrayAdapter<String> adaptadorRanchada) {
        this.adaptadorRanchada = adaptadorRanchada;
    }

    @Override
    public void setLatLng(LatLng latLng) {
        this.geocoderLatLng = latLng;
    }

    @Override
    public MapFragment getMapFragment() {
        return this.mapFragmentPersona;
    }
}
