package com.example.facundo.recorridaszotp._2_DataAccess;

/**
 * Created by gonzalo on 13/04/16.
 */
public class Config {
    private static Config instance = null;
    private static int rol;

    static public Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public static int getRol() {
        return rol;
    }

    public static void setRol(int rol) {
        Config.rol = rol;
    }
}
