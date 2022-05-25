package com.Ruben.BackWeb.bus.application;

import com.Ruben.BackWeb.bus.domain.Bus;
import com.Ruben.BackWeb.bus.infrastructure.repository.BusRepository;
import com.Ruben.BackWeb.reserva.application.ReservaService;
import com.Ruben.BackWeb.reserva.domain.Reserva;
import com.Ruben.BackWeb.shared.exceptions.NotFoundException;
import com.Ruben.BackWeb.shared.exceptions.UnprocesableException;
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
    public Bus addBus(Bus bus) throws UnprocesableException {
        //Comprobamos si existe el bus
        if (bus.getCiudadDestino()!= null ||bus.getHoraReserva()!=null||bus.getFechaReserva()!=null) {
            Optional<Bus> chekedBus = busRepository
                    .findByCiudadDestinoAndFechaReservaAndHoraReserva(bus.getCiudadDestino(),bus.getFechaReserva(),bus.getHoraReserva());
            if (chekedBus.isPresent()) {
                throw new UnprocesableException("Ya existe un Autobus con ID: " + bus.getId());
            }
        }
        return busRepository.saveAndFlush(bus);
    }

    @Override
    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    @Override
    public Bus findById(String id) throws NotFoundException {

        return busRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("No existe un autobus con ID: " + id));
    }

    @Override
    public String deleteById(String id) throws NotFoundException {
        Optional<Bus> chekedBus = busRepository.findById(id);
        if (chekedBus.isEmpty()) {
            throw new NotFoundException("No existe un Autobus con ID: " + id);
        }
        busRepository.deleteById(id);
        return "Se ha borrado el autobus correctamente";
    }

    @Override
    public Bus findByCiudadDestinoAndFechaReservaAndHoraReserva(String ciudad,Date fecha, Float hora) throws NotFoundException {
        return busRepository.
                findByCiudadDestinoAndFechaReservaAndHoraReserva(ciudad, fecha, hora).orElseThrow(() -> new NotFoundException("No se ha encontrado un autobus con estos requisitos."));
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
                } catch (UnprocesableException e) {
                    System.out.println(e.getMessage());
                }
            }
            case "borrarBus"-> deleteById(readValue.getId());
        }
    }

    @Override
    public void cancelarBus(Bus readValue) throws NotFoundException,UnprocesableException {
        //Buscamos el autobus
        Bus bus = busRepository
                .findById(readValue.getId())
                .orElseThrow(() -> new NotFoundException("No se ha encontrado un autobus con estos parámetros"));
        //Comprobamos el estado del autobus
        if (bus.getEstado().equals("CANCELADO"))
            throw new UnprocesableException("El viaje ya está cancelado");
        //Mandamos correo de cancelación a todos los usuarios y cancelamos las reservas
        for (Reserva reserva : bus.getReservas()) {
            reservaService.cancelCancelFromBackEmpresa(reserva);
        }
        bus.getReservas().clear();
        bus.setEstado("CANCELADO");
        busRepository.save(bus);
    }
}
