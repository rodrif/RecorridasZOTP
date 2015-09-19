package com.example.facundo.recorridaszotp._0_Infraestructure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._3_Domain.Persona;

import java.util.List;

/**
 * Created by Gonzalo on 22/08/2015.
 */
public class AdaptadorListaPersonas extends ArrayAdapter<Persona> {
    private List<Persona> listaPersonas;

    public AdaptadorListaPersonas(Context context, List<Persona> listaPersonas) {
        super(context, R.layout.list_item_persona, listaPersonas);
        this.listaPersonas = listaPersonas;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View item = convertView;

        if(item == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            item = inflater.inflate(R.layout.list_item_persona, null);
        }

        TextView lNombre = (TextView) item.findViewById(R.id.lNombre);
        lNombre.setText(listaPersonas.get(position).getNombre());

        TextView lApellido = (TextView) item.findViewById(R.id.lApellido);
        lApellido.setText(listaPersonas.get(position).getApellido());

        TextView lIdWeb = (TextView) item.findViewById(R.id.lIdWeb);
        lIdWeb.setText(listaPersonas.get(position).getWebId());

        return (item);
    }
}
