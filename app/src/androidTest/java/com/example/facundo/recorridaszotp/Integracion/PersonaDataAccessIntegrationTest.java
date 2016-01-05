package com.example.facundo.recorridaszotp.Integracion;

import android.test.AndroidTestCase;

import com.example.facundo.recorridaszotp._0_Infraestructure.DBUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Facundo on 24/10/2015.
 */
public class PersonaDataAccessIntegrationTest extends AndroidTestCase {

    public void testSincronizar() throws Exception {
        CountDownLatch signal = new CountDownLatch(1);
        DBUtils.loadDefaultDB();
        SincronizarDelegate delegate = new SincronizarDelegate(signal);
        PersonaDataAccess.get().sincronizar(delegate);

        if (!signal.await(Utils.MAX_INTENTOS + 5, TimeUnit.SECONDS)) {
            fail("no recibio respuesta del servidor");
        }
    }

    public class SincronizarDelegate implements AsyncDelegate {
        private CountDownLatch signal;

        public SincronizarDelegate(CountDownLatch signal) {
            this.signal = signal;
        }

        @Override
        public void ejecutar(String result) throws Exception {
            this.signal.countDown();
        }
    }
}
