package com.example.facundo.recorridaszotp._1_Infraestructure;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public final class Utils {
    public static final String WEB = "http://stark-island-3497.herokuapp.com";
    public static final String WEB_INSERTAR = Utils.WEB + "/people/mobGuardarNuevasPersonasPost";
    public static final String WEB_SINCRONIZAR = Utils.WEB + "/people/mobSincronizarPersonas";
    public static final int MAX_INTENTOS = 3;
    public static final String APPTAG = "recorridaszotp";

    public static final String WEB_BORRAR = Utils.WEB + ".atwebpages.com/borrar.php";
    public static final String WEB_BORRAR_DB = Utils.WEB + ".atwebpages.com/borrartodo.php";
    public static final String WEB_ACTUALIZAR = Utils.WEB + ".atwebpages.com/actualizar.php";
    public static final String WEB_CARGAR_PERSONAS_PRUEBA = Utils.WEB + ".atwebpages.com/inicializar.php";
    public static final String[] camposBD = new String[]{"id", "nombre",
            "apellido", "direccion", "zona", "descripcion", "latitud",
            "longitud", "ultMod", "estado"};
    public static final String TPersonas = "Personas";
    public static final String EST_ACTUALIZADO = "Actualizado";
    public static final String EST_BORRADO = "Borrado";
    public static final String EST_MODIFICADO = "Modificado";
    public static final String EST_NUEVO = "Nuevo";
    public static final String FECHA_CERO = "0000-00-00T00:00:00-0000";
    public static final int ZOOM_CERCA = 15;
    public static final int ZOOM_LEJOS = 10;
    public static final int MENU_MAPA_SINCRONIZAR = 1;
    public static final int MENU_MAPA_REFRESCAR_PANTALLA = 2;
    public static final int MENU_MAPA_BORRARDBLOCAL = 3;
    public static final int MENU_MAPA_SUBIRALSERVER = 4;
    public static final String KEY_LATITUD = "com.recorridaszo.recorridaszo.KEY_LATITUD";
    public static final String KEY_LONGITUD = "com.recorridaszo.recorridaszo.KEY_LONGITUD";
    public final static int REQ_CODE_FORMULARIO = 9000;
    public static final double PRECISION = 0.0000000000002;
    public static final int CANTIDAD_INTENTOS_UBICACION = 5;
    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
