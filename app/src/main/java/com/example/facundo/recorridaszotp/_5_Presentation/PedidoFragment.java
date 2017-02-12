package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.DatePickerFragment;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Pedido;

public class PedidoFragment extends Fragment {
    private static View vista;
    private Pedido pedido;
    private EditText etFecha = null;
    private ImageButton ibFecha = null;
    private EditText etDescripcion = null;
    private CheckBox chCompletado = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (vista != null) {
            ViewGroup parent = (ViewGroup) vista.getParent();
            if (parent != null)
                parent.removeView(vista);
        }
        try {
            vista = inflater.inflate(R.layout.fragment_pedido, container, false);
        } catch (InflateException e) {

        }
        etFecha = (EditText) vista.findViewById(R.id.ETFechaPedido);
        ibFecha = (ImageButton) vista.findViewById(R.id.bFecha);
        ibFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPicker();
            }
        });
        etDescripcion = (EditText) vista.findViewById(R.id.ETDescripcionPedido);
        chCompletado = (CheckBox) vista.findViewById(R.id.CHCompletadoPedido);
        this.actualizar();
        return vista;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void actualizar() {
        if (this.pedido != null) {
            this.etFecha.setText(this.pedido.getFechaString());
            this.etDescripcion.setText(this.pedido.getDescripcion());
            this.chCompletado.setPressed(this.pedido.getCompletado());
        }
    }

    public void cargarFecha(int day, int month, int year) {
        etFecha.setText(Integer.toString(day) + "/" +
                Integer.toString(month) + "/" + Integer.toString(year));
    }

    private void showDataPicker() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), Utils.DATE_PICKER_PEDIDO);
    }
}
