package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
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
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._0_Infraestructure.ZonaDrawer;
import com.example.facundo.recorridaszotp._0_Infraestructure.popUp;
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

import java.util.Calendar;

public class VisitaFragment extends Fragment implements OnMapReadyCallback, popUp {
    private static View vista;
    private EditText etFecha = null;
    private EditText etObservaciones = null;
    private ImageButton ibFecha = null;
    private double latitud = Double.NaN;
    private double longitud = Double.NaN;
    private MapFragment mapFragmentVisita = null;
    private Marker marker = null;
    private boolean locationCargada = false;
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

        mapFragmentVisita = (MapFragment) (getFragmentManager().findFragmentById(R.id.mapVisita));
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
        actualizar();
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

    private void setMapListeners(final GoogleMap googleMap) {
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
                    marker = mapFragmentVisita.getMap().addMarker(new MarkerOptions().position(new LatLng(
                            latLng.latitude, latLng.longitude)));
                    MainActivity.visitaSeleccionada.setUbicacion(marker.getPosition());
                    Log.v(Utils.APPTAG, "lat: " + marker.getPosition().toString().toString());
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
        this.setMapListeners(googleMap);
        if (MainActivity.visitaSeleccionada != null) {
            if (MainActivity.visitaSeleccionada.getUbicacion() != null) {
                marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(
                        MainActivity.visitaSeleccionada.getUbicacion().latitude,
                        MainActivity.visitaSeleccionada.getUbicacion().longitude)));
                centrarMapa(MainActivity.visitaSeleccionada.getUbicacion());
            } else {
                Log.d(Utils.APPTAG, "VisitaFragment::onMapReady" +
                        " MainActivity.visitaSeleccionada.getUbicacion() es null");
                marker = googleMap.addMarker(new MarkerOptions().position(
                            getDefaultUbicacion()));
                MainActivity.visitaSeleccionada.setUbicacion(marker.getPosition());
                centrarMapa(getDefaultUbicacion());
            }
        } else {

        }
        ZonaDrawer.draw(googleMap, MainActivity.visitaSeleccionada.getPersona().getZona().toString());
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