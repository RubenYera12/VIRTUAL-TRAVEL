package com.Ruben.BackEmpresa.bus.infrastructure.controllers;

import com.Ruben.BackEmpresa.bus.application.BusService;
import com.Ruben.BackEmpresa.bus.infrastructure.dto.OutputBusDTO;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v0/Bus")
@AllArgsConstructor
public class UpdateBusController {

    private final BusService busService;

    @PutMapping("activar")
    public ResponseEntity<OutputBusDTO> activarBus(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dia,
                                                   @RequestParam Float hora, @RequestParam String destino){
        return ResponseEntity.ok(new OutputBusDTO(busService.activarViaje(destino,dia,hora)));
    }
}
