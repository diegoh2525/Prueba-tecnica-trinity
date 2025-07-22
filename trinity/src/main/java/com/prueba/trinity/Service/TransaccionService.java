package com.prueba.trinity.Service;

import com.prueba.trinity.Dto.Request.TransaccionRequest;
import com.prueba.trinity.Dto.Response.TransaccionResponse;
import com.prueba.trinity.Entity.Cuenta;
import com.prueba.trinity.Entity.Enums.TipoTransaccion;
import com.prueba.trinity.Entity.Transaccion;
import com.prueba.trinity.Mapper.TransaccionMapper;
import com.prueba.trinity.Repository.CuentaRepository;
import com.prueba.trinity.Repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final CuentaRepository cuentaRepository;

    public TransaccionResponse realizarTransaccion(TransaccionRequest request) {
        Cuenta cuentaOrigen = cuentaRepository.findById(request.getCuentaOrigenId())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta origen no encontrada"));

        Cuenta cuentaDestino = null;
        if (request.getTipo() == TipoTransaccion.TRANSFERENCIA) {
            if (request.getCuentaDestinoId() == null) {
                throw new IllegalArgumentException("Debe proporcionar cuenta destino para una transferencia");
            }
            cuentaDestino = cuentaRepository.findById(request.getCuentaDestinoId())
                    .orElseThrow(() -> new IllegalArgumentException("Cuenta destino no encontrada"));
        }

        Double monto = request.getMonto();
        if (monto <= 0) throw new IllegalArgumentException("Monto debe ser mayor a 0");

        Transaccion transaccion = new Transaccion();
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setTipo(request.getTipo());
        transaccion.setDescripcion(request.getDescripcion());
        transaccion.setMonto(monto);
        transaccion.setCuentaOrigen(cuentaOrigen);
        transaccion.setCuentaDestino(cuentaDestino);

        switch (request.getTipo()) {
            case CONSIGNACION -> {
                cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() + monto);
                cuentaRepository.save(cuentaOrigen);
                transaccionRepository.save(transaccion);
            }
            case RETIRO -> {
                if (cuentaOrigen.getSaldo() < monto) {
                    throw new IllegalArgumentException("Saldo insuficiente para retiro");
                }
                cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
                cuentaRepository.save(cuentaOrigen);
                transaccionRepository.save(transaccion);
            }
            case TRANSFERENCIA -> {
                if (cuentaOrigen.getSaldo() < monto) {
                    throw new IllegalArgumentException("Saldo insuficiente para transferencia");
                }

                // Débito
                cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
                cuentaRepository.save(cuentaOrigen);
                transaccionRepository.save(transaccion);

                // Crédito (nueva transacción espejo)
                Transaccion transaccionDestino = new Transaccion();
                transaccionDestino.setFecha(LocalDateTime.now());
                transaccionDestino.setTipo(TipoTransaccion.CONSIGNACION);
                transaccionDestino.setDescripcion("Transferencia recibida: " + request.getDescripcion());
                transaccionDestino.setMonto(monto);
                transaccionDestino.setCuentaOrigen(cuentaOrigen);
                transaccionDestino.setCuentaDestino(cuentaDestino);

                cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);
                cuentaRepository.save(cuentaDestino);
                transaccionRepository.save(transaccionDestino);
            }
        }

        return TransaccionMapper.toResponse(transaccion);
    }

}
