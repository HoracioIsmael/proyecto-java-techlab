package com.techlab.productos;

import java.util.Objects;

/**
 * Clase base que representa un Producto.
 * Utiliza un contador estático para autogenerar IDs.
 */
public class Producto {
    // Contador estático para IDs autoincrementables
    private static int contadorProductos = 0;

    protected int id;
    protected String nombre;
    protected double precio;
    protected int stock;

    public Producto(String nombre, double precio, int stock) {
        // Validación de datos en el constructor
        if (precio <= 0 || stock < 0) {
            throw new IllegalArgumentException("El precio debe ser positivo y el stock no puede ser negativo.");
        }
        this.id = ++contadorProductos;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // --- Getters ---
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    // --- Setters con validación ---
    public void setPrecio(double precio) {
        if (precio > 0) {
            this.precio = precio;
        } else {
            System.out.println("Error: El precio debe ser positivo.");
        }
    }

    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
        } else {
            System.out.println("Error: El stock no puede ser negativo.");
        }
    }

    /**
     * Método para reducir el stock. Es crucial para los pedidos[cite:
     * 
     * @param cantidad La cantidad a reducir.
     * @throws StockInsuficienteException Si la cantidad es mayor al stock.
     */
    public void reducirStock(int cantidad) throws StockInsuficienteException {
        if (cantidad > this.stock) {
            throw new StockInsuficienteException("Stock insuficiente para " + this.nombre + ". Solicitados: " + cantidad
                    + ", Disponibles: " + this.stock);
        }
        this.stock -= cantidad;
    }

    @Override
    public String toString() {
        // Formato para listar productos
        return "Producto [" +
                "ID: " + id +
                ", Nombre: '" + nombre + '\'' +
                ", Precio: $" + precio +
                ", Stock: " + stock +
                ']';
    }

    // Métodos equals y hashCode para facilitar búsquedas y eliminaciones
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Producto producto = (Producto) o;
        return id == producto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}