package com.example.facundo.recorridaszotp._0_Infraestructure;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.widget.TextView;

import com.activeandroid.util.Log;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by gonzalo on 17/10/16.
 */
public class GeolocalizadorInverso extends AsyncTask<String, Void, List<Address>> {

    private TextView textView;
    private LatLng ubicacion;
    private Context context;

    public GeolocalizadorInverso(TextView textView, LatLng ubicacion, Context context) {
        this.textView = textView;
        this.ubicacion = ubicacion;
        this.context = context;
    }

    @Override
    protected List<Address> doInBackground(String... params) {
        List<Address> addresses = new ArrayList<Address>();
        Geocoder geocoder = new Geocoder(this.context);
        try {
            addresses = geocoder.getFromLocation(ubicacion.latitude, ubicacion.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    @Override
    protected void onPostExecute(List<Address> addresses) {
        super.onPostExecute(addresses);
        if (this.textView != null) {
            if (addresses.isEmpty()) {
                Log.e(Utils.APPTAG, "addresses is empty: " + (addresses.isEmpty() ? "true" : "false"));
                this.textView.setText("");
            }else {
                Address direccion = addresses.get(0);
                String locality = direccion.getLocality() == null ? "" : (", " + direccion.getLocality());
                this.textView.setText(direccion.getAddressLine(0) + locality);
            }
        }
    }
}
