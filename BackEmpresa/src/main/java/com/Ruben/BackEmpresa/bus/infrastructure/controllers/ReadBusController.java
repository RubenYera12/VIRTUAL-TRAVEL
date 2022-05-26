package com.Ruben.BackEmpresa.bus.infrastructure.controllers;

import com.Ruben.BackEmpresa.bus.application.BusService;
import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.bus.infrastructure.dto.OutputBusDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v0/Bus")
@AllArgsConstructor
public class ReadBusController {

    private final BusService busService;

    @GetMapping("{id}")
    public ResponseEntity<OutputBusDTO> findById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(new OutputBusDTO(busService.findById(id)));
    }

    @GetMapping()
    public ResponseEntity<List<OutputBusDTO>> findAll() {
        List<OutputBusDTO> outputBusDTOList = new ArrayList<>();
        for (Bus bus : busService.findAll()) {
            outputBusDTOList.add(new OutputBusDTO(bus));
        }
        return ResponseEntity.ok(outputBusDTOList);
    }
}
