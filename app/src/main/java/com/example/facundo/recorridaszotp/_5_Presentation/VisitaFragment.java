package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.DatePickerFragment;
import com.example.facundo.recorridaszotp._0_Infraestructure.Geolocalizador;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._0_Infraestructure.ZonaDrawer;
import com.example.facundo.recorridaszotp._0_Infraestructure.popUp;
import com.example.facundo.recorridaszotp._1_Red.Sincronizador;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Roles;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class VisitaFragment extends Fragment implements OnMapReadyCallback, popUp {
    private static View vista;
    private EditText etFecha = null;
    private EditText etObservaciones = null;
    private EditText etUbicacion = null;
    private ImageButton ibFecha = null;
    private ImageButton ibSpeak = null;
    private double latitud = Double.NaN;
    private double longitud = Double.NaN;
    private MapFragment mapFragmentVisita = null;
    private boolean locationCargada = false;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private final int RESULT_OK = -1;
    AlertDialog.Builder dialogoBorrar = null;
    MainActivity activity= null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (vista != null) {
            ViewGroup parent = (ViewGroup) vista.getParent();
            if (parent != null)
                parent.removeView(vista);
        }
        try {
            vista = inflater.inflate(R.layout.fragment_visita, container, false);
        } catch (InflateException e) {

        }
        //Para compatibilidad
        mapFragmentVisita = (MapFragment) (getChildFragmentManager().findFragmentById(R.id.mapVisita));
        if (mapFragmentVisita == null) {
            mapFragmentVisita = (MapFragment) (getFragmentManager().findFragmentById(R.id.mapVisita));
        }
        mapFragmentVisita.getMapAsync(this);

        ibFecha = (ImageButton) vista.findViewById(R.id.bFecha);
        ibFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPicker();
            }
        });

        etFecha = (EditText) vista.findViewById(R.id.ETFecha);
        etObservaciones = (EditText) vista.findViewById(R.id.ETObservacioneVisita);
        etUbicacion = (EditText) vista.findViewById(R.id.ETUbicacion);
        ibSpeak = (ImageButton) vista.findViewById(R.id.buttonSpeak);
        actualizar();
        ibSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        dialogoBorrar = new AlertDialog.Builder(getActivity());
        dialogoBorrar.setTitle("Importante");
        dialogoBorrar.setMessage("¿ Seguro quiere eliminar ?");
        dialogoBorrar.setCancelable(false);
        dialogoBorrar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                // cancelar();
            }
        });
        dialogoBorrar.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                if (MainActivity.visitaSeleccionada.getId() != null) {
                    VisitaDataAccess.get().deleteLogico(MainActivity.visitaSeleccionada);
                    if (MainActivity.visitaSeleccionada.getEstado() == Utils.EST_BORRADO) {
                        Toast.makeText(getActivity(),
                                "Se elimino la visita a " + MainActivity.visitaSeleccionada.
                                        getPersona().getNombre()
                                        + " exitosamente", Toast.LENGTH_SHORT).show();
                        Sincronizador sinc = new Sincronizador(getActivity(), false);
                        sinc.execute();
                        MainActivity.clean();
                        activity.onBackPressed();
                        activity.onBackPressed();
                    } else {
                        Log.e(Utils.APPTAG, "Error al hacer borrado logico de visitaId: " +
                                MainActivity.visitaSeleccionada.getId() +
                                "en VisitaFragment::PositiveButton::onClick");
                    }
                } else {
                    Toast.makeText(getActivity(),
                            "No se puede borrar una visita no creada", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return vista;
    }

    private void showDataPicker() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), Utils.DATE_PICKER_VISITA);
    }

    public void cargarFechaActual() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        this.cargarFecha(day, month, year);
    }

    public void cargarFecha(int day, int month, int year) {
        etFecha.setText(Integer.toString(day) + "/" +
                Integer.toString(month) + "/" + Integer.toString(year));
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void actualizar() {
        if (etFecha != null) {
            if (MainActivity.visitaSeleccionada.getFechaString() != null)
                etFecha.setText(MainActivity.visitaSeleccionada.getFechaString());
            else
                cargarFechaActual();

            if (MainActivity.visitaSeleccionada.getDescripcion() != null) {
                etObservaciones.setText(MainActivity.visitaSeleccionada.getDescripcion());
            }
            bloquearEdicion();
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
                if (!(Config.getInstance().isEditing()
                        && !Roles.getInstance().hasPermission(Utils.PUEDE_EDITAR_VISITA))) {
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(latLng));
                    new GeolocalizadorInverso(etUbicacion, latLng, activity).execute();
                    MainActivity.visitaSeleccionada.setUbicacion(latLng);
                    ZonaDrawer.draw(googleMap, MainActivity.visitaSeleccionada.getPersona().getZona().getNombre());
                    Log.v(Utils.APPTAG, "lat: " + latLng.toString());
                }
            }
        });

        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location myLocation) {
                centrarMapa(myLocation);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.clear();
        LatLng ubicacion = null;
        if (MainActivity.visitaSeleccionada != null) {
            if (MainActivity.visitaSeleccionada.getUbicacion() != null) {
                ubicacion = MainActivity.visitaSeleccionada.getUbicacion();
            } else {
                Log.d(Utils.APPTAG, "VisitaFragment::onMapReady" +
                        " MainActivity.visitaSeleccionada.getUbicacion() es null");
                ubicacion = getDefaultUbicacion();
                MainActivity.visitaSeleccionada.setUbicacion(ubicacion);
            }
            centrarMapa(ubicacion);
            googleMap.addMarker(new MarkerOptions().position(ubicacion));
            new GeolocalizadorInverso(this.etUbicacion, ubicacion, activity).execute();
            this.setMapListeners(googleMap, this.etUbicacion);
        } else {

        }
        ZonaDrawer.draw(googleMap, MainActivity.visitaSeleccionada.getPersona().getZona().getNombre());
    }

    private LatLng getDefaultUbicacion() {
        //FIXME getDefaultUbicacion() se podria obtener del Area o Zona
        return new LatLng(-34.6417109,-58.5651438);
    }

    private void centrarMapa(LatLng ubicacion) {
        if (!locationCargada) {
            mapFragmentVisita.getMap().animateCamera(
                    CameraUpdateFactory.newLatLngZoom(ubicacion, Utils.ZOOM_STANDAR));
            locationCargada = true;
        }
    }

    private void centrarMapa(Location myLocation) {
        if (!locationCargada) {
            mapFragmentVisita.getMap().animateCamera(
                    CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(),
                            myLocation.getLongitude()), Utils.ZOOM_STANDAR));
            locationCargada = true;
        }
    }

    private void bloquearEdicion() {
        if (Config.getInstance().isEditing()
                && !Roles.getInstance().hasPermission(Utils.PUEDE_EDITAR_VISITA)) {
            etFecha.setEnabled(false);
            etObservaciones.setEnabled(false);
            ibFecha.setEnabled(false);
        } else {
            etFecha.setEnabled(true);
            etObservaciones.setEnabled(true);
            ibFecha.setEnabled(true);
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
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Escuchando...");
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
    public void onAttach(Activity activity) { //No anda el onAttach(Context context) can API < 23
        super.onAttach(activity);
        ((MainActivity)activity).getAppbar().setTitle(Utils.VISITA);
        this.activity = (MainActivity)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity)getActivity()).getAppbar().setTitle(Utils.HOME);
    }

    @Override
    public void popUp() {
        if (Roles.getInstance().hasPermission(Utils.PUEDE_BORRAR_VISITA)) {
            if (dialogoBorrar != null)
                dialogoBorrar.show();
        } else {
            Toast unToast = Toast.makeText(this.activity, "No tiene permisos para borrar a esta visita", Toast.LENGTH_SHORT);
            unToast.show();
        }
    }

    @Override
    public String toString() {
        return Utils.FRAG_VISITA;
    }
}