package com.perfulandia.producto.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDto extends RepresentationModel<ProductoDto> {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precioUnitario;
    private String categoria;
    private Boolean activo;
    
}
