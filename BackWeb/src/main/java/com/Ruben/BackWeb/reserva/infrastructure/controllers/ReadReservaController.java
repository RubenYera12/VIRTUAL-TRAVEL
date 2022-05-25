package com.Ruben.BackWeb.reserva.infrastructure.controllers;

import com.Ruben.BackWeb.bus.application.BusService;
import com.Ruben.BackWeb.bus.domain.Bus;
import com.Ruben.BackWeb.reserva.application.ReservaService;
import com.Ruben.BackWeb.reserva.domain.Reserva;
import com.Ruben.BackWeb.reserva.infrastructure.dto.OutputReservaDTO;
import com.Ruben.BackWeb.reserva.infrastructure.dto.ReservaDisponibleOutputDTO;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v0/Reserva")
@AllArgsConstructor
public class ReadReservaController {

    private final ReservaService reservaService;
    private final BusService busService;

    @GetMapping("findById/{id}")
    public OutputReservaDTO findById(@PathVariable String id) throws Exception {
        return new OutputReservaDTO(reservaService.findById(id));
    }

    @GetMapping("findAll")
    public List<OutputReservaDTO> findAll() {
        List<OutputReservaDTO> outputReservaDTOList = new ArrayList<>();
        for (Reserva reserva : reservaService.findAll()) {
            outputReservaDTOList.add(new OutputReservaDTO(reserva));
        }
        return outputReservaDTOList;
    }

    @GetMapping("findReservasByConditions")
    public List<OutputReservaDTO> findReservaByConditions(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date dia, @RequestParam Float hora, @RequestParam String destino) throws Exception {
        //Creamos la lista de condiciones
        HashMap<String, Object> conditions = new HashMap<>();
        if (dia == null || hora == null || destino == null) {
            throw new Exception("Completa los parametros.");
        }

        //Creamos la lista de reservas
        List<OutputReservaDTO> outputReservaDTOList = new ArrayList<>();
        for (Reserva reserva : reservaService.findByCiudadDestinoAndFechaReservaAndHoraReserva(destino,dia,hora)) {
            outputReservaDTOList.add(new OutputReservaDTO(reserva));
        }

        return outputReservaDTOList;
    }

    @GetMapping("getNumPlazasByConditions")
    public ReservaDisponibleOutputDTO getNumPlazasByConditions(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date dia, @RequestParam Float hora, @RequestParam String destino) throws Exception {
        //Creamos la lista de condiciones
        HashMap<String, Object> conditions = new HashMap<>();
        if (dia == null || hora == null || destino == null) {
            throw new Exception("Completa los parametros.");
        }

        Bus bus = busService.findByCiudadDestinoAndFechaReservaAndHoraReserva(destino, dia, hora);

        return new ReservaDisponibleOutputDTO(bus);

    }
}
