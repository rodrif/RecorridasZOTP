package com.example.facundo.recorridaszotp._1_Red.Receptores;

import com.example.facundo.recorridaszotp._0_Infraestructure.JsonUtils.PedidoJsonUtils;
import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;
import com.example.facundo.recorridaszotp._1_Red.Receptores.BasicRecepcion;
import com.example.facundo.recorridaszotp._2_DataAccess.PedidoDataAccess;
import com.example.facundo.recorridaszotp._3_Domain.Pedido;

public class RecepcionPedidos extends BasicRecepcion<Pedido> {

    public RecepcionPedidos() {
        super(Utils.WEB_RECIBIR_PEDIDOS, PedidoDataAccess.get(), PedidoJsonUtils.get());
    }
}