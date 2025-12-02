package com.techlab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "lineas_pedido")
public class LineaPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Integer cantidad;

    // Guardamos el precio al momento de la compra por si cambia después
    private Double precioUnitario;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnore // Importante: Evita que al pedir un pedido se genere un bucle infinito
    private Pedido pedido;

    // --- Constructor Vacío (Obligatorio para JPA) ---
    public LineaPedido() {}

    // --- Constructor Útil ---
    public LineaPedido(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
    }

    // --- Método de Negocio ---
    public Double getSubtotal() {
        if (precioUnitario != null && cantidad != null) {
            return precioUnitario * cantidad;
        }
        return 0.0;
    }

    // --- GETTERS Y SETTERS MANUALES ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}