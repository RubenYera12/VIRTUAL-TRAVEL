package com.Ruben.BackEmpresa.reserva.infrastructure.controllers;

import com.Ruben.BackEmpresa.reserva.application.ReservaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v0/Reserva")
@AllArgsConstructor
public class DeleteReservaController {

    private final ReservaService reservaService;

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(reservaService.deleteById(id));
    }
}
