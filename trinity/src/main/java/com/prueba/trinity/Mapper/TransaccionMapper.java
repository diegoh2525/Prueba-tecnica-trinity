package com.prueba.trinity.Mapper;

import com.prueba.trinity.Dto.Response.TransaccionResponse;
import com.prueba.trinity.Entity.Transaccion;

public class TransaccionMapper {

    public static TransaccionResponse toResponse(Transaccion t) {
        TransaccionResponse dto = new TransaccionResponse();
        dto.setId(t.getId());
        dto.setTipo(t.getTipo());
        dto.setMonto(t.getMonto());
        dto.setDescripcion(t.getDescripcion());
        dto.setFecha(t.getFecha());
        dto.setCuentaOrigenId(t.getCuentaOrigen() != null ? t.getCuentaOrigen().getId() : null);
        dto.setCuentaDestinoId(t.getCuentaDestino() != null ? t.getCuentaDestino().getId() : null);
        return dto;
    }
}
