package com.prueba.trinity.Mapper;

import com.prueba.trinity.Dto.Request.ClienteRequest;
import com.prueba.trinity.Dto.Response.ClienteResponse;
import com.prueba.trinity.Entity.Cliente;

import java.time.LocalDateTime;

public class ClienteMapper {

    // dto-entidad
    public static Cliente toEntity(ClienteRequest dto) {
        return Cliente.builder()
                .tipoId(dto.getTipoId())
                .numeroId(dto.getNumeroId())
                .nombres(dto.getNombres())
                .apellidos(dto.getApellidos())
                .correo(dto.getCorreo())
                .fechaNacimiento(dto.getFechaNacimiento())
                .fechaCreacion(LocalDateTime.now())
                .build();
    }

    // entidad-dto
    public static ClienteResponse toResponseDTO(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getTipoId(),
                cliente.getNumeroId(),
                cliente.getNombres(),
                cliente.getApellidos(),
                cliente.getCorreo(),
                cliente.getFechaNacimiento(),
                cliente.getFechaCreacion(),
                cliente.getFechaModificacion()
        );
    }
}
