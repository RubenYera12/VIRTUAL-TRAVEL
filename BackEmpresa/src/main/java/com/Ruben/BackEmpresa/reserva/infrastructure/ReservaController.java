package com.Ruben.BackEmpresa.reserva.infrastructure;

import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.bus.infrastructure.repository.BusRepository;
import com.Ruben.BackEmpresa.reserva.application.ReservaService;
import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import com.Ruben.BackEmpresa.reserva.infrastructure.dto.InputReservaDTO;
import com.Ruben.BackEmpresa.reserva.infrastructure.dto.OutputReservaDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/Empresa/Reserva")
@AllArgsConstructor
public class ReservaController {
    private final ReservaService reservaService;
    private final BusRepository busRepository;

    @PostMapping("addReserva")
    public OutputReservaDTO addReserva(@RequestBody InputReservaDTO inputReservaDTO) throws Exception {
        return new OutputReservaDTO(reservaService.addReserva(new Reserva(inputReservaDTO)));
    }

    @PostMapping("reserva")
    public OutputReservaDTO reserar(@RequestBody InputReservaDTO inputReservaDTO) throws Exception {
        return new OutputReservaDTO(reservaService.reservar(new Reserva(inputReservaDTO)));
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

}
