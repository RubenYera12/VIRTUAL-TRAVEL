package com.Ruben.BackEmpresa.reserva.infrastructure.controllers;

import com.Ruben.BackEmpresa.bus.application.BusService;
import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.reserva.application.ReservaService;
import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import com.Ruben.BackEmpresa.reserva.infrastructure.dto.OutputReservaDTO;
import com.Ruben.BackEmpresa.reserva.infrastructure.dto.ReservaDisponibleOutputDTO;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v0/Reserva")
@AllArgsConstructor
public class ReadReservaController {

    private final ReservaService reservaService;
    private final BusService busService;

    @GetMapping("{id}")
    public ResponseEntity<OutputReservaDTO> findById(@PathVariable String id) throws Exception {
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
    public ResponseEntity<List<OutputReservaDTO>> findReservaByConditions(@RequestParam @Valid @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") Date dia, @RequestParam @Valid @NotNull Float hora, @RequestParam @Valid @NotNull String destino) throws Exception {
        //Creamos la lista de reservas
        List<OutputReservaDTO> outputReservaDTOList = new ArrayList<>();
        for (Reserva reserva : reservaService.findReservaByConditions(dia,hora,destino)) {
            outputReservaDTOList.add(new OutputReservaDTO(reserva));
        }
        return ResponseEntity.ok(outputReservaDTOList);
    }

    @GetMapping("NumPlazasByDiaHoraDestino")
    public ResponseEntity<ReservaDisponibleOutputDTO> getNumPlazasByConditions(@RequestParam @Valid @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd")Date dia, @RequestParam @Valid @NotNull Float hora, @RequestParam @Valid @NotNull String destino) throws Exception {
        Bus bus = busService.findByCiudadDestinoAndFechaReservaAndHoraReserva(destino, dia, hora);
        return ResponseEntity.ok(new ReservaDisponibleOutputDTO(bus));

    }
}
