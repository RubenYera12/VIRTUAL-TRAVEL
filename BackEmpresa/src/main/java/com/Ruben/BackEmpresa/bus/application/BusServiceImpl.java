package com.Ruben.BackEmpresa.bus.application;

import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.bus.infrastructure.repository.BusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BusServiceImpl implements BusService {
    private final BusRepository busRepository;

    @Override
    public Bus addBus(Bus bus) throws Exception {
        //Comprobamos si existe el bus
        if (bus.getId() != null) {
            Optional<Bus> chekedBus = busRepository.findById(bus.getId());
            if (!chekedBus.isEmpty()) {
                throw new Exception("Ya existe un Autobus con ID: " + bus.getId());
            }
        }
        return busRepository.saveAndFlush(bus);
    }

    @Override
    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    @Override
    public Bus findById(String id) throws Exception {

        return busRepository.findById(id).orElseThrow(()->new Exception("No existe un autobus con ID: "+id));
    }

    @Override
    public String deleteById(String id) throws Exception {
        Optional<Bus> chekedBus = busRepository.findById(id);
        if (chekedBus.isEmpty()) {
            throw new Exception("No existe un Autobus con ID: " + id);
        }
        busRepository.deleteById(id);
        return "Se ha borrado el autobus correctamente";
    }

    @Override
    public Bus updateBus(Bus bus, String id) throws Exception {
        Optional<Bus> chekedBus = busRepository.findById(id);
        if (chekedBus.isEmpty()) {
            throw new Exception("No existe un Autobus con ID: " + bus.getId());
        }
        //TODO: Comprobar nulos
        bus.setId(id);
        if (bus.getCiudadDestino()==null){
            bus.setCiudadDestino(chekedBus.get().getCiudadDestino());
        }
        if(bus.getFechaReserva()==null){
            bus.setFechaReserva(chekedBus.get().getFechaReserva());
        }
        if (bus.getHoraReserva()==null){
            bus.setHoraReserva(chekedBus.get().getHoraReserva());
        }
        if(bus.getReservas()==null){
            bus.setReservas(chekedBus.get().getReservas());
        }
        return busRepository.save(bus);
    }
}
