package com.techlab.servicios;

import com.techlab.pedidos.Pedido;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que encapsula la lógica de negocio para gestionar Pedidos.
 */
public class PedidoService {

    private List<Pedido> pedidosRealizados;

    public PedidoService() {
        this.pedidosRealizados = new ArrayList<>();
    }

    /**
     * Guarda un pedido confirmado en la lista.
     */
    public void guardarPedido(Pedido pedido) {
        pedidosRealizados.add(pedido);
        System.out.println("Pedido ID " + pedido.getId() + " creado exitosamente.");
    }

    /**
     * Devuelve la lista de todos los pedidos realizados.
     */
    public List<Pedido> listarPedidos() {
        return this.pedidosRealizados;
    }
}