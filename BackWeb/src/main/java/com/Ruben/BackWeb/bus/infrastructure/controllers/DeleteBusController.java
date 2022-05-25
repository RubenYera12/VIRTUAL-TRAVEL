package com.Ruben.BackWeb.bus.infrastructure.controllers;

import com.Ruben.BackWeb.bus.application.BusService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v0/Bus")
@AllArgsConstructor
public class DeleteBusController {

    private final BusService busService;

    //Un usuario no puede borrar un autobus

//    @DeleteMapping("delete/{id}")
//    public ResponseEntity<String> delete(@PathVariable String id) throws Exception {
//        return ResponseEntity.ok(busService.deleteById(id));
//    }
}
