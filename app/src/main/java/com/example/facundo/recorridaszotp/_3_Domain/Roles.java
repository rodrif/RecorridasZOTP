package com.example.facundo.recorridaszotp._3_Domain;

import android.util.Log;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gonzalo on 09/04/16.
 */
/*
Admin:          1
Referente:      2
Coordinador:    3
Voluntario:     4
Invitado:       5
*/
public class Roles {
    private static Roles instance = null;
    private Map<Integer, String> permisos;
    public Roles() {
        permisos = new HashMap<Integer, String>();

        //Admin
        permisos.put(1, Utils.PUEDE_CREAR_PERSONA + Utils.PUEDE_EDITAR_PERSONA + Utils.PUEDE_BORRAR_PERSONA
                + Utils.PUEDE_CREAR_VISITA + Utils.PUEDE_EDITAR_VISITA + Utils.PUEDE_BORRAR_VISITA
                + Utils.PUEDE_VER_TELEFONO_PERSONA);

        //Referente:
        permisos.put(2, Utils.PUEDE_CREAR_PERSONA + Utils.PUEDE_EDITAR_PERSONA
                + Utils.PUEDE_CREAR_VISITA + Utils.PUEDE_EDITAR_VISITA + Utils.PUEDE_BORRAR_VISITA
                + Utils.PUEDE_VER_TELEFONO_PERSONA);

        //Coordinador:
        permisos.put(3, Utils.PUEDE_CREAR_PERSONA + Utils.PUEDE_EDITAR_PERSONA + Utils.PUEDE_BORRAR_PERSONA
                + Utils.PUEDE_CREAR_VISITA + Utils.PUEDE_EDITAR_VISITA + Utils.PUEDE_BORRAR_VISITA
                + Utils.PUEDE_VER_TELEFONO_PERSONA);

        //Voluntario:
        permisos.put(4, Utils.PUEDE_CREAR_PERSONA
                + Utils.PUEDE_CREAR_VISITA + Utils.PUEDE_EDITAR_VISITA);
    }
    
    static public Roles getInstance() {
        if (instance == null) {
            instance = new Roles();
        }
        return instance;
    }

    private String getPermissions(int rolId) {
        return permisos.get(rolId);
    }

    public boolean hasPermission(String permiso) {
        String permisos = this.getPermissions(Config.getInstance().getRol());
        if (permisos != null ) {
            boolean resultado = permisos.matches(".*" + permiso + ".*");
            Log.v(Utils.APPTAG, "Permiso: " + permiso + " " + resultado);
            return resultado;
        } else {
            Log.e(Utils.APPTAG, "Roles: permisos null");
        }
        return false;
    }

    public boolean hasPermission(int rolId, String permiso) {
            return this.getPermissions(rolId).matches(permiso);
    }
}
