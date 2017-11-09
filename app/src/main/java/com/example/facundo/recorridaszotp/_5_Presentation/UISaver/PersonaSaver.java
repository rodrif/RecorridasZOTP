package com.example.facundo.recorridaszotp._5_Presentation.UISaver;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Sincronizador;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Query.PersonaQuery;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;

/**
 * Created by gonzalo on 08/11/17.
 */

public class PersonaSaver {

    public static void save(MainActivity activity) {
        View vista = activity.getFragmentManager().findFragmentById(R.id.content_frame).getView();

        EditText ETnombre = (EditText) vista.findViewById(R.id.ETNombre);
        EditText ETapellido = (EditText) vista.findViewById(R.id.ETApellido);
        EditText ETfechaNacimiento = (EditText) vista.findViewById(R.id.ETFechaNacimiento);
        EditText ETobservaciones = (EditText) vista.findViewById(R.id.ETObservaciones);
        EditText ETdni = (EditText) vista.findViewById(R.id.ETDni);
        EditText ETTelefono = (EditText) vista.findViewById(R.id.ETTelefono);
        EditText ETPantalon = (EditText) vista.findViewById(R.id.ETPantalon);
        EditText ETRemera = (EditText) vista.findViewById(R.id.ETRemera);
        EditText ETZapatillas = (EditText) vista.findViewById(R.id.ETZapatillas);
        Spinner sZona = (Spinner) vista.findViewById(R.id.spinner_zona);

        String nombre = ETnombre.getText().toString();
        String apellido = ETapellido.getText().toString();
        String dni = ETdni.getText().toString();
        String telefono = ETTelefono.getText().toString();
        String pantalon = ETPantalon.getText().toString();
        String remera = ETRemera.getText().toString();
        String zapatillas = ETZapatillas.getText().toString();
        String observaciones = ETobservaciones.getText().toString();
        String fechaNacimiento = ETfechaNacimiento.getText().toString();
        String zona = (String) sZona.getSelectedItem();


        if (!nombre.equals("") && !zona.equals("Zona")) {
            if (activity.personaSeleccionada != null) {// si habia persona seleccionada
                activity.personaSeleccionada.setNombre(nombre);
                activity.personaSeleccionada.setApellido(apellido);
                activity.personaSeleccionada.setDNI(dni);
                activity.personaSeleccionada.setTelefono(telefono);
                activity.personaSeleccionada.setPantalon(pantalon);
                activity.personaSeleccionada.setRemera(remera);
                activity.personaSeleccionada.setZapatillas(zapatillas);
                activity.personaSeleccionada.setObservaciones(observaciones);
                activity.personaSeleccionada.setFechaNacimientoDesdeMob(fechaNacimiento);
                activity.personaSeleccionada.setZona(zona);
                activity.personaSeleccionada.setEstado(Utils.EST_MODIFICADO);
                PersonaDataAccess.get().save(activity.personaSeleccionada);
                if (activity.visitaSeleccionada != null && activity.visitaSeleccionada.getUbicacion() != null) {
                    activity.visitaSeleccionada.setDescripcion("Primera Visita");
                    VisitaDataAccess.get().save(activity.visitaSeleccionada);
                }

                //Chequea creación correcta
                PersonaQuery query = new PersonaQuery();
                query.nombre = nombre;

                Persona p = PersonaDataAccess.get().find(query);
                Toast unToast = Toast.makeText(activity, " ", Toast.LENGTH_SHORT);
                if (p == null) {
                    unToast.setText("Error al grabar");
                } else {
                    unToast.setText("Se grabo: " + p.getNombre());
                }
                unToast.show();
                Answers.getInstance().logCustom(new CustomEvent("Persona guardada")
                        .putCustomAttribute("Area", Config.getInstance().getArea())
                        .putCustomAttribute("User", Config.getInstance().getUserMail()));
                Sincronizador sinc = new Sincronizador(activity, false);
                sinc.execute();
                activity.menuGuardar(false);
                activity.clearAllFragments();
                activity.clean();
            }
        } else {
            Animation shake = AnimationUtils.loadAnimation(activity, R.anim.shake);
            if (nombre.equals("")) {
                ETnombre.requestFocus();
                ETnombre.startAnimation(shake);
                ETnombre.setError("El nombre es obligatorio");
            } else {
                sZona.requestFocus();
                sZona.startAnimation(shake);
            }
        }
    }
}
