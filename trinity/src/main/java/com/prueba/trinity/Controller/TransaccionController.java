package com.prueba.trinity.Controller;

import com.prueba.trinity.Dto.Request.TransaccionRequest;
import com.prueba.trinity.Dto.Response.TransaccionResponse;
import com.prueba.trinity.Service.TransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionService transaccionService;

    @PostMapping
    public ResponseEntity<TransaccionResponse> crear(@RequestBody @Valid TransaccionRequest request) {
        TransaccionResponse response = transaccionService.realizarTransaccion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
