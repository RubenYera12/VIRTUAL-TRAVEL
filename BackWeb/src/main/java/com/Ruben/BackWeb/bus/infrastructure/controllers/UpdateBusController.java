package com.Ruben.BackWeb.bus.infrastructure.controllers;

import com.Ruben.BackWeb.bus.application.BusService;
import com.Ruben.BackWeb.bus.domain.Bus;
import com.Ruben.BackWeb.bus.infrastructure.dto.OutputBusDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0/Bus")
@AllArgsConstructor
public class UpdateBusController {
    private final BusService busService;

    //Un usuario no puede modificar un autobus
//    @PostMapping("update/{id}")
//    public ResponseEntity<OutputBusDTO> update(@RequestBody Bus bus, @PathVariable String id) throws Exception {
//        return ResponseEntity.ok(new OutputBusDTO(busService.updateBus(bus, id)));
//    }
}
