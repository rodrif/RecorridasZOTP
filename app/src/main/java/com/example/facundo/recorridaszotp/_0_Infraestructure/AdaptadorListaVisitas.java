package com.example.facundo.recorridaszotp._0_Infraestructure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._3_Domain.Persona;
import com.example.facundo.recorridaszotp._3_Domain.Visita;
import com.example.facundo.recorridaszotp._3_Domain.Zona;

import java.util.List;

/**
 * Created by Gonzalo on 20/11/2015.
 */
public class AdaptadorListaVisitas extends ArrayAdapter<Visita> {
    private List<Visita> listaVisitas;

    public AdaptadorListaVisitas(Context context, List<Visita> listaVisitas) {
        super(context, R.layout.list_item_visita, listaVisitas);
        this.listaVisitas = listaVisitas;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        View item = convertView;

        if (item == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            item = inflater.inflate(R.layout.list_item_visita, null);
        }

        TextView lNombre = (TextView) item.findViewById(R.id.lNombreVisita);
        TextView lZona = (TextView) item.findViewById(R.id.lZonaVisita);

        Persona persona = listaVisitas.get(position).getPersona();
        if (persona != null) {
            lNombre.setText(persona.getNombre());

            Zona unaZona = persona.getZona();
            if (unaZona != null) {
                lZona.setText(listaVisitas.get(position).getPersona().getZona().getNombre());
            }
        }

        TextView lIdWeb = (TextView) item.findViewById(R.id.lIdWebVisita);
        lIdWeb.setText(Integer.toString(listaVisitas.get(position).getWebId()));

        return (item);
    }
}
