package com.example.facundo.recorridaszotp._7_Interfaces;

import android.app.Fragment;

import com.example.facundo.recorridaszotp._5_Presentation.VisitaFragment;

/**
 * Created by nkali on 25/05/16.
 */
public interface iFragmentChanger {
    public void changeFragment(Fragment frag, String fragVisita, boolean addToBackStack);
}
