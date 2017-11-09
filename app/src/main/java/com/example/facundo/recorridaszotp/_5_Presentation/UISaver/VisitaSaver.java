package com.example.facundo.recorridaszotp._5_Presentation.UISaver;

import android.widget.EditText;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Sincronizador;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;

/**
 * Created by gonzalo on 08/11/17.
 */

public class VisitaSaver {

    public static void save(MainActivity activity) {
        //Obtengo fecha y observaciones de la visita
        //La ubicacion se carga desde MapsFragment
        EditText eTFecha = (EditText) activity.getFragmentManager()
                .findFragmentById(R.id.content_frame).getView().findViewById(R.id.ETFecha);
        EditText eTObservaciones = (EditText) activity.getFragmentManager()
                .findFragmentById(R.id.content_frame).getView().findViewById(R.id.ETObservacioneVisita);
        EditText etDireccion = (EditText) activity.getFragmentManager()
                .findFragmentById(R.id.content_frame).getView().findViewById(R.id.ETUbicacion);

        if (activity.visitaSeleccionada.getUbicacion() != null) {
            activity.visitaSeleccionada.setFecha(eTFecha.getText().toString());
            activity.visitaSeleccionada.setDescripcion(eTObservaciones.getText().toString());
            activity.visitaSeleccionada.setEstado(Utils.EST_MODIFICADO);
            activity.visitaSeleccionada.setDireccion(etDireccion.getText().toString());
            VisitaDataAccess.get().save(activity.visitaSeleccionada);
            Toast unToast = Toast.makeText(activity, "Visita a " + activity.visitaSeleccionada.getPersona().getNombre()
                    + " guardada", Toast.LENGTH_SHORT);
            unToast.show();
            Answers.getInstance().logCustom(new CustomEvent("Visita guardada")
                    .putCustomAttribute("Area", Config.getInstance().getArea())
                    .putCustomAttribute("User", Config.getInstance().getUserMail()));
            Sincronizador sinc = new Sincronizador(activity, false);
            sinc.execute();
        }
        activity.clearAllFragments();
        activity.clean();
    }

}
