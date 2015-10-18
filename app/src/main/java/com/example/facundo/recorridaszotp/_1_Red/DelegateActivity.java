package com.example.facundo.recorridaszotp._1_Red;

import android.app.Activity;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.PersonaJsonUtils;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._5_Presentation.ListaPersonas;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by Facundo on 18/10/2015.
 */
public class DelegateActivity implements AsyncDelegate {
    AsyncDelegate delegate;
    Activity activity;

    public DelegateActivity(Activity activity) {
        this.activity = activity;
    }

    public DelegateActivity(AsyncDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void executionFinished(String result) throws Exception {
        //Como fue llamado desde lista Personas
        Fragment fragment = new ListaPersonas();
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        if (this.delegate != null) {
            delegate.executionFinished("");
        }
    }
}
