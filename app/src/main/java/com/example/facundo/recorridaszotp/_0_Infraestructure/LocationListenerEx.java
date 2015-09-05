package com.example.facundo.recorridaszotp._0_Infraestructure;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class LocationListenerEx implements LocationListener {
    Location ubicacion = null;

    public LocationListenerEx(Location ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.ubicacion.set(location);
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

}
