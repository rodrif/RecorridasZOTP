package com.example.facundo.recorridaszotp._0_Infraestructure.Handlers;

import android.app.Fragment;
import android.os.Bundle;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Pedido;
import com.example.facundo.recorridaszotp._5_Presentation.ListaPedidos;
import com.example.facundo.recorridaszotp._5_Presentation.PedidoFragment;
import com.example.facundo.recorridaszotp._7_Interfaces.iFragmentChanger;

/**
 * Created by gonzalo on 07/02/17.
 */
public class PedidoHandler {

    public void listarPedidos(long personaId, iFragmentChanger fragmentChanger) {
        Bundle bundle = new Bundle();
        bundle.putLong("personaId", personaId);
        Fragment fragment = new ListaPedidos();
        fragment.setArguments(bundle);
        fragmentChanger.changeFragment(fragment, Utils.FRAG_PEDIDOS, true);
    }

    public void mostrarPedido(Pedido pedido, iFragmentChanger fragmentChanger) {
        PedidoFragment frag = new PedidoFragment();
        frag.setPedido(pedido);
        fragmentChanger.changeFragment(frag, Utils.FRAG_PEDIDO, true);
    }
}
