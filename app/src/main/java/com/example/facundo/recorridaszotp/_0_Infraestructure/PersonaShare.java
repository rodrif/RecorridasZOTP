package com.example.facundo.recorridaszotp._0_Infraestructure;

import android.content.Intent;
import android.app.Activity;

import com.example.facundo.recorridaszotp._2_DataAccess.VisitaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.example.facundo.recorridaszotp._5_Presentation.MainActivity;

import static android.support.v4.app.ActivityCompat.startActivity;


/**
 * Created by gonzalo on 13/12/16.
 */
public class PersonaShare {

    private Persona persona;
    private Visita visita;

    public PersonaShare(Persona persona) {
        this.persona = persona;
        this.visita = VisitaDataAccess.get().findUltimaVisita(this.persona);
    }

    public PersonaShare(Visita visita) {
        this.visita = visita;
        this.persona = this.visita.getPersona();
    }

    public void share(Activity activity) {
        if (this.visita != null) {
            String textoACompartir = "Nombre: " + persona.getNombre() + "\n";
            textoACompartir += "Apellido: " + persona.getNombre() + "\n";
            textoACompartir += "Ubicación: http://maps.google.com/maps?&q=" +
                    Double.toString(visita.getLatitud()) + "+" +
                    Double.toString(visita.getLongitud());
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, textoACompartir);
            sendIntent.setType("text/plain");
            activity.startActivity(sendIntent);
        }
    }
}
