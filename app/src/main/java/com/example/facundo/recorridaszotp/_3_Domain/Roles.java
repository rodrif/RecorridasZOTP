package com.example.facundo.recorridaszotp._3_Domain;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gonzalo on 09/04/16.
 */
/*
Admin:          1
Coordinador:    2
Referente:      3
Voluntario:     4
*/
public class Roles {
    private static Roles instance = null;
    private Map<Integer, String> permisos;
    public Roles() {
        permisos = new HashMap<Integer, String>();
        permisos.put(1, Utils.PUEDE_CREAR_PERSONA);
        permisos.put(1, Utils.PUEDE_EDITAR_PERSONA);
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

    public boolean hasPermission(int rolId, String permiso) {
            return this.getPermissions(rolId).matches(permiso);
    }
}
