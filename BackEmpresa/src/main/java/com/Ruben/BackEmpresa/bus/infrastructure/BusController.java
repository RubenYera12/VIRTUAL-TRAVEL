package com.Ruben.BackEmpresa.bus.infrastructure;

import com.Ruben.BackEmpresa.bus.application.BusService;
import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.bus.infrastructure.dto.InputBusDTO;
import com.Ruben.BackEmpresa.bus.infrastructure.dto.OutputBusDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/Empresa/Bus")
@AllArgsConstructor
public class BusController {
    private final BusService busService;

    @PostMapping("addBus")
    public OutputBusDTO addBus(@RequestBody InputBusDTO inputBusDTO) throws Exception {
        Bus bus = new Bus(inputBusDTO);
        return new OutputBusDTO(busService.addBus(bus));
    }

    @GetMapping("findById/{id}")
    public OutputBusDTO findById(@PathVariable String id) throws Exception {
        return new OutputBusDTO(busService.findById(id));
    }

    @GetMapping("findAll")
    public List<OutputBusDTO> findAll() {
        List<OutputBusDTO> outputBusDTOList = new ArrayList<>();
        for (Bus bus : busService.findAll()) {
            outputBusDTOList.add(new OutputBusDTO(bus));
        }
        return outputBusDTOList;
    }

    @PostMapping("update/{id}")
    public OutputBusDTO update(@RequestBody Bus bus, @PathVariable String id) throws Exception {
        return new OutputBusDTO(busService.updateBus(bus,id));
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable String id) throws Exception {
        return busService.deleteById(id);
    }
}
