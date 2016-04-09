package com.example.facundo.recorridaszotp._3_Domain;

/**
 * Created by gonzalo on 09/04/16.
 */
public class Roles {
    static private Roles instance = null;
    static public Roles getInstance() {
        if (instance == null) {
            instance = new Roles();
        }
        return instance;
    }

    static private String getPermissions(int rolId) {
        String resultado = "";
        switch (rolId) {
            case 1:
                resultado = "CrearUsuario,EditarUsuario";
                break;
        }
        return resultado;
    }

    static public boolean getPermission(String permiso) {
            return false;
    }
}
