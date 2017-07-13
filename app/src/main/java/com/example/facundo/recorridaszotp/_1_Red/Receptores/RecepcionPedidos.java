public class RecepcionPedidos extends BasicRecepcion<Pedido> {

    public RecepcionPedidos() {
        super(PedidoDataAccess.get(), PedidoJsonUtils.get());
    }
}