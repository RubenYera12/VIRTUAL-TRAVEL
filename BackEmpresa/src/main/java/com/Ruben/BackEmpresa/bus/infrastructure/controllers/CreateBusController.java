package com.Ruben.BackEmpresa.bus.infrastructure.controllers;

import com.Ruben.BackEmpresa.bus.application.BusService;
import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.bus.infrastructure.dto.InputBusDTO;
import com.Ruben.BackEmpresa.bus.infrastructure.dto.OutputBusDTO;
import com.Ruben.BackEmpresa.shared.exceptions.UnprocesableException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v0/Bus")
@AllArgsConstructor
public class CreateBusController {

    private final BusService busService;

    @PostMapping("addBus")
    public ResponseEntity<OutputBusDTO> addBus(@Valid @RequestBody InputBusDTO inputBusDTO) throws UnprocesableException {
        Bus bus = new Bus(inputBusDTO);
            return ResponseEntity.ok(new OutputBusDTO(busService.addBus(bus)));

    }
}
