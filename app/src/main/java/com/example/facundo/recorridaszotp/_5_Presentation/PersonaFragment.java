package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.DatePickerFragment;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._0_Infraestructure.ZonaPersonaListener;
import com.example.facundo.recorridaszotp._0_Infraestructure.popUp;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;


public class PersonaFragment extends Fragment implements OnMapReadyCallback, popUp {
    private static View vista;
    private EditText etFechaNacimiento = null;
    private EditText etNombre = null;
    private EditText etApellido = null;
    private EditText etObservaciones;
    private EditText etDNI;
    private EditText etTelefono;
    private Spinner sGrupoFamiliar = null;
    private Spinner sZona = null;
    private Spinner sRanchada = null;
    private ImageButton bFechaNacimiento = null;
    private MapFragment mapFragmentPersona = null;
    private Marker marker = null;
    private boolean locationCargada = false;
    private ArrayAdapter<String> adaptadorFamilia = null;
    ArrayAdapter<String> adaptadorZona = null;
    private ArrayAdapter<String> adaptadorRanchada = null;
    AlertDialog.Builder dialogoBorrar = null;
    MainActivity activity = null;

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

        mapFragmentPersona = (MapFragment) (getFragmentManager().findFragmentById(R.id.mapPersona));
        mapFragmentPersona.getMapAsync(this);

        ImageButton ib = (ImageButton) vista.findViewById(R.id.bFechaNacimiento);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPicker();
            }
        });

        etNombre = (EditText) vista.findViewById(R.id.ETNombre);
        etApellido = (EditText) vista.findViewById(R.id.ETApellido);
        etFechaNacimiento = (EditText) vista.findViewById(R.id.ETFechaNacimiento);
        etObservaciones = (EditText) vista.findViewById(R.id.ETObservaciones);
        etDNI = (EditText) vista.findViewById(R.id.ETDni);
        etTelefono = (EditText) vista.findViewById(R.id.ETTelefono);
        sRanchada = (Spinner) vista.findViewById(R.id.spinner_ranchada);
        bFechaNacimiento = (ImageButton)vista.findViewById(R.id.bFechaNacimiento);

        //Grupo Familiar
        sGrupoFamiliar = (Spinner) vista.findViewById(R.id.spinner_grupo_familiar);
        final List<Familia> lFamilias = FamiliaDataAccess.get().getAll();
        final List<String> familiasString = new ArrayList<String>();
        familiasString.add("Familia");
        for (Familia familia : lFamilias) {
            familiasString.add(familia.getNombre());
        }
        adaptadorFamilia = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, familiasString);
        adaptadorFamilia.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        sGrupoFamiliar.setAdapter(adaptadorFamilia);

        //Zona
        sZona = (Spinner) vista.findViewById(R.id.spinner_zona);
        final List<Zona> lZonas = ZonaDataAccess.get().getAll();
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
        sZona.setOnItemSelectedListener(new ZonaPersonaListener(this));
        //Ranchada
        Spinner sRanchada = (Spinner) vista.findViewById(R.id.spinner_ranchada);

        final List<Ranchada> lRanchada = RanchadaDataAccess.get().filtrarPorZona(
                (String) sZona.getSelectedItem());
        final List<String> ranchadasString = new ArrayList<String>();
        ranchadasString.add("Ranchada");
        for (Ranchada ranchada : lRanchada) {
            ranchadasString.add(ranchada.getNombre());
        }

        adaptadorRanchada = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, ranchadasString);
        adaptadorRanchada.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        sRanchada.setAdapter(adaptadorRanchada);

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
                            MainActivity.clean();
                            activity.onBackPressed();
                            activity.onBackPressed();
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
        return vista;
    }

    private void showDataPicker() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), Utils.DATE_PICKER_PERSONA);
    }

    public void cargarFecha(int day, int month, int year) {
        etFechaNacimiento.setText(Integer.toString(day) + "/" +
                Integer.toString(month) + "/" + Integer.toString(year));
    }

    public void actualizarRanchada() {
        if (MainActivity.personaSeleccionada.getRanchada() != null) {
            String nombreRanchada = MainActivity.personaSeleccionada.getRanchada().getNombre();
            int pos = adaptadorRanchada.getPosition(nombreRanchada);
            sRanchada.setSelection(pos);
        }
    }

    public void actualizarFamilia() {
        if (MainActivity.personaSeleccionada.getGrupoFamiliar() != null) {
            sGrupoFamiliar.setSelection(adaptadorFamilia.getPosition(
                    MainActivity.personaSeleccionada.getGrupoFamiliar().getNombre()));
        }
    }

    public void actualizar() {
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

                if (MainActivity.personaSeleccionada.getZona() != null) {
                    sZona.setSelection(adaptadorZona.getPosition(
                            MainActivity.personaSeleccionada.getZona().getNombre()));
                }

                actualizarRanchada();
                actualizarFamilia();

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

                if (!Roles.getInstance().hasPermission(Utils.PUEDE_VER_TELEFONO_PERSONA)) {
                    etTelefono.setVisibility(View.GONE);
                } else {
                    etTelefono.setVisibility(View.VISIBLE);
                }
                this.bloquearEdicion();
            }
        }
    }

    private void bloquearEdicion() {
        if (Config.getInstance().isEditing()
                && !Roles.getInstance().hasPermission(Utils.PUEDE_EDITAR_PERSONA)) {
            etFechaNacimiento.setEnabled(false);
            etNombre.setEnabled(false);
            etApellido.setEnabled(false);
            etObservaciones.setEnabled(false);
            etDNI.setEnabled(false);
            etTelefono.setEnabled(false);
            sGrupoFamiliar.setEnabled(false);
            sZona.setEnabled(false);
            sRanchada.setEnabled(false);
            bFechaNacimiento.setEnabled(false);
        } else {
            etFechaNacimiento.setEnabled(true);
            etNombre.setEnabled(true);
            etApellido.setEnabled(true);
            etObservaciones.setEnabled(true);
            etDNI.setEnabled(true);
            etTelefono.setEnabled(true);
            sGrupoFamiliar.setEnabled(true);
            sZona.setEnabled(true);
            sRanchada.setEnabled(true);
            bFechaNacimiento.setEnabled(true);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.clear();
        this.setMapListeners(googleMap);
        this.drawZoneLimit(googleMap);
        if (MainActivity.personaSeleccionada != null) {
            Visita visita = VisitaDataAccess.get().findUltimaVisita(MainActivity.personaSeleccionada);
            if (visita != null) {
                if (visita.getUbicacion() != null) {
                    marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(
                            visita.getUbicacion().latitude,
                            visita.getUbicacion().longitude)));
                    centrarMapa(googleMap, visita.getUbicacion());
                }
            } else {
                 if(MainActivity.visitaSeleccionada != null) {
                    marker = googleMap.addMarker(new MarkerOptions().position(
                            getDefaultUbicacion()));
                     MainActivity.visitaSeleccionada.setUbicacion(marker.getPosition());
                    centrarMapa(googleMap, getDefaultUbicacion());
                }
                Log.d(Utils.APPTAG, "PersonaFragment::onMapReady ultimaVisita es null");
            }
        }
    }

    private void drawZoneLimit(GoogleMap googleMap) {
        PolygonOptions polygon = new PolygonOptions();
        polygon.strokeColor(Color.parseColor("#FF0000"))
                .strokeWidth(2)
                .fillColor(0x27FF0000);
        switch (this.sZona.getSelectedItem().toString()) {
            case "Ciudadela":
                polygon.add(new LatLng(-34.627008, -58.565394),
                        new LatLng(-34.618014, -58.543605),
                        new LatLng(-34.621034, -58.541937),
                        new LatLng(-34.620416, -58.540628),
                        new LatLng(-34.620010, -58.541122),
                        new LatLng(-34.619103, -58.538788),
                        new LatLng(-34.623945, -58.532576),
                        new LatLng(-34.622607, -58.531062),
                        new LatLng(-34.654543, -58.529076)
                );
                googleMap.addPolygon(polygon);
                break;
            case "Haedo":
                polygon.add(new LatLng(-34.632693, -58.620531),
                        new LatLng(-34.633480, -58.619137),
                        new LatLng(-34.636001, -58.602394),
                        new LatLng(-34.635478, -58.586819),
                        new LatLng(-34.642322, -58.577665),
                        new LatLng(-34.642719, -58.577527),
                        new LatLng(-34.643461, -58.581886),
                        new LatLng(-34.649336, -58.580723),
                        new LatLng(-34.650484, -58.583062),
                        new LatLng(-34.659867, -58.593466),
                        new LatLng(-34.636811, -58.621079),
                        new LatLng(-34.634735, -58.621677),
                        new LatLng(-34.633918, -58.620779)
                );
                googleMap.addPolygon(polygon);
                break;
            case "Liniers":
                polygon.add(new LatLng(-34.634780, -58.530340),
                        new LatLng(-34.633633, -58.519810),
                        new LatLng(-34.634709, -58.510856),
                        new LatLng(-34.640103, -58.509872),
                        new LatLng(-34.645752, -58.502341),
                        new LatLng(-34.656943, -58.525715),
                        new LatLng(-34.654349, -58.529300)
                );
                googleMap.addPolygon(polygon);
                break;
            case "Ramos":
                polygon.add(new LatLng(-34.634662, -58.556171),
                        new LatLng(-34.651741, -58.532967),
                        new LatLng(-34.658220, -58.541853),
                        new LatLng(-34.666264, -58.550333),
                        new LatLng(-34.668864, -58.552383),
                        new LatLng(-34.673064, -58.557039),
                        new LatLng(-34.667901, -58.562925),
                        new LatLng(-34.663636, -58.565897),
                        new LatLng(-34.650680, -58.583256),
                        new LatLng(-34.649414, -58.580771),
                        new LatLng(-34.647014, -58.581093),
                        new LatLng(-34.643426, -58.581886),
                        new LatLng(-34.641556, -58.570235),
                        new LatLng(-34.638284, -58.571003),
                        new LatLng(-34.637873, -58.564352),
                        new LatLng(-34.637271, -58.561778),
                        new LatLng(-34.636468, -58.562065)
                );
                googleMap.addPolygon(polygon);
                break;
    /*        case "San Justo":
                polygon.add(new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng()
                );
                googleMap.addPolygon(polygon);
                break;
            case "Tres de Febrero":
                polygon.add(new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng()
                );
                googleMap.addPolygon(polygon);
                break;
            case "Villa Luro":
                polygon.add(new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng()
                );
                googleMap.addPolygon(polygon);
                break;
            case "Villa Sarmiento":
                polygon.add(new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng(),
                        new LatLng()
                );
                googleMap.addPolygon(polygon);
                break;*/
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
                if (!Config.getInstance().isEditing()) {
                    mapFragmentPersona.getMap().clear();
                    marker = mapFragmentPersona.getMap().addMarker(new MarkerOptions().position(new LatLng(
                            latLng.latitude, latLng.longitude)));
                    MainActivity.visitaSeleccionada.setUbicacion(marker.getPosition());
                }
            }
        });

        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location myLocation) {
                if (!locationCargada) {
                    googleMap.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(),
                                    myLocation.getLongitude()), Utils.ZOOM_STANDAR));
                    locationCargada = true;
                }
                centrarMapa(googleMap, myLocation);
            }
        });
    }


    private LatLng getDefaultUbicacion() {
    //FIXME getDefaultUbicacion() se podria obtener del Area o Zona
        return new LatLng(-34.6417109,-58.5651438);
    }

    private void centrarMapa(GoogleMap googleMap, LatLng ubicacion) {
        if (!locationCargada) {
            googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(ubicacion, Utils.ZOOM_STANDAR));
            locationCargada = true;
        }
    }

    private void centrarMapa(GoogleMap googleMap,Location myLocation) {
        if (!locationCargada) {
            googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(),
                            myLocation.getLongitude()), Utils.ZOOM_STANDAR));
            locationCargada = true;
        }
    }

    @Override
    public void onAttach(Activity activity) { //No anda el onAttach(Context context) can API < 23
        super.onAttach(activity);
        ((MainActivity) activity).getAppbar().setTitle(Utils.PERSONA);
        this.activity = (MainActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) getActivity()).getAppbar().setTitle(Utils.HOME);
    }

    @Override
    public void popUp() {
        if (Roles.getInstance().hasPermission(Utils.PUEDE_BORRAR_PERSONA)) {
            if (dialogoBorrar != null)
                dialogoBorrar.show();
        } else {
            Toast unToast = Toast.makeText(this.activity, "No tiene permisos para borrar a esta persona", Toast.LENGTH_SHORT);
            unToast.show();
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
}
