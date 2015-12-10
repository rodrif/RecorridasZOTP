package com.example.facundo.recorridaszotp._2_DataAccess;

import android.test.AndroidTestCase;

import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp._0_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._3_Domain.Zona;

/**
 * Created by Gonzalo on 10/12/2015.
 */
public class ZonaDataAccessTest extends AndroidTestCase {
    public void testFindPrimaryKey() throws Exception {
        ActiveAndroid.initialize(getContext());
        DBUtils.getZonaTest();
        String zonaABuscar = "Liniers";
        Zona resultado = ZonaDataAccess.get().find(zonaABuscar);

        assertNotNull("No se encuentra la zona", resultado);
    }
}
