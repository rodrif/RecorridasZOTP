package com.example.facundo.recorridaszotp._5_Presentation;

import android.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;

import com.example.facundo.recorridaszotp.R;
import com.example.facundo.recorridaszotp._0_Infraestructure.Handlers.PedidoHandler;
import com.example.facundo.recorridaszotp._0_Infraestructure.PersonaShare;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._0_Infraestructure.popUp;
import com.example.facundo.recorridaszotp._3_Domain.Persona;

/**
 * Created by gonzalo on 09/11/17.
 */

public class MenuSuperior {

    public static boolean seleccionar(MenuItem item, MainActivity activity) {
        switch (item.getItemId()) {
            case android.R.id.home:
                activity.getNavDrawerLayout().openDrawer(GravityCompat.START);
                break;
            case R.id.action_guardar: //Guardar
                //Busco que fragment se esta usando actualmente
                FragmentManager fm = activity.getFragmentManager();
                int cant = fm.getBackStackEntryCount();
                FragmentManager.BackStackEntry bse = fm.getBackStackEntryAt(cant - 1);
                String tag = bse.getName();
                if (tag != null) {
                    switch (tag) {
                        case Utils.FRAG_PERSONA:
                            activity.GuardarPersonaClickFormulario();
                            return true;
                        case Utils.FRAG_VISITA:
                            activity.GuardarVisitaClickFormulario();
                            return true;
                        case Utils.FRAG_PEDIDO:
                            activity.GuardarPedidoClickFormulario();
                            return true;
                    }
                }
            case R.id.action_borrar: //Borrar
                ((popUp) (activity.getFragmentManager().findFragmentById(R.id.content_frame))).popUp();
                break;
            case R.id.action_compartir: //compartir
                if (activity.personaSeleccionada != null) {
                    new PersonaShare(activity.personaSeleccionada).share(activity);
                }
                break;
            case R.id.action_pedidos: //pedidos
                long personaId = -1;
                if (activity.personaSeleccionada != null) {
                    personaId = activity.personaSeleccionada.getId();
                } else {
                    personaId = activity.visitaSeleccionada.getPersona().getId();
                }
                new PedidoHandler().listarPedidos(personaId, activity);
                break;
            case R.id.action_crear_pedido: //crear pedido
                Persona persona = null;
                if (activity.personaSeleccionada != null) {
                    persona = activity.personaSeleccionada;
                } else {
                    persona = activity.visitaSeleccionada.getPersona();
                }
                new PedidoHandler().crearPedido(persona, activity);
                break;
        }
        return true;
    }
}