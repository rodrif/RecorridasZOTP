package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
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
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.google.android.gms.maps.CameraUpdateFactory;
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
    private boolean locationCargada = false;

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
        mapFragmentMapa = (MapFragment) (getFragmentManager().findFragmentById(R.id.mapMapa));
        mapFragmentMapa.getMapAsync(this);
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

        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location myLocation) {
                if (!locationCargada) {
                    mapFragmentMapa.getMap().animateCamera(
                            CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(),
                                    myLocation.getLongitude()), Utils.ZOOM_STANDAR));
                    locationCargada = true;
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
    }

    @Override
    public void onAttach(Activity activity) { //No anda el onAttach(Context context) can API < 23
        super.onAttach(activity);
        ((MainActivity)activity).getAppbar().setTitle(Utils.MAPA);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity)getActivity()).getAppbar().setTitle(Utils.HOME);
    }

    @Override
    public String toString() {
        return Utils.FRAG_MAPA;
    }
}
