package com.example.facundo.recorridaszotp._0_Infraestructure;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._3_Domain.Pedido;
import com.example.facundo.recorridaszotp._5_Presentation.PedidoFragment;
import com.example.facundo.recorridaszotp._5_Presentation.PersonaFragment;
import com.example.facundo.recorridaszotp._5_Presentation.VisitaFragment;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        FragmentManager fm = getFragmentManager();
        String tag = this.getTag();
        if (tag != null) {
            switch (tag) {
                case Utils.DATE_PICKER_VISITA:
                    VisitaFragment vF = (VisitaFragment) fm.findFragmentByTag(Utils.FRAG_VISITA);
                    if (vF != null) {
                        vF.cargarFecha(dayOfMonth, monthOfYear + 1, year);
                    }
                    break;
                case Utils.DATE_PICKER_PERSONA:
                    PersonaFragment pF = (PersonaFragment) fm.findFragmentByTag(Utils.FRAG_PERSONA);
                    if (pF != null) {
                        pF.cargarFecha(dayOfMonth, monthOfYear + 1, year);
                    }
                    break;
                case Utils.DATE_PICKER_PEDIDO:
                    PedidoFragment pedidoFragment = (PedidoFragment) fm.findFragmentByTag(Utils.FRAG_PEDIDO);
                    if (pedidoFragment != null) {
                        pedidoFragment.cargarFecha(dayOfMonth, monthOfYear + 1, year);
                    }
                    break;
            }
        }
    }
}
