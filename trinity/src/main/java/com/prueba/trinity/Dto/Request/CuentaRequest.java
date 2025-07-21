package com.prueba.trinity.Dto.Request;

import com.prueba.trinity.Entity.Enums.TipoCuenta;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuentaRequest {
    @NotNull
    private TipoCuenta tipoCuenta;

    private Double saldoInicial;

    private Boolean exentaGmf;

    @NotNull
    private Long clienteId;
}

