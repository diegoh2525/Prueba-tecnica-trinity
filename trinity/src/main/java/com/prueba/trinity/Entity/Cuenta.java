package com.prueba.trinity.Entity;

import com.prueba.trinity.Entity.Enums.EstadoCuenta;
import com.prueba.trinity.Entity.Enums.TipoCuenta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cuentas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta; // AHORRO, CORRIENTE

    @Column(unique = true, length = 10)
    private String numeroCuenta;

    @Enumerated(EnumType.STRING)
    private EstadoCuenta estado; // ACTIVA, INACTIVA, CANCELADA

    private Double saldo;

    private Boolean exentaGmf;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
