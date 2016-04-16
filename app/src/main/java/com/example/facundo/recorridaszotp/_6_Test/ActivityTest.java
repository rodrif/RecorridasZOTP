package com.example.facundo.recorridaszotp._6_Test;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._5_Presentation.ListaPersonas;

public class ActivityTest extends AppCompatActivity {
    public Fragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        frag = new ListaPersonas();
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame_test, frag)
                .commit();
    }

    public void cambiarFragment(Fragment unFragment) {
        frag = unFragment;
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame_test, unFragment)
                .commit();
    }
}
