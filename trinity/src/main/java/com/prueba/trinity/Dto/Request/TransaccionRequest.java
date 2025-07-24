package com.prueba.trinity.Dto.Request;

import com.prueba.trinity.Entity.Enums.TipoTransaccion;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionRequest {

    @NotNull
    private TipoTransaccion tipo;

    @NotNull
    private Double monto;

    private String descripcion;

    @NotNull
    private Long cuentaOrigenId;

    private Long cuentaDestinoId; // solo en trnsferencias
}
