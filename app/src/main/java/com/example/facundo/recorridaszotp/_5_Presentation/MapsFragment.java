package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {
    // private InterfaceMapa interfaceMapa;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private static View vista;
    private Visita visita;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            visita = b.getParcelable("visita");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //View vista = inflater.inflate(R.layout.fragment_map, container, false);

        if (vista != null) {
            ViewGroup parent = (ViewGroup) vista.getParent();
            if (parent != null)
                parent.removeView(vista);
        }
        try {
            vista = inflater.inflate(R.layout.fragment_map, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is  */
        }

        setUpMapIfNeeded();
        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link MapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        Fragment fragment;
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.

            fragment = getFragmentManager().findFragmentById(R.id.map);
            // mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
            //       .getMap();
            if (fragment == null) {
                Toast.makeText(getActivity().getApplicationContext(), "Mapa fragment null", Toast.LENGTH_SHORT).show();
            } else {
                mMap = ((MapFragment) fragment).getMap();
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    public void onMapClick(LatLng point) {
                        //if (visita.getUbicacion() != null)
                        mMap.clear();
                        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(
                                point.latitude, point.longitude)));
                        visita.setUbicacion(marker.getPosition());
                        //interfaceMapa.guardarPersona(point);
                    }
                });
            }

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.clear();
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
 /*       //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.clear();
        if (visita != null)
            if (visita.getUbicacion() != null) {
                Marker marker = mMap.addMarker(new MarkerOptions().position(visita.getUbicacion()));
            }*/
    }


    @Override
    public void onAttach(Activity activity) { //No anda el onAttach(Context context) can API < 23
        super.onAttach(activity);
        // interfaceMapa = (InterfaceMapa) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //interfaceMapa = null;
    }

/*    public interface InterfaceMapa {
        public void guardarPersona(LatLng latLng);
    }*/

    public Visita getVisita() {
        return visita;
    }

    public void setVisita(Visita visita) {
        this.visita = visita;
        mMap.clear();
        if (visita.getUbicacion() != null) {
            mMap.addMarker(new MarkerOptions().position(visita.getUbicacion()));
        }
    }
}