package com.prueba.trinity.Dto.Request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {
    private String tipoId;
    private String numeroId;
    private String nombres;
    private String apellidos;
    private String correo;
    private LocalDate fechaNacimiento;
}