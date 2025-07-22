package com.prueba.trinity.Dto.Response;

import com.prueba.trinity.Entity.Enums.TipoTransaccion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionResponse {

    private Long id;
    private TipoTransaccion tipo;
    private Double monto;
    private String descripcion;
    private LocalDateTime fecha;
    private Long cuentaOrigenId;
    private Long cuentaDestinoId;
}
