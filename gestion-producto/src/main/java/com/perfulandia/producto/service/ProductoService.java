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

    /**
     * Convierte un modelo Producto a un DTO de Producto.
     * @param producto El producto a convertir.
     * @return El DTO de Producto con enlaces HATEOAS.
     */
    private ProductoDto toDTO(Producto producto) {
        ProductoDto productoDto = new ProductoDto(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecioUnitario(),
                producto.getCategoria(),
                producto.getActivo()
        );
        // Aquí puedes agregar enlaces HATEOAS
        // productoDto.add(linkTo(methodOn(ProductoController.class).obtener(productoDto.getId())).withSelfRel());
        // ProductoController es el controlador que maneja la solicitud, y esto proporciona el enlace al propio recurso.
        
        return productoDto;
    }

    /**
     * Convierte un DTO de Producto a un modelo Producto.
     * @param dto El DTO a convertir.
     * @return El modelo Producto correspondiente.
     */
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

    /**
     * Crea un nuevo Producto a partir del DTO.
     * @param dto El DTO con la información del producto.
     * @return El Producto recién creado convertido a DTO.
     */
    public ProductoDto crear(ProductoDto dto) {
        Producto producto = toEntity(dto);
        return toDTO(productoRepository.save(producto));
    }

    /**
     * Obtiene todos los productos de la base de datos.
     * @return Una lista de productos convertidos a DTO.
     */
    public List<ProductoDto> listar() {
        return productoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un producto por su ID.
     * @param id El ID del producto a obtener.
     * @return El Producto encontrado convertido a DTO.
     */
    public ProductoDto obtenerPorId(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return toDTO(producto);
    }

    /**
     * Actualiza un producto existente con el DTO proporcionado.
     * @param id El ID del producto a actualizar.
     * @param dto El DTO con la nueva información del producto.
     * @return El Producto actualizado convertido a DTO.
     */
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

    /**
     * Elimina un producto por su ID.
     * @param id El ID del producto a eliminar.
     */
    public void eliminar(Integer id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }
}
