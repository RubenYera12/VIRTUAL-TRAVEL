package com.Ruben.BackWeb.reserva.infrastructure.controllers;

import com.Ruben.BackWeb.reserva.application.ReservaService;
import com.Ruben.BackWeb.reserva.domain.Reserva;
import com.Ruben.BackWeb.reserva.infrastructure.dto.InputReservaDTO;
import com.Ruben.BackWeb.reserva.infrastructure.dto.OutputReservaDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v0/Reserva")
@AllArgsConstructor
public class CreateReservaController {

    private final ReservaService reservaService;

    @PostMapping("reserva")
    public OutputReservaDTO reservar(@RequestBody InputReservaDTO inputReservaDTO) throws Exception {
        return new OutputReservaDTO(reservaService.reservar(new Reserva(inputReservaDTO)));
    }
}
