package com.techlab.service;

import com.techlab.model.Producto;
import com.techlab.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository repository;

    public List<Producto> listar() { return repository.findAll(); }
    public Producto guardar(Producto p) { return repository.save(p); }
    public Optional<Producto> buscarPorId(Long id) { return repository.findById(id); }
    public void eliminar(Long id) { repository.deleteById(id); }
}