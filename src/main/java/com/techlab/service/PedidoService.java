package com.techlab.service;

import com.techlab.model.*;
import com.techlab.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PedidoService {
    @Autowired private PedidoRepository pedidoRepo;
    @Autowired private ProductoRepository productoRepo;

    @Transactional
    public Pedido crearPedido(List<LineaPedido> items) {
        Pedido pedido = new Pedido();

        for (LineaPedido item : items) {
            Producto prod = productoRepo.findById(item.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (prod.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente: " + prod.getNombre());
            }

            prod.setStock(prod.getStock() - item.getCantidad());
            productoRepo.save(prod);

            item.setPrecioUnitario(prod.getPrecio());
            item.setProducto(prod);
            pedido.agregarItem(item);
        }
        return pedidoRepo.save(pedido);
    }

    public List<Pedido> listar() { return pedidoRepo.findAll(); }
}