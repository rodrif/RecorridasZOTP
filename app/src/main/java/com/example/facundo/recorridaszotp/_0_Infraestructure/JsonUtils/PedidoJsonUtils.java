package com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils;

import com.activeandroid.util.Log;
import com.example.facundo.recorridaszotp._0_Infraestructure.ExcepcionNoActualizoDB;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._2_DataAccess.PedidoDataAccess;
import com.example.facundo.recorridaszotp._2_DataAccess.PersonaDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Pedido;
import com.example.facundo.recorridaszotp._3_Domain.Persona;

import org.json.JSONObject;

/**
 * Created by gonzalo on 10/01/17.
 */
public class PedidoJsonUtils extends BasicJsonUtil<Pedido> {

    private static PedidoJsonUtils ourInstance = new PedidoJsonUtils();

    public static PedidoJsonUtils get() {
        return ourInstance;
    }

    private PedidoJsonUtils() {
    }

    @Override
    public Class getClase() {
        return Pedido.class;
    }

    @Override
    public String toJSonAEnviar(Pedido pedido) throws Exception {
        String aEnviar = toJsonObject(pedido).toString();
        return aEnviar;
    }

    @Override
    public Pedido fromJsonObject(JSONObject pedidoJson) throws Exception {
        if (pedidoJson.optInt("web_id") == -1) {
            throw new ExcepcionNoActualizoDB();
        }
        Pedido pedido = new Pedido(PersonaDataAccess.get().findByWebId(pedidoJson.optInt("web_person_id")));
        pedido.setWebId(pedidoJson.optInt("web_id"));
        pedido.setEstado(pedidoJson.optInt("estado"));
        pedido.setDescripcion(pedidoJson.optString("descripcion"));
        pedido.setCompletado(pedidoJson.optBoolean("completado"));
        pedido.setFecha(pedidoJson.optLong("fecha"));
        return pedido;
    }

    @Override
    public JSONObject toJsonObject(Pedido pedido) throws Exception {
        //FIXME en pedidoJsonUtils

        try {
           JSONObject jsonObj = new JSONObject();
   /*          jsonObj.put("android_id", persona.getId());
            jsonObj.put("web_id", persona.getWebId());
            jsonObj.put("nombre", persona.getNombre());
            jsonObj.put("apellido", persona.getApellido());
            jsonObj.put("estado", persona.getEstado());
            if (persona.getZona() != null) { // por si esta borrado
                jsonObj.put("web_zone_id", persona.getZona().getWebId());
            }
            jsonObj.put("fecha_nacimiento", persona.getFechaNacimiento());
            jsonObj.put("descripcion", persona.getObservaciones());
            jsonObj.put("dni", persona.getDNI());
            jsonObj.put("telefono", persona.getTelefono());
            jsonObj.put("remera", persona.getRemera());
            jsonObj.put("pantalon", persona.getPantalon());
            jsonObj.put("zapatillas", persona.getZapatillas());
            if (persona.getFamilia() != null) {
                jsonObj.put("web_familia_id", persona.getFamilia().getWebId());
            }
            if (persona.getRanchada() != null) {
                jsonObj.put("web_ranchada_id", persona.getRanchada().getWebId());
            }*/

            return jsonObj;

        } catch (Exception ex) {
            Log.e(Utils.APPTAG, "Fallo al crear PersonaJSON");
            throw ex;
        }
    }
}
