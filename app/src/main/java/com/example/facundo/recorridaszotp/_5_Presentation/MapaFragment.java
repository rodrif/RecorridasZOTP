package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapaFragment extends Fragment implements OnMapReadyCallback {
    private static View vista;
    private MapFragment mapFragmentMapa = null;

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

        mapFragmentMapa = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapMapa);
        mapFragmentMapa.getMapAsync(this);
        mapFragmentMapa.getMap().setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });
        mapFragmentMapa.getMap().setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
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

        return vista;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        List<Visita> visitas = VisitaDataAccess.get().findUltimasVisita();
        GoogleMap gMap = mapFragmentMapa.getMap();
        gMap.clear();
        for (Visita unaVisita : visitas) {
            if (unaVisita.getUbicacion() != null) {
                Marker mTest = gMap.addMarker(new MarkerOptions().position(unaVisita.getUbicacion()));
                if(unaVisita.getUbicacion().latitude != mTest.getPosition().latitude
                        || unaVisita.getUbicacion().longitude != mTest.getPosition().longitude) {
                    Log.e(Utils.APPTAG, "Markers no coinciden" + mTest.getPosition().toString() +
                            "distinto " + unaVisita.getUbicacion().toString());
                }
            }
        }
    }
}
