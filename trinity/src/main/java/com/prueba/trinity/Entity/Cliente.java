package com.prueba.trinity.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "clientes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String tipoIdentificacion;

    @NotBlank
    @Column(unique = true)
    private String numeroIdentificacion;

    @NotBlank
    @Size(min = 2)
    private String nombres;

    @NotBlank
    @Size(min = 2)
    private String apellidos;

    @Email
    @NotBlank
    private String correoElectronico;

    @Past
    private LocalDate fechaNacimiento;

    private LocalDate fechaCreacion;

    private LocalDate fechaModificacion;
}
