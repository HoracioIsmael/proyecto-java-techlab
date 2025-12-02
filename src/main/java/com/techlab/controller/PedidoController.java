package com.techlab.controller;

import com.techlab.model.*;
import com.techlab.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {
    @Autowired private PedidoService service;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody List<LineaPedido> items) {
        try {
            return ResponseEntity.ok(service.crearPedido(items));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Pedido> listar() { return service.listar(); }
}