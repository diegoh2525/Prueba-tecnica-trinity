package com.prueba.trinity;

import com.prueba.trinity.Dto.Request.ClienteRequest;
import com.prueba.trinity.Dto.Request.CuentaRequest;
import com.prueba.trinity.Dto.Request.TransaccionRequest;
import com.prueba.trinity.Entity.Enums.TipoCuenta;
import com.prueba.trinity.Entity.Enums.TipoTransaccion;
import com.prueba.trinity.Service.ClienteService;
import com.prueba.trinity.Service.CuentaService;
import com.prueba.trinity.Service.TransaccionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ServiceTest {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private TransaccionService transaccionService;

    @Test
    void flujoCompleto() {
        // Crear cliente 1
        ClienteRequest cliente1 = new ClienteRequest();
        cliente1.setTipoId("CC");
        cliente1.setNumeroId("1003951905");
        cliente1.setNombres("Juan");
        cliente1.setApellidos("Herrera");
        cliente1.setCorreo("juan@mail.com");
        cliente1.setFechaNacimiento(LocalDate.of(2002, 7, 24));
        var clienteCreado1 = clienteService.crearCliente(cliente1);
        assertNotNull(clienteCreado1.getId());

        // Crear cliente 2
        ClienteRequest cliente2 = new ClienteRequest();
        cliente2.setTipoId("CC");
        cliente2.setNumeroId("1080951905");
        cliente2.setNombres("Juan");
        cliente2.setApellidos("Casta");
        cliente2.setCorreo("jjuni@mia.com");
        cliente2.setFechaNacimiento(LocalDate.of(2002, 7, 24));
        var clienteCreado2 = clienteService.crearCliente(cliente2);
        assertNotNull(clienteCreado2.getId());

        // Crear cuenta para cliente 1
        CuentaRequest cuenta1 = new CuentaRequest();
        cuenta1.setTipoCuenta(TipoCuenta.AHORRO);
        cuenta1.setSaldoInicial(10000.0);
        cuenta1.setExentaGmf(true);
        cuenta1.setClienteId(clienteCreado1.getId());
        var cuentaCreada1 = cuentaService.crearCuenta(cuenta1);
        assertNotNull(cuentaCreada1.getId());

        // Crear cuenta para cliente 2
        CuentaRequest cuenta2 = new CuentaRequest();
        cuenta2.setTipoCuenta(TipoCuenta.CORRIENTE);
        cuenta2.setSaldoInicial(10000.0);
        cuenta2.setExentaGmf(true);
        cuenta2.setClienteId(clienteCreado2.getId());
        var cuentaCreada2 = cuentaService.crearCuenta(cuenta2);
        assertNotNull(cuentaCreada2.getId());

        // Transferencia entre cuentas
        TransaccionRequest transferencia = new TransaccionRequest();
        transferencia.setTipo(TipoTransaccion.TRANSFERENCIA);
        transferencia.setMonto(5000.0);
        transferencia.setDescripcion("Pago test");
        transferencia.setCuentaOrigenId(cuentaCreada1.getId());
        transferencia.setCuentaDestinoId(cuentaCreada2.getId());
        var transaccionRealizada = transaccionService.realizarTransaccion(transferencia);
        assertNotNull(transaccionRealizada.getId());
    }
}
