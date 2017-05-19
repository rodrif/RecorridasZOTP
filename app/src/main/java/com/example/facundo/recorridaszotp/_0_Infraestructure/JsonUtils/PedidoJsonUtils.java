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
        try {
           JSONObject jsonObj = new JSONObject();
            jsonObj.put("android_id", pedido.getId());
            jsonObj.put("web_id", pedido.getWebId());
            jsonObj.put("estado", pedido.getEstado());
            jsonObj.put("descripcion", pedido.getDescripcion());
            jsonObj.put("completado", pedido.getCompletado());
            jsonObj.put("fecha", pedido.getFecha());
            Persona persona = pedido.getPersona();
            if (persona != null)
                jsonObj.put("web_person_id", persona.getWebId());
            return jsonObj;

        } catch (Exception ex) {
            Log.e(Utils.APPTAG, "Fallo al crear PedidoJSON");
            throw ex;
        }
    }
}
