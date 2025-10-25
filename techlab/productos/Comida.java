package com.techlab.productos;

import java.time.LocalDate;

public class Comida extends Producto {
    private LocalDate fechaVencimiento;

    public Comida(String nombre, double precio, int stock, LocalDate fechaVencimiento) {
        super(nombre, precio, stock);
        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    @Override
    public String toString() {
        // Demostración de Polimorfismo
        return "Comida [" +
                "ID: " + id +
                ", Nombre: '" + nombre + '\'' +
                ", Precio: $" + precio +
                ", Stock: " + stock +
                ", Vence: " + fechaVencimiento +
                ']';
    }
}
