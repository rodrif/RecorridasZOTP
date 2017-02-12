package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.AdaptadorListaPedidos;
import com.example.facundo.recorridaszotp._0_Infraestructure.Handlers.PedidoHandler;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.PedidoDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Pedido;
import com.example.facundo.recorridaszotp._7_Interfaces.iFragmentChanger;

import java.util.List;


public class ListaPedidos extends Fragment {

    private List<Pedido> listaPedidos;
    private iFragmentChanger fragmentChanger;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        View vista =  inflater.inflate(R.layout.fragment_lista_pedidos, container, false);
        long personaId = -1;
        Bundle bundle = getArguments();
        if (bundle != null) {
            personaId = bundle.getLong("personaId", -1);
        }
        this.listaPedidos = this.getPedidos(personaId);
        AdaptadorListaPedidos adaptador =
                new AdaptadorListaPedidos(getActivity().getApplicationContext(), this.listaPedidos);
        ListView lViewPedidos = (ListView) vista.findViewById(R.id.lista_pedidos);
        lViewPedidos.setAdapter(adaptador);
        lViewPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new PedidoHandler().mostrarPedido(listaPedidos.get(position), fragmentChanger);
            }
        });

        this.setMenu();
        return vista;
    }

    private List<Pedido> getPedidos(long personaId) {
        return PedidoDataAccess.get().getAllOKOrderFechaSinCompletar(personaId);
    }

    @Override
    public void onAttach(Activity activity) { //No anda el onAttach(Context context) can API < 23
        super.onAttach(activity);
        fragmentChanger = (iFragmentChanger) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentChanger = null;
    }

    private void setMenu() {
        ((MainActivity)getActivity()).getAppbar().setTitle(Utils.PEDIDOS);
        MainActivity.menuGuardar(false);
    }

}
