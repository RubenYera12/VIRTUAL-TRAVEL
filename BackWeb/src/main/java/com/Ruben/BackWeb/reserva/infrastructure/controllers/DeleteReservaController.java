package com.Ruben.BackWeb.reserva.infrastructure.controllers;

import com.Ruben.BackWeb.reserva.application.ReservaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v0/Reserva")
@AllArgsConstructor
public class DeleteReservaController {

    private final ReservaService reservaService;

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable String id) throws Exception {
        return reservaService.deleteById(id);
    }
}
