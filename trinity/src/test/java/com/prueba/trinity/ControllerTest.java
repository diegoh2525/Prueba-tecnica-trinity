package com.prueba.trinity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.trinity.Dto.Request.ClienteRequest;
import com.prueba.trinity.Dto.Request.CuentaRequest;
import com.prueba.trinity.Dto.Request.TransaccionRequest;
import com.prueba.trinity.Entity.Enums.TipoCuenta;
import com.prueba.trinity.Entity.Enums.TipoTransaccion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void flujoCompletoController() throws Exception {
        // Crear cliente 1
        ClienteRequest cliente1 = new ClienteRequest();
        cliente1.setTipoId("CC");
        cliente1.setNumeroId("1003951905");
        cliente1.setNombres("Juan");
        cliente1.setApellidos("Herrera");
        cliente1.setCorreo("juan@mail.com");
        cliente1.setFechaNacimiento(LocalDate.of(2002, 7, 24));

        String cliente1Response = mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente1)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Long cliente1Id = objectMapper.readTree(cliente1Response).get("id").asLong();

        // Crear cliente 2
        ClienteRequest cliente2 = new ClienteRequest();
        cliente2.setTipoId("CC");
        cliente2.setNumeroId("1080951905");
        cliente2.setNombres("Juan");
        cliente2.setApellidos("Casta");
        cliente2.setCorreo("jjuni@mia.com");
        cliente2.setFechaNacimiento(LocalDate.of(2002, 7, 24));

        String cliente2Response = mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente2)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Long cliente2Id = objectMapper.readTree(cliente2Response).get("id").asLong();

        // Crear cuenta para cliente 1
        CuentaRequest cuenta1 = new CuentaRequest();
        cuenta1.setTipoCuenta(TipoCuenta.AHORRO);
        cuenta1.setSaldoInicial(10000.0);
        cuenta1.setExentaGmf(true);
        cuenta1.setClienteId(cliente1Id);

        String cuenta1Response = mockMvc.perform(post("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuenta1)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Long cuenta1Id = objectMapper.readTree(cuenta1Response).get("id").asLong();

        // Crear cuenta para cliente 2
        CuentaRequest cuenta2 = new CuentaRequest();
        cuenta2.setTipoCuenta(TipoCuenta.CORRIENTE);
        cuenta2.setSaldoInicial(10000.0);
        cuenta2.setExentaGmf(true);
        cuenta2.setClienteId(cliente2Id);

        String cuenta2Response = mockMvc.perform(post("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuenta2)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Long cuenta2Id = objectMapper.readTree(cuenta2Response).get("id").asLong();

        // Transferencia entre cuentas
        TransaccionRequest transferencia = new TransaccionRequest();
        transferencia.setTipo(TipoTransaccion.TRANSFERENCIA);
        transferencia.setMonto(5000.0);
        transferencia.setDescripcion("Pago test");
        transferencia.setCuentaOrigenId(cuenta1Id);
        transferencia.setCuentaDestinoId(cuenta2Id);

        mockMvc.perform(post("/api/transacciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transferencia)))
                .andExpect(status().isCreated());
    }
}
