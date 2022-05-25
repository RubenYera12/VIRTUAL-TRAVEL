package com.Ruben.BackEmpresa.reserva.infrastructure.controllers;

import com.Ruben.BackEmpresa.reserva.application.ReservaService;
import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import com.Ruben.BackEmpresa.reserva.infrastructure.dto.InputReservaDTO;
import com.Ruben.BackEmpresa.reserva.infrastructure.dto.OutputReservaDTO;
import com.Ruben.BackEmpresa.shared.exceptions.NotFoundException;
import com.Ruben.BackEmpresa.shared.exceptions.UnprocesableException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("api/v0/Reserva")
@AllArgsConstructor
public class CreateReservaController {

    private final ReservaService reservaService;

    @PostMapping("reserva")
    public ResponseEntity<OutputReservaDTO> reservar(@Valid @RequestBody InputReservaDTO inputReservaDTO) throws NotFoundException, UnprocesableException, UnsupportedEncodingException {
        return ResponseEntity.ok(new OutputReservaDTO(reservaService.reservar(new Reserva(inputReservaDTO))));
    }
}
