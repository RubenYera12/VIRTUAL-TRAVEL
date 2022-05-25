package com.Ruben.BackWeb.bus.infrastructure.controllers;

import com.Ruben.BackWeb.bus.application.BusService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0/Bus")
@AllArgsConstructor
public class CreateBusController {
    private final BusService busService;

    //En teoría un cliente no puede agregar un autobus

//    @PostMapping("addBus")
//    public ResponseEntity<OutputBusDTO> addBus(@RequestBody InputBusDTO inputBusDTO) throws Exception {
//        Bus bus = new Bus(inputBusDTO);
//        return ResponseEntity.ok(new OutputBusDTO(busService.addBus(bus)));
//    }
//
//    @DeleteMapping("delete/{id}")
//    public ResponseEntity<String> delete(@PathVariable String id) throws Exception {
//        return ResponseEntity.ok(busService.deleteById(id));
//    }
}
