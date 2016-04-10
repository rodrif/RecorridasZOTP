package com.example.facundo.recorridaszotp._3_Domain;

/**
 * Created by gonzalo on 09/04/16.
 */
public class Roles {
    private Roles instance = null;
    private Map<Integer, String> permisos;
    public Roles() {
        permisos = new HashMap<Integer, String>();
        permisos.put(1, Utils.EITAR_PERSONA);
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
