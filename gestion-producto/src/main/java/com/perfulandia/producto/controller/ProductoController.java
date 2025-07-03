package com.perfulandia.producto.controller;

import com.perfulandia.producto.dto.ProductoDto;
import com.perfulandia.producto.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    /**
     * Crear un producto
     * @param dto ProductoDto con los datos del nuevo producto
     * @return ProductoDto creado con enlaces HATEOAS
     */
    @PostMapping
    public ResponseEntity<ProductoDto> crear(@RequestBody ProductoDto dto) {
        ProductoDto producto = service.crear(dto);
        // Agregar enlace HATEOAS para obtener el producto creado
        producto.add(linkTo(methodOn(ProductoController.class).obtener(producto.getId())).withSelfRel());
        return ResponseEntity.ok(producto);
    }

    /**
     * Obtener la lista de productos
     * @return Lista de ProductoDto con enlaces HATEOAS
     */
    @GetMapping
    public ResponseEntity<List<ProductoDto>> listar() {
        List<ProductoDto> lista = service.listar();
        // Agregar enlaces HATEOAS a cada producto de la lista
        lista.forEach(producto -> 
            producto.add(linkTo(methodOn(ProductoController.class).obtener(producto.getId())).withSelfRel())
        );
        return ResponseEntity.ok(lista);
    }

    /**
     * Obtener un producto por su ID
     * @param id ID del producto a obtener
     * @return ProductoDto con enlaces HATEOAS
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> obtener(@PathVariable Integer id) {
        ProductoDto producto = service.obtenerPorId(id);
        // Agregar enlace HATEOAS para el producto obtenido
        producto.add(linkTo(methodOn(ProductoController.class).obtener(id)).withSelfRel());
        return ResponseEntity.ok(producto);
    }

    /**
     * Actualizar un producto existente
     * @param id ID del producto a actualizar
     * @param dto ProductoDto con los nuevos datos
     * @return ProductoDto actualizado con enlaces HATEOAS
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> actualizar(@PathVariable Integer id, @RequestBody ProductoDto dto) {
        ProductoDto producto = service.actualizar(id, dto);
        // Agregar enlace HATEOAS para el producto actualizado
        producto.add(linkTo(methodOn(ProductoController.class).obtener(id)).withSelfRel());
        return ResponseEntity.ok(producto);
    }

    /**
     * Eliminar un producto por su ID
     * @param id ID del producto a eliminar
     * @return Respuesta vacía (204 No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
