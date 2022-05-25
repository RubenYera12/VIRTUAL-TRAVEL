package com.Ruben.BackWeb.reserva.infrastructure.controllers;

import com.Ruben.BackWeb.reserva.application.ReservaService;
import com.Ruben.BackWeb.shared.exceptions.NotFoundException;
import com.Ruben.BackWeb.shared.exceptions.UnprocesableException;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;


@RestController
@RequestMapping("api/v0/Reserva")
@AllArgsConstructor
public class CancelReservaController {

    private final ReservaService reservaService;

    @PutMapping("cancel/Trip")
    public String cancelTrip(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") @Valid @NotNull Date dia,
                             @RequestParam @Valid @NotNull Float hora,
                             @RequestParam @Valid @NotNull String destino)
            throws NotFoundException, UnprocesableException {
        reservaService.cancelAllReservas(dia, hora, destino);
        return "Se ha cancelado el viaje correctamente.";
    }

    @PutMapping("cancel/Reserva/{id}")
    public String cancelReserva(@PathVariable String id) throws NotFoundException,UnprocesableException {
        reservaService.cancelReservaById(id);
        return "Se ha cancelado la reserva correctamente";
    }
}
