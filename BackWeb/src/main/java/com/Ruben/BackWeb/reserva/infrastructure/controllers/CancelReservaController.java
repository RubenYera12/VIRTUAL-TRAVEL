package com.Ruben.BackWeb.reserva.infrastructure.controllers;

import com.Ruben.BackWeb.reserva.application.ReservaService;
import com.Ruben.BackWeb.shared.exceptions.NotFoundException;
import com.Ruben.BackWeb.shared.exceptions.UnprocesableException;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;


@RestController
@RequestMapping("api/v0/Reserva")
@AllArgsConstructor
public class CancelReservaController {

    private final ReservaService reservaService;

    @PutMapping("cancel/{id}")
    public ResponseEntity<String> cancelReserva(@PathVariable String id) throws Exception {
        reservaService.cancelReservaById(id);
        return ResponseEntity.ok("Se ha cancelado la reserva correctamente");
    }
}
