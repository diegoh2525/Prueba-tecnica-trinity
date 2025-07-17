package com.prueba.trinity.Mapper;

import com.prueba.trinity.Dto.Request.ClienteRequest;
import com.prueba.trinity.Dto.Response.ClienteResponse;
import com.prueba.trinity.Entity.Cliente;

import java.time.LocalDate;

public class ClienteMapper {

    // dto-entidad
    public static Cliente toEntity(ClienteRequest dto) {
        return Cliente.builder()
                .tipoIdentificacion(dto.getTipoIdentificacion())
                .numeroIdentificacion(dto.getNumeroIdentificacion())
                .nombres(dto.getNombres())
                .apellidos(dto.getApellidos())
                .correoElectronico(dto.getCorreoElectronico())
                .fechaNacimiento(dto.getFechaNacimiento())
                .fechaCreacion(LocalDate.now())
                .build();
    }

    // dto-entidad para actualizar
    public static void updateEntity(Cliente cliente, ClienteRequest dto) {
        cliente.setTipoIdentificacion(dto.getTipoIdentificacion());
        cliente.setNumeroIdentificacion(dto.getNumeroIdentificacion());
        cliente.setNombres(dto.getNombres());
        cliente.setApellidos(dto.getApellidos());
        cliente.setCorreoElectronico(dto.getCorreoElectronico());
        cliente.setFechaNacimiento(dto.getFechaNacimiento());
        cliente.setFechaModificacion(LocalDate.now());
    }

    // entidad-dto
    public static ClienteResponse toResponseDTO(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getTipoIdentificacion(),
                cliente.getNumeroIdentificacion(),
                cliente.getNombres(),
                cliente.getApellidos(),
                cliente.getCorreoElectronico(),
                cliente.getFechaNacimiento(),
                cliente.getFechaCreacion(),
                cliente.getFechaModificacion()
        );
    }
}
