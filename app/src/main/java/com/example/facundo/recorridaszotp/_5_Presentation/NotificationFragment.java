package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Handlers.PersonaHandler;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._7_Interfaces.iFragmentChanger;

public class NotificationFragment extends Fragment {
    private iFragmentChanger fragmentChanger;
    private int idPersona;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String codigoNotificacion = getArguments().getString(Utils.CODIGO_NOTIFICACION);
        String idPersonaString = getArguments().getString(Utils.PERSONA_ID, "");
        if (idPersonaString.equalsIgnoreCase("")) {
            this.idPersona = -1;
        } else {
            this.idPersona = Integer.parseInt(idPersonaString);
        }
        String titulo = getArguments().getString(Utils.TITULO);
        String subtitulo = getArguments().getString(Utils.SUBTITULO);
        String descripcion = getArguments().getString(Utils.DESCRIPCION);
        Log.d(Utils.APPTAG, "Notificacion: codigo: " + codigoNotificacion +
            " idPersona: " + idPersonaString + " titulo: " + titulo +
        " subtitulo: " + subtitulo + " decripcion: " + descripcion);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        EditText TVTitle = (EditText) v.findViewById(R.id.TVNotificationTitle);
        EditText TVSubtitle = (EditText) v.findViewById(R.id.TVNotificationSubtitle);
        TVSubtitle.setOnClickListener(new onClickListenerNotificacion(
                this.idPersona, this.fragmentChanger));
        EditText TVDescription = (EditText) v.findViewById(R.id.TVNotificationDescription);

        TVTitle.setText(titulo);
        Drawable draw = getResources().getDrawable(R.drawable.peligro);;
        switch (codigoNotificacion) {
            case "1":
                draw = getResources().getDrawable(R.drawable.clock);
                break;
            case "2":
                draw = getResources().getDrawable(R.drawable.peligro);
                break;
            case "3":
                draw = getResources().getDrawable(R.drawable.cumple);
                break;
        }
        TVTitle.setCompoundDrawablesWithIntrinsicBounds(draw, null, draw, null);
        TVSubtitle.setText(subtitulo);
        TVDescription.setText(descripcion);
        return v;
    }

    @Override
    public void onAttach(Activity activity) { //No anda el onAttach(Context context) can API < 23
        super.onAttach(activity);
        fragmentChanger = (iFragmentChanger) activity;
        ((MainActivity)activity).getAppbar().setTitle(Utils.NOTIFICACION); //FIXME no se ve titulo notificacion
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentChanger = null;
        ((MainActivity)getActivity()).getAppbar().setTitle(Utils.HOME);
    }

    @Override
    public String toString() {
        return Utils.FRAG_NOTIFICACION;
    }
}

class onClickListenerNotificacion implements View.OnClickListener {
    private int personaId = -1;
    private iFragmentChanger fragmentChanger;

    public onClickListenerNotificacion (int personaId, iFragmentChanger fragmentChanger) {
        this.personaId = personaId;
        this.fragmentChanger = fragmentChanger;
    }

    @Override
    public void onClick(View v) {
        if (this.personaId > 0) {
            new PersonaHandler().mostrarPersona(this.personaId, this.fragmentChanger);
        } else {
            Log.e(Utils.APPTAG, "this.personaId erroneo en NotificationFragment::onClick");
        }
    }
}
