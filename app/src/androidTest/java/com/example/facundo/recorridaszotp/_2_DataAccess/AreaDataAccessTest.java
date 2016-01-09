package com.example.facundo.recorridaszotp._2_DataAccess;

import android.test.AndroidTestCase;

import com.example.facundo.recorridaszotp._3_Domain.Area;

import java.util.List;

/**
 * Created by Gonzalo on 09/01/2016.
 */
public class AreaDataAccessTest extends AndroidTestCase{
    public void testDownloadAreas() {
        AreaDataAccess.get().sincronizar(null);
        List<Area> listaAreas = AreaDataAccess.get().getAll();
        assertNotNull(listaAreas);
    }
}
