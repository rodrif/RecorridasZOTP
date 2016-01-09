package com.example.facundo.recorridaszotp._2_DataAccess;

import android.test.AndroidTestCase;

import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp._3_Domain.Familia;

/**
 * Created by Gonzalo on 05/12/2015.
 */
public class FamiliaDataAccessTest extends AndroidTestCase {

    public void testFindPrimaryKey() throws Exception {
        ActiveAndroid.initialize(getContext());
//        new Delete().from(GrupoFamiliar.class).execute();
        Familia familia = new Familia("Familia 1");
        String stringABuscar = "Familia 1";
        FamiliaDataAccess.get().save(familia);
        Familia resultado = FamiliaDataAccess.get().find(stringABuscar);

        assertNotNull("No se encuentra el Grupo Familiar", resultado);
    }
}
