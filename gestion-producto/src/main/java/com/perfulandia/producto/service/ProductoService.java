package com.perfulandia.producto.service;

import com.perfulandia.producto.model.Producto;
import com.perfulandia.producto.dto.ProductoDto;
import com.perfulandia.producto.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    private ProductoDto toDTO(Producto producto) {
        return new ProductoDto(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecioUnitario(),
                producto.getCategoria(),
                producto.getActivo()
        );
    }

    private Producto toEntity(ProductoDto dto) {
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecioUnitario(dto.getPrecioUnitario());
        producto.setCategoria(dto.getCategoria());
        producto.setActivo(dto.getActivo());
        return producto;
    }

    public ProductoDto crear(ProductoDto dto) {
        Producto producto = toEntity(dto);
        return toDTO(productoRepository.save(producto));
    }

    public List<ProductoDto> listar() {
        return productoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductoDto obtenerPorId(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return toDTO(producto);
    }

    public ProductoDto actualizar(Integer id, ProductoDto dto) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setPrecioUnitario(dto.getPrecioUnitario());
        existente.setCategoria(dto.getCategoria());
        existente.setActivo(dto.getActivo());

        return toDTO(productoRepository.save(existente));
    }

    public void eliminar(Integer id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }
}
