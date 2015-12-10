package com.example.facundo.recorridaszotp._2_DataAccess;

import android.test.AndroidTestCase;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.example.facundo.recorridaszotp._3_Domain.GrupoFamiliar;

/**
 * Created by Gonzalo on 05/12/2015.
 */
public class GrupoFamiliarDataAccessTest  extends AndroidTestCase {

    public void testFindPrimaryKey() throws Exception {
        ActiveAndroid.initialize(getContext());
//        new Delete().from(GrupoFamiliar.class).execute();
        GrupoFamiliar grupoFamiliar = new GrupoFamiliar("Familia 1");
        String stringABuscar = "Familia 1";
        GrupoFamiliarDataAccess.get().save(grupoFamiliar);
        GrupoFamiliar resultado = GrupoFamiliarDataAccess.get().find(stringABuscar);

        assertNotNull("No se encuentra el Grupo Familiar", resultado);
    }
}
