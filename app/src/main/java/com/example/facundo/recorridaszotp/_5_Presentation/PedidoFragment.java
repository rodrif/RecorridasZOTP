package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.DatePickerFragment;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._0_Infraestructure.popUp;
import com.example.facundo.recorridaszotp._1_Red.Sincronizador;
import com.example.facundo.recorridaszotp._2_DataAccess.PedidoDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Pedido;

public class PedidoFragment extends Fragment implements popUp {
    private static View vista;
    private Pedido pedido;
    private EditText etFecha = null;
    private ImageButton ibFecha = null;
    private EditText etDescripcion = null;
    private CheckBox chCompletado = null;
    AlertDialog.Builder dialogoBorrar = null;

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
        MainActivity.menuGuardar(true);
        this.actualizar();
        dialogoBorrar = new AlertDialog.Builder(getActivity());
        dialogoBorrar.setTitle("Importante");
        dialogoBorrar.setMessage("¿ Seguro quiere eliminar ?");
        dialogoBorrar.setCancelable(false);
        dialogoBorrar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                // cancelar();
            }
        });
        dialogoBorrar.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                if (pedido.getWebId() != -1) {
                    PedidoDataAccess.get().deleteLogico(pedido);
                    if (pedido.getEstado() == Utils.EST_BORRADO) {
                        Toast.makeText(getActivity(),
                                "Se elimino el pedido a " + pedido.getPersona().getNombre()
                                        + " exitosamente", Toast.LENGTH_SHORT).show();
                        Sincronizador sinc = new Sincronizador(getActivity(), false);
                        sinc.execute();
                        getActivity().onBackPressed();
                    } else {
                        Log.e(Utils.APPTAG, "Error al hacer borrado logico de pedidoId: " +
                                pedido.getId() +
                                "en PedidoFragment::PositiveButton::onClick");
                    }
                } else {
                    Toast.makeText(getActivity(),
                            "No se puede borrar un pedido no creado", Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.setMenu();
        return vista;
    }

    private void setMenu() {
        ((MainActivity)getActivity()).getAppbar().setTitle(Utils.PEDIDO);
        MainActivity.menuGuardar(true);
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void actualizar() {
        if (this.pedido != null) {
            this.etFecha.setText(this.pedido.getFechaString());
            this.etDescripcion.setText(this.pedido.getDescripcion());
            this.chCompletado.setChecked(this.pedido.getCompletado());
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

    public void savePedido() {
        View vista = getFragmentManager().findFragmentById(R.id.content_frame).getView();
        EditText ETFecha = (EditText) vista.findViewById(R.id.ETFechaPedido);
        EditText ETDescripcion = (EditText) vista.findViewById(R.id.ETDescripcionPedido);
        CheckBox CHCompletado = (CheckBox) vista.findViewById(R.id.CHCompletadoPedido);
        String fecha = ETFecha.getText().toString();
        String descripcion = ETDescripcion.getText().toString();
        boolean completado = CHCompletado.isChecked();
        this.pedido.setFecha(fecha);
        this.pedido.setDescripcion(descripcion);
        this.pedido.setCompletado(completado);
        this.pedido.setEstado(Utils.EST_MODIFICADO);
        this.pedido.save();
        Sincronizador sinc = new Sincronizador(getActivity(), false);
        sinc.execute();
        Toast unToast = Toast.makeText(getActivity(), "Pedido guardado", Toast.LENGTH_SHORT);
        unToast.show();
        getFragmentManager().popBackStack(); //Si se guarda vuelve al fragment anterior
    }

    @Override
    public void popUp() {
       if (dialogoBorrar != null) {
           dialogoBorrar.show();
       }
    }
}