public class RecepcionPedidos extends BasicRecepcion<Pedido> {

    public RecepcionPedidos() {
        super(Utils.WEB_RECIBIR_PEDIDOS, PedidoDataAccess.get(), PedidoJsonUtils.get());
    }
}