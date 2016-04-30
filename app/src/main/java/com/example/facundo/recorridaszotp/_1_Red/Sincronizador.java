package com.example.facundo.recorridaszotp._1_Red;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.example.facundo.recorridaszotp._1_Red.Delegates.AsyncDelegate;
import com.example.facundo.recorridaszotp._2_DataAccess.AreaDataAccess;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;

/**
 * Created by gonzalo on 30/04/16.
 */
public class Sincronizador extends AsyncTask<Void, Void, Void>{
    private Activity activity;
    private ProgressDialog progressDialog;
    private AsyncDelegate delegate = null;

    public Sincronizador(Activity activity) {
        this.activity = activity;
    }

    public Sincronizador(Activity activity, AsyncDelegate delegate) {
        this(activity);
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Sincronizando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        AreaDataAccess.get().sincronizarTodo(null);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        progressDialog.dismiss();
        if (this.delegate != null) try {
            delegate.ejecutar("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
