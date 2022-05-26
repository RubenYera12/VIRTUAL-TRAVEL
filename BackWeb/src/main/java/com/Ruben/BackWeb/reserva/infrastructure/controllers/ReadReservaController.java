package com.Ruben.BackWeb.reserva.infrastructure.controllers;

import com.Ruben.BackWeb.bus.application.BusService;
import com.Ruben.BackWeb.bus.domain.Bus;
import com.Ruben.BackWeb.reserva.application.ReservaService;
import com.Ruben.BackWeb.reserva.domain.Reserva;
import com.Ruben.BackWeb.reserva.infrastructure.dto.OutputReservaDTO;
import com.Ruben.BackWeb.reserva.infrastructure.dto.ReservaDisponibleOutputDTO;
import com.Ruben.BackWeb.shared.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    @GetMapping("{id}")
    public ResponseEntity<OutputReservaDTO> findById(@PathVariable String id) throws NotFoundException {
        return ResponseEntity.ok(new OutputReservaDTO(reservaService.findById(id)));
    }

    @GetMapping()
    public ResponseEntity<List<OutputReservaDTO>> findAll() {
        List<OutputReservaDTO> outputReservaDTOList = new ArrayList<>();
        for (Reserva reserva : reservaService.findAll()) {
            outputReservaDTOList.add(new OutputReservaDTO(reserva));
        }
        return ResponseEntity.ok(outputReservaDTOList);
    }

    @GetMapping("ByDiaHoraDestino")
    public ResponseEntity<List<OutputReservaDTO>> findReservaByConditions(@RequestParam @Valid @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") Date dia,
                                                          @RequestParam @Valid @NotNull Float hora,
                                                          @RequestParam @Valid @NotNull String destino)
            throws Exception {
        //Creamos la lista de reservas
        List<OutputReservaDTO> outputReservaDTOList = new ArrayList<>();
        for (Reserva reserva : reservaService.findByCiudadDestinoAndFechaReservaAndHoraReserva(destino, dia, hora)) {
            outputReservaDTOList.add(new OutputReservaDTO(reserva));
        }

        return ResponseEntity.ok(outputReservaDTOList);
    }

    @GetMapping("NumPlazasByDiaHoraDestino")
    public ResponseEntity<ReservaDisponibleOutputDTO> getNumPlazasByConditions(@RequestParam @Valid @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") Date dia,
                                                               @RequestParam @Valid @NotNull Float hora,
                                                               @RequestParam @Valid @NotNull String destino)
            throws Exception {
        Bus bus = busService.findByCiudadDestinoAndFechaReservaAndHoraReserva(destino, dia, hora);

        return ResponseEntity.ok(new ReservaDisponibleOutputDTO(bus));

    }
}
