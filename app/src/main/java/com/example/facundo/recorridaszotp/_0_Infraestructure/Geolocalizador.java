package com.example.facundo.recorridaszotp._0_Infraestructure;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.widget.TextView;

import com.activeandroid.util.Log;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;
import com.example.facundo.recorridaszotp._5_Presentation.VisitaFragment;
import com.example.facundo.recorridaszotp._7_Interfaces.iMapFragment;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gonzalo on 29/10/16.
 */
public class Geolocalizador extends AsyncTask<String, Void, List<Address>> {

    private String direccion;
    private Context context;
    private iMapFragment iMapFragment;

    public Geolocalizador(String direccion, Context context, iMapFragment iMapFragment) {
        this.direccion = direccion;
        this.context = context;
        this.iMapFragment = iMapFragment;
    }

    @Override
    protected List<Address> doInBackground(String... params) {
        List<Address> addresses = new ArrayList<Address>();
        Geocoder geocoder = new Geocoder(this.context);
        try {
            addresses = geocoder.getFromLocationName(direccion + " Buenos Aires,Argentina", 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    @Override
    protected void onPostExecute(List<Address> addresses) {
        if (addresses.isEmpty()) {
            Log.d(Utils.APPTAG, "Geolocalización fallida");
        } else {
            LatLng latLng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            this.iMapFragment.setLatLng(latLng);
            iMapFragment.getMapFragment().getMapAsync(iMapFragment);
        }

    }
}
