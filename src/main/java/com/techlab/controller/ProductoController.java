package com.techlab.controller;

import com.techlab.model.Producto;
import com.techlab.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    @Autowired private ProductoService service;

    @GetMapping
    public List<Producto> listar() { return service.listar(); }

    @PostMapping
    public Producto guardar(@RequestBody Producto p) { return service.guardar(p); }

    @GetMapping("/{id}")
    public Producto obtener(@PathVariable Long id) { return service.buscarPorId(id).orElse(null); }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}