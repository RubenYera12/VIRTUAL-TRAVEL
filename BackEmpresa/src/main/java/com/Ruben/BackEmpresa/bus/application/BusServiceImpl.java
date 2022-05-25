package com.Ruben.BackEmpresa.bus.application;

import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.bus.infrastructure.repository.BusRepository;
import com.Ruben.BackEmpresa.shared.exceptions.NotFoundException;
import com.Ruben.BackEmpresa.shared.exceptions.UnprocesableException;
import com.Ruben.BackEmpresa.shared.kafka.Producer.KafkaSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service

public class BusServiceImpl implements BusService {
    private final BusRepository busRepository;
    private KafkaSender kafkaSender;

    @Value("${server.port}")
    private String port;

    @Value("${topic}")
    private String topic;

    private final String CLASE = "BUS";

    public BusServiceImpl(BusRepository busRepository, KafkaSender kafkaSender) {
        this.busRepository = busRepository;
        this.kafkaSender = kafkaSender;
    }

    @Override
    public Bus addBus(Bus bus) throws UnprocesableException {
        //Comprobamos si existe el bus
        if (bus.getCiudadDestino()!= null ||bus.getHoraReserva()!=null||bus.getFechaReserva()!=null) {
            Optional<Bus> chekedBus = busRepository.findByCiudadDestinoAndFechaReservaAndHoraReserva(bus.getCiudadDestino(),bus.getFechaReserva(),bus.getHoraReserva());
            if (chekedBus.isPresent()) {
                throw new UnprocesableException("Ya existe un Autobus con ID: " + bus.getId());
            }
        }
        kafkaSender.sendMessage(topic,bus,port,"crearBus",CLASE);
        return busRepository.saveAndFlush(bus);
    }

    @Override
    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    @Override
    public Bus findById(String id) throws NotFoundException {

        return busRepository.findById(id).orElseThrow(()->new NotFoundException("No existe un autobus con ID: "+id));
    }

    @Override
    public String deleteById(String id) throws NotFoundException {
        Optional<Bus> chekedBus = busRepository.findById(id);
        if (chekedBus.isEmpty()) {
            throw new NotFoundException("No existe un Autobus con ID: " + id);
        }
        busRepository.deleteById(id);
        kafkaSender.sendMessage(topic,chekedBus.get(),port,"borrarBus",CLASE);
        return "Se ha borrado el autobus correctamente";
    }

    @Override
    public Bus findByCiudadDestinoAndFechaReservaAndHoraReserva(String ciudad,Date fecha, Float hora) throws NotFoundException {
        return busRepository.
        findByCiudadDestinoAndFechaReservaAndHoraReserva(ciudad,fecha,hora)
                .orElseThrow(()->new NotFoundException("No se ha encontrado un autobus con estos requisitos."));
    }
}
