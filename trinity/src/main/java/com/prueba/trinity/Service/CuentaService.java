package com.prueba.trinity.Service;

import com.prueba.trinity.Dto.Request.CuentaRequest;
import com.prueba.trinity.Dto.Response.CuentaResponse;
import com.prueba.trinity.Entity.Cliente;
import com.prueba.trinity.Entity.Cuenta;
import com.prueba.trinity.Entity.Enums.EstadoCuenta;
import com.prueba.trinity.Entity.Enums.TipoCuenta;
import com.prueba.trinity.Mapper.CuentaMapper;
import com.prueba.trinity.Repository.ClienteRepository;
import com.prueba.trinity.Repository.CuentaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;

    public CuentaService(CuentaRepository cuentaRepository, ClienteRepository clienteRepository) {
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
    }

    public CuentaResponse crearCuenta(CuentaRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        if (request.getTipoCuenta() == TipoCuenta.AHORRO && request.getSaldoInicial() < 0) {
            throw new IllegalArgumentException("El saldo inicial para cuentas de ahorro no puede ser negativa");
        }

        Cuenta cuenta = CuentaMapper.toEntity(request, cliente);
        while (cuentaRepository.existsByNumeroCuenta(cuenta.getNumeroCuenta())) {
            cuenta.setNumeroCuenta(generarNuevoNumero(cuenta.getTipoCuenta()));
        }

        Cuenta guardada = cuentaRepository.save(cuenta);
        return CuentaMapper.toResponse(guardada);
    }

    public List<CuentaResponse> listarCuentas() {
        return cuentaRepository.findAll().stream()
                .map(CuentaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public CuentaResponse obtenerCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
        return CuentaMapper.toResponse(cuenta);
    }

    public CuentaResponse actualizarEstado(Long id, EstadoCuenta nuevoEstado) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));

        if (nuevoEstado == EstadoCuenta.CANCELADA && cuenta.getSaldo() > 0) {
            throw new IllegalStateException("No se puede cancelar una cuenta con saldo mayor a 0");
        }

        cuenta.setEstado(nuevoEstado);
        cuenta.setFechaModificacion(LocalDateTime.now());
        Cuenta actualizada = cuentaRepository.save(cuenta);
        return CuentaMapper.toResponse(actualizada);
    }

    public void eliminarCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));

        if (cuenta.getSaldo() > 0) {
            throw new IllegalStateException("No se puede eliminar una cuenta con saldo mayor a 0");
        }

        cuentaRepository.delete(cuenta);
    }

    private String generarNuevoNumero(TipoCuenta tipo) {
        String prefijo = (tipo == TipoCuenta.AHORRO) ? "53" : "33";
        return prefijo + new Random().ints(8, 0, 10)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }
}
