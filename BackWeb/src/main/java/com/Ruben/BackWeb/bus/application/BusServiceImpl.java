package com.Ruben.BackWeb.bus.application;

import com.Ruben.BackWeb.bus.domain.Bus;
import com.Ruben.BackWeb.bus.infrastructure.repository.BusRepository;
import com.Ruben.BackWeb.reserva.application.ReservaService;
import com.Ruben.BackWeb.reserva.domain.Reserva;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Proxy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Proxy(lazy = true)
public class BusServiceImpl implements BusService {
    private final BusRepository busRepository;
    private final ReservaService reservaService;

    @Override
    public Bus addBus(Bus bus) throws Exception {
        //Comprobamos si existe el bus
        if (bus.getCiudadDestino()!= null ||bus.getHoraReserva()!=null||bus.getFechaReserva()!=null) {
            Optional<Bus> chekedBus = busRepository.findByCiudadDestinoAndFechaReservaAndHoraReserva(bus.getCiudadDestino(),bus.getFechaReserva(),bus.getHoraReserva());
            if (chekedBus.isPresent()) {
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

        return busRepository.findById(id).orElseThrow(() -> new Exception("No existe un autobus con ID: " + id));
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
        if (bus.getCiudadDestino() == null) {
            bus.setCiudadDestino(chekedBus.get().getCiudadDestino());
        }
        if (bus.getFechaReserva() == null) {
            bus.setFechaReserva(chekedBus.get().getFechaReserva());
        }
        if (bus.getHoraReserva() == null) {
            bus.setHoraReserva(chekedBus.get().getHoraReserva());
        }
        if (bus.getReservas() == null) {
            bus.setReservas(chekedBus.get().getReservas());
        }
        return busRepository.save(bus);
    }

    @Override
    public Bus findByCiudadDestinoAndFechaReservaAndHoraReserva(String ciudad,Date fecha, Float hora) throws Exception {
        return busRepository.
                findByCiudadDestinoAndFechaReservaAndHoraReserva(ciudad, fecha, hora).orElseThrow(() -> new Exception("No se ha encontrado un autobus con estos requisitos."));
    }

    @Override
    public void listenTopic(String s, Bus readValue) {
        switch (s) {
            case "cancelarBus" -> {
                try {
                    cancelarBus(readValue);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            case "crearBus"->{
                try {
                    addBus(readValue);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void cancelarBus(Bus readValue) throws Exception {
        //Buscamos el autobus
        Bus bus = busRepository
                .findById(readValue.getId())
                .orElseThrow(() -> new Exception("No se ha encontrado un autobus con estos parámetros"));
        //Comprobamos el estado del autobus
        if (bus.getEstado().equals("CANCELADO"))
            throw new Exception("El viaje ya está cancelado");
        //Mandamos correo de cancelación a todos los usuarios y cancelamos las reservas
        for (Reserva reserva : bus.getReservas()) {
            reservaService.cancelCancelFromBackEmpresa(reserva);
        }
        bus.getReservas().clear();
        bus.setEstado("CANCELADO");
        busRepository.save(bus);
    }
}
