package com.prueba.trinity.Dto.Response;

import com.prueba.trinity.Entity.Enums.EstadoCuenta;
import com.prueba.trinity.Entity.Enums.TipoCuenta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuentaResponse {
    private Long id;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private EstadoCuenta estado;
    private Double saldo;
    private Boolean exentaGmf;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private Long clienteId;
}
