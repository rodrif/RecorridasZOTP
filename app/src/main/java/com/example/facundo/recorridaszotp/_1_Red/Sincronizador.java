package com.example.facundo.recorridaszotp._1_Red;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.PedidoJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Enviadores.EnvioAreas;
import com.example.facundo.recorridaszotp._1_Red.Enviadores.EnvioPedidos;
import com.example.facundo.recorridaszotp._1_Red.Enviadores.EnvioPersonas;
import com.example.facundo.recorridaszotp._1_Red.Enviadores.EnvioVisitas;
import com.example.facundo.recorridaszotp._1_Red.Receptores.BasicRecepcion;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionAreas;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionFamilias;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionPersonas;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionRanchadas;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionVisitas;
import com.example.facundo.recorridaszotp._1_Red.Receptores.RecepcionZonas;
import com.example.facundo.recorridaszotp._2_DataAccess.AreaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.PedidoDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Pedido;
import com.example.facundo.recorridaszotp._5_Presentation.ExceptionHandler;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;

/**
 * Created by gonzalo on 30/04/16.
 */
public class Sincronizador extends AsyncTask<Void, Void, Void>{
    private Activity activity;
    private ProgressDialog progressDialog;
    private boolean bloquearApp = false;

    public Sincronizador(Activity activity) {
        this.activity = activity;
    }

    public Sincronizador(Activity activity, boolean blockApp) {
        this(activity);
        this.bloquearApp = blockApp;
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
        RecepcionAreas recepcionAreas = new RecepcionAreas();
        recepcionAreas.executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_RECIBIR_AREAS);
    }

    @Override
    protected Void doInBackground(Void... params) {
        //FIXME: Hacer sincronizacion aca!!!!
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (MainActivity.versionError == true) {
            ExceptionHandler.makeExceptionVersionAlert(activity);
        } else {
            new RecepcionZonas().executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_RECIBIR_ZONAS);
            new RecepcionRanchadas().executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_RECIBIR_RANCHADAS);
            new RecepcionFamilias().executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_RECIBIR_FAMILIAS);
            new RecepcionPersonas().executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_RECIBIR_PERSONAS);
            new EnvioPersonas(PersonaDataAccess.get().findASincronizar()).executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_ENVIO_PERSONAS);
            new RecepcionVisitas().executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_RECIBIR_VISITAS);
            new EnvioVisitas(VisitaDataAccess.get().findASincronizar()).executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_ENVIO_VISITAS);
            new BasicRecepcion<Pedido>(PedidoDataAccess.get(), PedidoJsonUtils.get()).executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_RECIBIR_PEDIDOS); //FIXME se podria crear recepcion pedidos para que aparezca con ese nombre en el debug
            new EnvioPedidos(PedidoDataAccess.get().findASincronizar()).executeOnExecutor(SERIAL_EXECUTOR, Utils.WEB_ENVIAR_PEDIDOS);

        }
        if (this.bloquearApp) {
            progressDialog.dismiss();
        }
    }
}
