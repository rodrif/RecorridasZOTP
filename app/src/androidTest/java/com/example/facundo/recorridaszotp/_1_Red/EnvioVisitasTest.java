package com.example.facundo.recorridaszotp._1_Red;

import android.test.AndroidTestCase;

import com.example.facundo.recorridaszotp._0_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._1_Red.Mocks.EnvioVisitasMock;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Facundo on 18/10/2015.
 */
public class EnvioVisitasTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DBUtils.loadDefaultDB();
    }

    public void testEnvioVisitas() throws Exception {
        EnvioVisitas envioVisitas = new EnvioVisitasMock(VisitaDataAccess.findVisitasASincronizar());

        String datosAEnviar = envioVisitas.cargarJson().toString();
        String expected = "[{\"android_id\":510,\"descripcion\":\"desc3\",\"fecha\":1425990950000,\"estado\":0,\"web_id\":-1,\"persona_web_id\":2},{\"android_id\":509,\"fecha\":1425992960000,\"estado\":0,\"web_id\":-1,\"persona_web_id\":1000},{\"android_id\":508,\"descripcion\":\"desc1\",\"fecha\":1425990960000,\"estado\":0,\"web_id\":-1,\"persona_web_id\":1000}]";
        String regExp = "\"android_id\":[0-9]*,";

        assertEquals("EnvioVisitasDatosMalCargados", expected.replaceAll(regExp, ""), datosAEnviar.replaceAll(regExp, ""));
    }

}
