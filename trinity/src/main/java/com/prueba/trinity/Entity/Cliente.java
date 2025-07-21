package com.prueba.trinity.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String tipoId;

    @NotBlank
    @Column(unique = true)
    private String numeroId;

    @NotBlank
    @Size(min = 2)
    private String nombres;

    @NotBlank
    @Size(min = 2)
    private String apellidos;

    @Email
    @NotBlank
    private String correo;

    @Past
    private LocalDate fechaNacimiento;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaModificacion;
}
