package com.techlab.servicios;

import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.productos.Producto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que encapsula la lógica de negocio para gestionar Productos.
 * Maneja la colección de productos.
 */
public class ProductoService {

    private List<Producto> inventario;

    public ProductoService() {
        this.inventario = new ArrayList<>();

    /**
     * Agrega un producto al inventario.
     */
    public void agregarProducto(Producto producto) {
        inventario.add(producto);
        System.out.println("Producto '" + producto.getNombre() + "' agregado exitosamente.");
    }

    /**
     * Devuelve la lista completa de productos.
     */
    public List<Producto> listarProductos() {
        return this.inventario;
    }

    /**
     * Busca un producto por su ID.
     * 
     * @return un Optional<Producto> para manejar si se encuentra o no.
     */
    public Optional<Producto> buscarProductoPorId(int id) {
        for (Producto p : inventario) {
            if (p.getId() == id) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    /**
     * Actualiza el stock o precio de un producto.
     */
    public boolean actualizarProducto(int id, double nuevoPrecio, int nuevoStock) {
        Optional<Producto> productoOpt = buscarProductoPorId(id);
        if (productoOpt.isPresent()) {
            Producto p = productoOpt.get();
            // Validación de coherencia
            if (nuevoPrecio > 0)
                p.setPrecio(nuevoPrecio);
            if (nuevoStock >= 0)
                p.setStock(nuevoStock);
            System.out.println("Producto ID " + id + " actualizado.");
            return true;
        }
        return false;
    }

    /**
     * Elimina un producto del inventario.
     */
    public boolean eliminarProducto(int id) {
        Optional<Producto> productoOpt = buscarProductoPorId(id);
        if (productoOpt.isPresent()) {
            inventario.remove(productoOpt.get());
            System.out.println("Producto '" + productoOpt.get().getNombre() + "' eliminado.");
            return true;
        }
        return false;
    }

    /**
     * Valida si hay stock y lo reduce si se confirma el pedido
     */
    public void validarYReducirStock(int id, int cantidad) throws StockInsuficienteException {
        Producto p = buscarProductoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto con ID " + id + " no encontrado."));

        // El método reducirStock de Producto lanza la excepción si no hay
        p.reducirStock(cantidad);
    }
}
