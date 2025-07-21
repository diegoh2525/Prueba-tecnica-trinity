package com.prueba.trinity.Controller;

import com.prueba.trinity.Dto.Request.CuentaRequest;
import com.prueba.trinity.Dto.Response.CuentaResponse;
import com.prueba.trinity.Entity.Enums.EstadoCuenta;
import com.prueba.trinity.Service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ResponseEntity<CuentaResponse> crearCuenta(@Valid @RequestBody CuentaRequest request) {
        CuentaResponse response = cuentaService.crearCuenta(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CuentaResponse>> listarCuentas() {
        return ResponseEntity.ok(cuentaService.listarCuentas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponse> obtenerCuenta(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.obtenerCuenta(id));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<CuentaResponse> actualizarEstado(
            @PathVariable Long id,
            @RequestParam EstadoCuenta nuevoEstado) {
        return ResponseEntity.ok(cuentaService.actualizarEstado(id, nuevoEstado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }
}

