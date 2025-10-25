package com.techlab.pedidos;

import com.techlab.productos.Producto;

/**
 * Objeto intermedio para asociar un Producto con una cantidad
 * dentro de un Pedido
 */
public class LineaPedido {
    private Producto producto;
    private int cantidad;

    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double calcularSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return "\t- " + producto.getNombre() +
                " (Cant: " + cantidad +
                ", Subtotal: $" + String.format("%.2f", calcularSubtotal()) + ")";
    }
}