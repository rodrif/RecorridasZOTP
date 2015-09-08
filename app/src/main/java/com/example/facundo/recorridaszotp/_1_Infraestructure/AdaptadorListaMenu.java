package com.example.facundo.recorridaszotp._1_Infraestructure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._3_Domain.ItemLista;
import java.util.List;

/**
 * Created by Gonzalo on 05/09/2015.
 */
public class AdaptadorListaMenu extends ArrayAdapter<ItemLista> {
    private List<ItemLista> listaItems;

    public AdaptadorListaMenu(Context context, List<ItemLista> listaItems) {
        super(context, R.layout.itm, listaItems);
        this.listaItems = listaItems;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View item = convertView;

        if(item == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            item = inflater.inflate(R.layout.itm, null);
        }

        TextView lNombre = (TextView) item.findViewById(R.id.nombre);
        lNombre.setText(listaItems.get(position).getNombre());

        ImageView icono = (ImageView) item.findViewById(R.id.icono);
        icono.setImageResource(listaItems.get(position).getIcono());

        return (item);
    }
}