package com.prueba.trinity.Repository;

import com.prueba.trinity.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByNumeroId(String numeroIdentificacion);
}
