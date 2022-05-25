package com.Ruben.BackEmpresa.bus.application;

import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.bus.infrastructure.repository.BusRepository;
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
    public Bus addBus(Bus bus) throws Exception {
        //Comprobamos si existe el bus
        if (bus.getCiudadDestino()!= null ||bus.getHoraReserva()!=null||bus.getFechaReserva()!=null) {
            Optional<Bus> chekedBus = busRepository.findByCiudadDestinoAndFechaReservaAndHoraReserva(bus.getCiudadDestino(),bus.getFechaReserva(),bus.getHoraReserva());
            if (chekedBus.isPresent()) {
                throw new Exception("Ya existe un Autobus con ID: " + bus.getId());
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

    @Override
    public Bus findByCiudadDestinoAndFechaReservaAndHoraReserva(String ciudad,Date fecha, Float hora) throws Exception {
        return busRepository.
        findByCiudadDestinoAndFechaReservaAndHoraReserva(ciudad,fecha,hora).orElseThrow(()->new Exception("No se ha encontrado un autobus con estos requisitos."));
    }

    @Override
    public void listenTopic(String s, Bus readValue) {

    }
}
