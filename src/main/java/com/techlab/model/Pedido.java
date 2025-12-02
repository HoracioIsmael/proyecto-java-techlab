package com.techlab.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;
    private Double total;
    private String estado; // Ejemplo: "PENDIENTE", "CONFIRMADO", "ENVIADO"

    // Relación OneToMany: Un pedido tiene muchas líneas
    // mappedBy = "pedido" indica que la clase LineaPedido es la dueña de la relación (campo 'pedido')
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<LineaPedido> items = new ArrayList<>();

    // --- CONSTRUCTORES ---

    public Pedido() {
        this.fecha = LocalDateTime.now();
        this.total = 0.0;
        this.estado = "PENDIENTE";
    }

    // --- MÉTODOS DE NEGOCIO ---

    // Método helper para mantener la consistencia de la relación bidireccional
    public void agregarItem(LineaPedido item) {
        items.add(item);
        item.setPedido(this); // Vincula este pedido a la línea

        // Sumamos al total del pedido
        // (Asegúrate de que item tenga precio y cantidad seteados antes de llamar a esto)
        if (item.getSubtotal() != null) {
            this.total += item.getSubtotal();
        }
    }

    // --- GETTERS Y SETTERS MANUALES ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<LineaPedido> getItems() {
        return items;
    }

    public void setItems(List<LineaPedido> items) {
        this.items = items;
    }
}