package com.Ruben.BackEmpresa.bus.infrastructure.controllers;

import com.Ruben.BackEmpresa.bus.application.BusService;
import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.bus.infrastructure.dto.InputBusDTO;
import com.Ruben.BackEmpresa.bus.infrastructure.dto.OutputBusDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v0/Bus")
@AllArgsConstructor
public class BusController {
    private final BusService busService;

    @PostMapping("addBus")
    public ResponseEntity<OutputBusDTO> addBus(@RequestBody InputBusDTO inputBusDTO) throws Exception {
        Bus bus = new Bus(inputBusDTO);
        return ResponseEntity.ok(new OutputBusDTO(busService.addBus(bus)));
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<OutputBusDTO> findById(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(new OutputBusDTO(busService.findById(id)));
    }

    @GetMapping("findAll")
    public ResponseEntity<List<OutputBusDTO>> findAll() {
        List<OutputBusDTO> outputBusDTOList = new ArrayList<>();
        for (Bus bus : busService.findAll()) {
            outputBusDTOList.add(new OutputBusDTO(bus));
        }
        return ResponseEntity.ok(outputBusDTOList);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<OutputBusDTO> update(@RequestBody Bus bus, @PathVariable String id) throws Exception {
        return ResponseEntity.ok(new OutputBusDTO(busService.updateBus(bus,id)));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(busService.deleteById(id));
    }
}
