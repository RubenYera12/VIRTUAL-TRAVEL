package com.Ruben.BackWeb.reserva.infrastructure.controllers;

import com.Ruben.BackWeb.reserva.application.ReservaService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping("api/v0/Reserva")
@AllArgsConstructor
public class CancelReservaController {

    private final ReservaService reservaService;

    @PutMapping("cancel/Trip")
    public String cancelTrip(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dia, @RequestParam Float hora, @RequestParam String destino) throws Exception {
        reservaService.cancelAllReservas(dia, hora, destino);
        return "Se ha cancelado el viaje correctamente.";
    }

    @PutMapping("cancel/Reserva/{id}")
    public String cancelReserva(@PathVariable String id) throws Exception {
        reservaService.cancelReservaById(id);
        return "Se ha cancelado la reserva correctamente";
    }
}
