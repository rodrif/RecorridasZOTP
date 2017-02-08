package com.example.facundo.recorridaszotp._0_Infraestructure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._3_Domain.Pedido;

import java.util.List;

/**
 * Created by gonzalo on 16/01/17.
 */
public class AdaptadorListaPedidos extends ArrayAdapter<Pedido> {
    private List<Pedido> listaPedidos;

    public AdaptadorListaPedidos(Context context, List<Pedido> listaPedidos) {
        super(context, R.layout.list_item_pedido, listaPedidos);
        this.listaPedidos = listaPedidos;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        View item = convertView;

        if (item == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            item = inflater.inflate(R.layout.list_item_pedido, null);
        }

        TextView lDescripcion = (TextView) item.findViewById(R.id.lDescripcionPedido);
        TextView lFecha = (TextView) item.findViewById(R.id.lFechaPedido);

        Pedido pedido = listaPedidos.get(position);
        lDescripcion.setText(pedido.getDescripcion());
        lFecha.setText(pedido.getFechaString());

        // TextView lIdWeb = (TextView) item.findViewById(R.id.lIdWebVisita);
        //  lIdWeb.setText(Integer.toString(listaVisitas.get(position).getWebId()));

        return (item);
    }
}
