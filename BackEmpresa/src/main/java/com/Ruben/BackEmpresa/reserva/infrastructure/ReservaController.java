package com.Ruben.BackEmpresa.reserva.infrastructure;

import com.Ruben.BackEmpresa.bus.application.BusService;
import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.email.application.EmailService;
import com.Ruben.BackEmpresa.reserva.application.ReservaService;
import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import com.Ruben.BackEmpresa.reserva.infrastructure.dto.InputReservaDTO;
import com.Ruben.BackEmpresa.reserva.infrastructure.dto.OutputReservaDTO;
import com.Ruben.BackEmpresa.reserva.infrastructure.dto.ReservaDisponibleOutputDTO;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/Empresa/Reserva")
@AllArgsConstructor
public class ReservaController {
    private final ReservaService reservaService;
    private final BusService busService;
    private final EmailService emailService;

    @PostMapping("reserva")
    public OutputReservaDTO reservar(@RequestBody InputReservaDTO inputReservaDTO) throws Exception {
        return new OutputReservaDTO(reservaService.reservar(new Reserva(inputReservaDTO)));
    }

    @PutMapping("cancel/Trip")
    public String cancelTrip(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date dia, @RequestParam Float hora, @RequestParam String destino) throws Exception {
        reservaService.cancelAllReservas(dia, hora, destino);
        return "Se ha cancelado el viaje correctamente.";
    }

    @PutMapping("cancel/Reserva/{id}")
    public String cancelReserva(@PathVariable String id) throws Exception {
        reservaService.cancelReservaById(id);
        return "Se ha cancelado la reserva correctamente";
    }

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

    @PostMapping("update/{id}")
    public OutputReservaDTO update(@PathVariable String id, @RequestBody Reserva reserva) throws Exception {
        return new OutputReservaDTO(reservaService.updateReserva(reserva, id));
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable String id) throws Exception {
        return reservaService.deleteById(id);
    }

    @GetMapping("findReservasByConditions")
    public List<OutputReservaDTO> findReservaByConditions(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date dia, @RequestParam Float hora, @RequestParam String destino) throws Exception {
        //Creamos la lista de condiciones
        HashMap<String, Object> conditions = new HashMap<>();
        if (dia == null || hora == null || destino == null) {
            throw new Exception("Completa los parametros.");
        }
//        conditions.put("fechaReserva", dia);
//        conditions.put("horaReserva", hora);
//        conditions.put("ciudadDestino", destino);


        //Creamos la lista de reservas
        List<OutputReservaDTO> outputReservaDTOList = new ArrayList<>();
        for (Reserva reserva : reservaService.findReservaByConditions(dia,hora,destino)) {
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
//        conditions.put("fechaReserva", dia);
//        conditions.put("horaReserva", hora);
//        conditions.put("ciudadDestino", destino);

        Bus bus = busService.findBusByConditions(dia, hora, destino);

        return new ReservaDisponibleOutputDTO(bus);

    }
}
