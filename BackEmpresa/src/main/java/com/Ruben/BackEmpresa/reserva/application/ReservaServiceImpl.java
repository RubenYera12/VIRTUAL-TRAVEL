package com.Ruben.BackEmpresa.reserva.application;

import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.bus.infrastructure.repository.BusRepository;
import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import com.Ruben.BackEmpresa.reserva.infrastructure.repository.ReservaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.management.BufferPoolMXBean;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservaServiceImpl implements ReservaService {
    private final ReservaRepository reservaRepository;
    private final BusRepository busRepository;

    @Override
    public Reserva addReserva(Reserva reserva) throws Exception {
        //Comprobamos si existe la Reserva
        if (reserva.getId() != null) {
            Optional<Reserva> reservaChecked = reservaRepository.findById(reserva.getId());
            if (!reservaChecked.isEmpty()) {
                throw new Exception("Ya existe una reserva con ID: " + reserva.getId());
            }
        }
        return reservaRepository.save(reserva);
    }

    @Override
    public Reserva reservar(Reserva reserva) throws Exception {
        //Comprobamos si existe la reserva
        if (reserva.getId() != null) {
            Optional<Reserva> checkedReserva = reservaRepository.findById(reserva.getId());
            if (!checkedReserva.isEmpty()) {
                throw new Exception("Ya existe una reserva con ID: " + reserva.getId());
            }
        }
        //Comprobamos que haya plazas en el autobus
        Optional<Bus> bus = busRepository.findById(reserva.getBus().getId());
        if (bus.get().getReservas().size() >= bus.get().getCapacidad()) {
            throw new Exception("No hay plazas disponibles en el autobus.");
        }
        //Realizamos reserva
        bus.get().getReservas().add(reserva);
        return reservaRepository.save(reserva);
    }

    @Override
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Override
    public Reserva findById(String id) throws Exception {
        return reservaRepository.findById(id).orElseThrow(() -> new Exception("No existe una reserva con ID: " + id));
    }

    @Override
    public String deleteById(String id) throws Exception {
        Optional<Reserva> reservaChecked = reservaRepository.findById(id);
        if (reservaChecked.isEmpty()) {
            throw new Exception("No existe una reserva con ID: " + id);
        }
        reservaRepository.deleteById(id);
        return "Se ha borrado correctamente la reserva";
    }

    @Override
    public Reserva updateReserva(Reserva reserva, String id) throws Exception {
        //Comprobamos si existe la Reserva
        Optional<Reserva> reservaChecked = reservaRepository.findById(id);
        if (reservaChecked.isEmpty()) {
            throw new Exception("No existe reserva con ID: " + reserva.getId());
        }
        //TODO: comprobar nulos
        reserva.setId(id);
        return reservaRepository.save(reserva);
    }


}
