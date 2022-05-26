package com.Ruben.BackWeb.reserva.infrastructure.controllers;

import com.Ruben.BackWeb.reserva.application.ReservaService;
import com.Ruben.BackWeb.reserva.domain.Reserva;
import com.Ruben.BackWeb.reserva.infrastructure.dto.InputReservaDTO;
import com.Ruben.BackWeb.reserva.infrastructure.dto.OutputReservaDTO;
import com.Ruben.BackWeb.shared.exceptions.NotFoundException;
import com.Ruben.BackWeb.shared.exceptions.UnprocesableException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v0/Reserva")
@AllArgsConstructor
public class CreateReservaController {

    private final ReservaService reservaService;

    @PostMapping()
    public ResponseEntity<OutputReservaDTO> reservar(@Valid @RequestBody InputReservaDTO inputReservaDTO) throws Exception {
        return ResponseEntity.ok(new OutputReservaDTO(reservaService.reservar(new Reserva(inputReservaDTO))));
    }
}
