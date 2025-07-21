package com.prueba.trinity.Mapper;

import com.prueba.trinity.Dto.Request.CuentaRequest;
import com.prueba.trinity.Dto.Response.CuentaResponse;
import com.prueba.trinity.Entity.Cliente;
import com.prueba.trinity.Entity.Cuenta;
import com.prueba.trinity.Entity.Enums.EstadoCuenta;
import com.prueba.trinity.Entity.Enums.TipoCuenta;

import java.time.LocalDateTime;
import java.util.Random;

public class CuentaMapper {

    public static Cuenta toEntity(CuentaRequest dto, Cliente cliente) {
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldo(dto.getSaldoInicial() != null ? dto.getSaldoInicial() : 0.0);
        cuenta.setExentaGmf(dto.getExentaGmf());
        cuenta.setCliente(cliente);
        cuenta.setEstado(EstadoCuenta.ACTIVA);
        cuenta.setFechaCreacion(LocalDateTime.now());
        cuenta.setFechaModificacion(LocalDateTime.now());

        String prefijo = (dto.getTipoCuenta() == TipoCuenta.AHORRO) ? "53" : "33";
        cuenta.setNumeroCuenta(prefijo + generarNumeroAleatorio(8));

        return cuenta;
    }

    public static CuentaResponse toResponse(Cuenta cuenta) {
        CuentaResponse response = new CuentaResponse();
        response.setId(cuenta.getId());
        response.setNumeroCuenta(cuenta.getNumeroCuenta());
        response.setTipoCuenta(cuenta.getTipoCuenta());
        response.setEstado(cuenta.getEstado());
        response.setSaldo(cuenta.getSaldo());
        response.setExentaGmf(cuenta.getExentaGmf());
        response.setFechaCreacion(cuenta.getFechaCreacion());
        response.setFechaModificacion(cuenta.getFechaModificacion());
        response.setClienteId(cuenta.getCliente().getId());
        return response;
    }

    private static String generarNumeroAleatorio(int digitos) {
        StringBuilder numero = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < digitos; i++) {
            numero.append(random.nextInt(10));
        }
        return numero.toString();
    }
}
