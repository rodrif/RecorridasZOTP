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
    }

    @Override
    protected Void doInBackground(Void... params) {
        RecepcionAreas recepcionAreas = new RecepcionAreas();
        recepcionAreas.execute(Utils.WEB_RECIBIR_AREAS);
        if (MainActivity.versionError == true) {
            ExceptionHandler.makeExceptionVersionAlert(activity);
        } else {
            new RecepcionZonas().execute();
            //new RecepcionRanchadas().execute(Utils.WEB_RECIBIR_RANCHADAS); //FIXME Delete?
            //new RecepcionFamilias().execute(Utils.WEB_RECIBIR_FAMILIAS);   //FIXME Delete?
            new RecepcionPersonas().execute();
            new EnvioPersonas(PersonaDataAccess.get().findASincronizar()).execute();
            new RecepcionVisitas().execute();
            new EnvioVisitas(VisitaDataAccess.get().findASincronizar()).execute();
            new RecepcionPedidos().execute();
            new EnvioPedidos(PedidoDataAccess.get().findASincronizar()).execute();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (this.bloquearApp) {
            progressDialog.dismiss();
        }
    }
}
