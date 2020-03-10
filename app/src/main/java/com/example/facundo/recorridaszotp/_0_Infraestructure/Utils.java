package com.example.facundo.recorridaszotp._0_Infraestructure;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public final class Utils {

//    public static final String WEB =  "https://prod-recorridaszo.rhcloud.com/";
 //   public static final String WEB = "https://dev-recorridaszo.rhcloud.com/";
  //  public static final String WEB = "https://stark-island-3497.herokuapp.com/";
    //public static final String WEB = "http://recorridaszo.ddnsking.com";
   // public static final String WEB = "http://31.220.58.188";
   // public static final String WEB = "http://recorridaszo.fundacionsi.org.ar";
    public static final String WEB = "https://recorridaszo.ddns.net";
    public static final String WEB_LOGIN = Utils.WEB +"/api/auth/sign_in";
    public static final String WEB_ENVIO_PERSONAS = Utils.WEB + "/api/people/upload";
    public static final String WEB_ENVIO_AREA = Utils.WEB + "/api/areas/upload";
    public static final String WEB_ENVIO_VISITAS = Utils.WEB + "/api/visits/upload";
    public static final String WEB_RECIBIR_AREAS = Utils.WEB + "/api/areas/download";
    public static final String WEB_RECIBIR_ZONAS = Utils.WEB + "/api/zones/download";
    public static final String WEB_RECIBIR_RANCHADAS = Utils.WEB + "/api/ranchadas/download";
    public static final String WEB_RECIBIR_FAMILIAS = Utils.WEB + "/api/familias/download";
    public static final String WEB_RECIBIR_PERSONAS = Utils.WEB + "/api/people/download";
    public static final String WEB_RECIBIR_PEDIDOS = Utils.WEB + "/api/pedidos/download";
    public static final String WEB_ENVIAR_PEDIDOS = Utils.WEB + "/api/pedidos/upload";
    public static final String WEB_RECIBIR_VISITAS = Utils.WEB + "/api/visits/download";
    public static final String WEB_RECIBIR_REFERENTES = Utils.WEB + "/api/referentes/download";
    //Permisos
    public static final String PUEDE_CREAR_PERSONA = "puedeCrearPersona";
    public static final String PUEDE_EDITAR_PERSONA = "puedeEditarPersona";
    public static final String PUEDE_BORRAR_PERSONA = "puedeBorrarPersona";
    public static final String PUEDE_CREAR_VISITA = "puedeCrearVisita";
    public static final String PUEDE_EDITAR_VISITA = "puedeEditarVisita";
    public static final String PUEDE_BORRAR_VISITA = "puedeBorrarVisita";
    public static final String PUEDE_VER_DATOS_PERSONALES = "puedeVerDatosPersonales";
    public static final String PUEDE_VER_ESTADOS = "puedeVerEstados";
    public static final String PUEDE_VER_SECTORES = "puedeVerSectores";
    ////////////////////////////////////////////////////////////////////////
    public static final int INVALID_TOKEN = 401;
    public static final int LOGIN_OK_CODE = 200;
//    public static final int LOGIN_ERROR = ;
    public static final String TOKEN_TYPE = "token-type";
    public static final String ACCESS_TOKEN = "access-token";
    public static final String CLIENT = "client";
    public static final String EXPIRY = "expiry";
    public static final String UID = "uid";
    public static final String BEARER = "Bearer";
    public static final String APPTAG = "recorridaszotpD";
    public static final String UltFechaSincr = "UltFechaSincr";
    public static final int MAX_INTENTOS = 3;
    public static final int EST_NUEVO = 0;
    public static final int EST_ACTUALIZADO = 1;
    public static final int EST_MODIFICADO = 2;
    public static final int EST_BORRADO = 3;
    public static final String FRAG_PERSONA = "Fragment Persona";
    public static final String FRAG_MAPA = "Fragment Mapa";
    public static final String FRAG_HOME = "Fragment Home";
    public static final String FRAG_VISITA = "Fragment Visita";
    public static final String FRAG_LOGIN = "Fragment Login";
    public static final String FRAG_PEDIDOS = "Fragment Pedidos";
    public static final String FRAG_PEDIDO = "Fragment Pedido";
    public static final String FRAG_NOTIFICACION = "Fragment Notificacion";
    public static final String FRAG_FILTROS = "Fragment Filtros";
    public static final String TITULO = "titulo";
    public static final String SUBTITULO = "subtitulo";
    public static final String DESCRIPCION = "descripcion";
    public static final String DATE_PICKER_VISITA = "Date Picker Visita";
    public static final String DATE_PICKER_PERSONA = "Date Picker Persona";
    public static final String DATE_PICKER_PEDIDO = "Date Picker Pedido";
    public static final int MAX_VISITAS = 20;
    public static final float ZOOM_STANDAR = 15.0f;
    public static final float ZOOM_ZONA = 13.0f;
    public static final String LISTA_PERSONAS = "Lista Personas";
    public static final String ULTIMAS_VISITAS = "Ultimas Visitas";
    public static final String PERSONA = "Persona";
    public static final String PEDIDOS = "Pedidos";
    public static final String PEDIDO = "Pedido";
    public static final String VISITA = "Visita";
    public static final String MAPA = "Mapa";
    public static final String HOME = "Recorridas";
    public static final String PREFS_NAME = "Mis Preferencias";
    public static final String USER_EMAIL = "User Email";
    public static final String USER_PASSWORD = "User Password";
    public static final String USER_IS_LOGIN = "User is login";
    public static final String USER_ROL_ID = "User Rol Id";
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final int ROL_ADMIN = 1;
    public static final int ROL_REFERENTE = 2;
    public static final int ROL_COORDINADOR = 3;
    public static final int ROL_VOLUNTARIO = 4;
    public static final int ROL_INVITADO = 5;
    public static final String ROL_ADMIN_STRING = "administrador";
    public static final String ROL_REFERENTE_STRING = "referente";
    public static final String ROL_COORDINADOR_STRING = "coordinador";
    public static final String ROL_VOLUNTARIO_STRING = "voluntario";
    public static final String ROL_INVITADO_STRING = "invitado";
    public static final String CODIGO_NOTIFICACION = "tipo";
    public static final String NOTIFICACION = "Notificación";
    public static final String FILTROS = "Filtros";
    public static final String PERSONA_ID = "persona_id";
    public static final String AREA_ID = "area_id";
    public static final String VERSION = "52";

    public static String toString(InputStream inputStream) throws Exception {
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        return total.toString();
    }

    public static String getDateTime(Long dateLong) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
        Date date = new Date(dateLong);
        return dateFormat.format(date);
    }
}
