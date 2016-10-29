package com.example.facundo.recorridaszotp._7_Interfaces;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by gonzalo on 29/10/16.
 */
public interface iMapFragment extends OnMapReadyCallback {
    void setLatLng(LatLng latLng);
    MapFragment getMapFragment();
}
