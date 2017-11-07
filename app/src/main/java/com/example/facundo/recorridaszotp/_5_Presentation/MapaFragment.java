package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.content.Intent;
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

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Handlers.PersonaHandler;
import com.example.facundo.recorridaszotp._0_Infraestructure.Handlers.VisitaHandler;
import com.example.facundo.recorridaszotp._0_Infraestructure.PersonaShare;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.example.facundo.recorridaszotp._7_Interfaces.iFragmentChanger;
import com.example.facundo.recorridaszotp._7_Interfaces.iPersonaHandler;
import com.example.facundo.recorridaszotp._7_Interfaces.iVisitaHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.plus.model.people.Person;

import java.util.List;

public class MapaFragment extends Fragment implements OnMapReadyCallback {
    private static View vista;
    private MapFragment mapFragmentMapa = null;
    private iFragmentChanger fragmentChanger;
    private final iPersonaHandler personaHandler = getPersonaHandler();

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
            vista = inflater.inflate(R.layout.fragment_mapa, container, false);
        } catch (InflateException e) {

        }
        //Para compatibilidad
        mapFragmentMapa = (MapFragment) (getChildFragmentManager().findFragmentById(R.id.mapMapa));
        if (mapFragmentMapa == null) {
            mapFragmentMapa = (MapFragment) (getFragmentManager().findFragmentById(R.id.mapMapa));
        }
        mapFragmentMapa.getMapAsync(this);
        this.setMenu();
        return vista;
    }

    private void setMapListeners(GoogleMap googleMap) {
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Visita visita = VisitaDataAccess.get().find(marker);
                if (visita != null) {
                    marker.setTitle(visita.getPersona().getNombre());
                    Log.v(Utils.APPTAG, "lat: " + marker.getPosition().toString().toString());
                } else {
                    Log.e(Utils.APPTAG, "Error en click marker " + marker.getPosition().toString());
                }
                return false;
            }
        });

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Visita visita = VisitaDataAccess.get().find(marker);
                if (visita != null) {
                    personaHandler.mostrarPersona(visita.getPersona(), fragmentChanger);
                } else {
                    Log.e(Utils.APPTAG, "Error en OnInfoWindowClickListener ");
                }
            }
        });

        googleMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                Visita visita = VisitaDataAccess.get().find(marker);
                if (visita != null) {
                    PersonaShare personaShare = new PersonaShare(visita);
                    personaShare.share(getActivity());
                } else {
                    Log.e(Utils.APPTAG, "Error en OnInfoWindowLongClickListener ");
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            googleMap.setMyLocationEnabled(true);
        } catch (SecurityException ex) {
            Log.e(Utils.APPTAG, "My location enabled security exception");
        }
        this.setMapListeners(googleMap);
        List<Visita> visitas = VisitaDataAccess.get().findUltimasVisita();
        googleMap.clear();
        for (Visita unaVisita : visitas) {
            if (unaVisita.getUbicacion() != null) {
                Marker mTest = googleMap.addMarker(new MarkerOptions().position(unaVisita.getUbicacion()));
                if (unaVisita.getUbicacion().latitude != mTest.getPosition().latitude
                        || unaVisita.getUbicacion().longitude != mTest.getPosition().longitude) {
                    Log.e(Utils.APPTAG, "Markers no coinciden" + mTest.getPosition().toString() +
                            "distinto " + unaVisita.getUbicacion().toString());
                }
            }
        }
        this.centrarMapa(getDefaultUbicacion(), googleMap);
    }

    private void centrarMapa(LatLng ubicacion, GoogleMap googleMap) {
        googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(ubicacion, Utils.ZOOM_STANDAR));
    }

    private LatLng getDefaultUbicacion() {
        //FIXME getDefaultUbicacion() se podria obtener del Area o Zona
        return new LatLng(-34.5803526,-58.4360354);
    }

    private iPersonaHandler getPersonaHandler() {
        return new PersonaHandler();
    }

    @Override
    public void onAttach(Activity activity) { //No anda el onAttach(Context context) can API < 23
        super.onAttach(activity);
        fragmentChanger = (iFragmentChanger) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentChanger = null;
        ((MainActivity)getActivity()).getAppbar().setTitle(Utils.HOME);
    }

    private void setMenu() {
        ((MainActivity)getActivity()).getAppbar().setTitle(Utils.MAPA);
        MainActivity.menuGuardar(false);
    }

    @Override
    public String toString() {
        return Utils.FRAG_MAPA;
    }
}
