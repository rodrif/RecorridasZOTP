package com.example.facundo.recorridaszotp._1_Red;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._1_Red.Enviadores.EnvioAreas;
import com.example.facundo.recorridaszotp._1_Red.Enviadores.EnvioPersonas;
import com.example.facundo.recorridaszotp._1_Red.Enviadores.EnvioVisitas;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionAreas;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionFamilias;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionPersonas;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionRanchadas;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionVisitas;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionZonas;
import com.example.facundo.recorridaszotp._2_DataAccess.AreaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;

/**
 * Created by gonzalo on 30/04/16.
 */
public class Sincronizador extends AsyncTask<Void, Void, Void>{
    private Activity activity;
    private ProgressDialog progressDialog;
    private AsyncDelegate delegate = null;
    private boolean bloquearApp = false;

    public Sincronizador(Activity activity) {
        this.activity = activity;
    }

    public Sincronizador(Activity activity, AsyncDelegate delegate, boolean blockApp) {
        this(activity);
        this.delegate = delegate;
        this.bloquearApp = blockApp;
    }

    public Sincronizador(Activity activity, boolean blockApp) {
        this(activity, null, blockApp);
    }

    public Sincronizador(Activity activity, AsyncDelegate delegate) {
        this(activity, delegate, false);
    }

        @Override
    protected void onPreExecute() {
        if (this.bloquearApp) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("Obteniendo datos...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
        }
        new RecepcionAreas().executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_RECIBIR_AREAS);
        new RecepcionZonas().executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_RECIBIR_ZONAS);
        new RecepcionRanchadas().executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_RECIBIR_RANCHADAS);
        new RecepcionFamilias().executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_RECIBIR_FAMILIAS);
        new RecepcionPersonas().executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_RECIBIR_PERSONAS);
        new EnvioPersonas(PersonaDataAccess.get().findASincronizar()).executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_ENVIO_PERSONAS);
        new RecepcionVisitas().executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_RECIBIR_VISITAS);
        new EnvioVisitas(VisitaDataAccess.get().findASincronizar()).executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_ENVIO_VISITAS);
        new RecepcionPedidos().executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_RECIBIR_PEDIDOS);
            new EnvioPedidos(PedidosDataAccess.get().findASincronizar()).executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_ENVIAR_PEDIDOS);
    }

    @Override
    protected Void doInBackground(Void... params) {
        //FIXME: Hacer sincronizacion aca!!!!
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (delegate != null) {
            try {
                delegate.ejecutar("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.bloquearApp) {
            progressDialog.dismiss();
        }
    }
}
