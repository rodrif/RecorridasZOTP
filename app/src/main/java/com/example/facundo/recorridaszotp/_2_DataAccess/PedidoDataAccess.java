package com.example.facundo.recorridaszotp._2_DataAccess;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._3_Domain.Pedido;

import java.util.List;

/**
 * Created by gonzalo on 05/01/17.
 */
public class PedidoDataAccess extends BasicDataAccess<Pedido> {

    private static PedidoDataAccess ourInstance = new PedidoDataAccess();

    public static PedidoDataAccess get() {
        return ourInstance;
    }

    private PedidoDataAccess() {
    }

    public Class getClase() {
        return Pedido.class;
    }

    public int acualizarDB(List<Pedido> pedidos) throws Exception {
        int resultado = -1;
        ActiveAndroid.beginTransaction();
        try {
            for (Pedido pedido : pedidos) {
                Pedido p = new Select()
                        .from(Pedido.class)
                        .where("WebId = ?", pedido.getWebId())
                        .executeSingle();
                if (p != null && pedido.getEstado() == Utils.EST_BORRADO) {
                    p.delete();
                } else if (p != null) {
                    p.mergeFromWeb(pedido);
                    p.save();
                } else if (pedido.getEstado() != Utils.EST_BORRADO) {
                    pedido.save();
                }
            }
            ActiveAndroid.setTransactionSuccessful();
            resultado = 0;
        } finally {
            ActiveAndroid.endTransaction();
        }
        return resultado;
    }
}
