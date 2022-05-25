package com.Ruben.BackEmpresa.reserva.infrastructure.controllers;

import com.Ruben.BackEmpresa.reserva.application.ReservaService;
import com.Ruben.BackEmpresa.shared.exceptions.NotFoundException;
import com.Ruben.BackEmpresa.shared.exceptions.UnprocesableException;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@RestController
@RequestMapping("api/v0/Reserva")
@AllArgsConstructor
public class CancelReservaController {
    private final ReservaService reservaService;

    @PutMapping("cancel/Trip")
    public ResponseEntity<String> cancelTrip(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dia,
                                             @RequestParam Float hora, @RequestParam String destino) throws NotFoundException, UnprocesableException, UnsupportedEncodingException {
        reservaService.cancelAllReservas(dia, hora, destino);
        return ResponseEntity.ok("Se ha cancelado el viaje correctamente.");
    }

    @PutMapping("cancel/Reserva/{id}")
    public ResponseEntity<String> cancelReserva(@PathVariable String id) throws NotFoundException,UnprocesableException,UnsupportedEncodingException {
        reservaService.cancelReservaById(id);
        return ResponseEntity.ok("Se ha cancelado la reserva correctamente");
    }
}
