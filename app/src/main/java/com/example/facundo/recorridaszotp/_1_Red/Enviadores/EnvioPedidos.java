package com.example.facundo.recorridaszotp._1_Red.Enviadores;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.example.facundo.recorridaszotp._0_Infraestructure.ExcepcionNoActualizoDB;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.BasicJsonUtil;
import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.PedidoJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Configuracion;
import com.example.facundo.recorridaszotp._3_Domain.Pedido;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by gonzalo on 12/02/17.
 */
public class EnvioPedidos extends BasicEnvio<Pedido> {

    public EnvioPedidos(List<Pedido> pedidos) {
        super(PedidoJsonUtils.get(), pedidos);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d(Utils.APPTAG, "EnvioPedidos::onPostExecute result: " + result);
        try {
            this.respuesta = new JSONObject(result);
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, "Envio pedidos respuestaJsonInvalida: " + ex.getMessage());
            return;
        }

        ActiveAndroid.beginTransaction();
        try {
            for (Pedido pedido : this.ts) {
                if (this.respuesta.getJSONObject("datos").optInt(pedido.getId().toString()) > 0 ) {
                    pedido.setWebId(this.respuesta.getJSONObject("datos").optInt(pedido.getId().toString()));
                } else {
                    Log.e(Utils.APPTAG, "Error: webId negativo en EnvioPedido::onPostExecute");
                    continue;
                }
                if (pedido.getEstado() != Utils.EST_BORRADO) {
                    pedido.setEstado(Utils.EST_ACTUALIZADO);
                    pedido.save();
                } else {
                    pedido.delete();
                }
            }
            Configuracion.guardar(getUltimaFechaMod(), this.respuesta.getString("fecha").toString());
            ActiveAndroid.setTransactionSuccessful();
        } catch (Exception ex) {
            Log.e(Utils.APPTAG, "falloEnviarVisitas: " + ex.getMessage());
        } finally {
            ActiveAndroid.endTransaction();
        }
    }
}
