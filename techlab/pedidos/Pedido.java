package com.techlab.pedidos;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un Pedido
 * Contiene una lista de LineaPedido
 */
public class Pedido {
    private static int contadorPedidos = 0;

    private int id;
    private List<LineaPedido> lineas;
    private double costoTotal;

    public Pedido() {
        this.id = ++contadorPedidos;
        this.lineas = new ArrayList<>();
        this.costoTotal = 0.0;
    }

    public void agregarLinea(LineaPedido linea) {
        this.lineas.add(linea);
        this.recalcularCostoTotal();
    }

    /**
     * Calcula el costo total del pedido
     */
    private void recalcularCostoTotal() {
        this.costoTotal = 0.0;
        for (LineaPedido linea : this.lineas) {
            this.costoTotal += linea.calcularSubtotal();
        }
    }

    public int getId() {
        return id;
    }

    public List<LineaPedido> getLineas() {
        return lineas;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    @Override
    public String toString() {
        // Formato para listar pedidos
        StringBuilder sb = new StringBuilder();
        sb.append("==============================\n");
        sb.append("Pedido ID: ").append(id).append("\n");
        sb.append("Costo Total: $").append(String.format("%.2f", costoTotal)).append("\n");
        sb.append("Productos:\n");
        for (LineaPedido linea : lineas) {
            sb.append(linea.toString()).append("\n");
        }
        sb.append("==============================\n");
        return sb.toString();
    }
}