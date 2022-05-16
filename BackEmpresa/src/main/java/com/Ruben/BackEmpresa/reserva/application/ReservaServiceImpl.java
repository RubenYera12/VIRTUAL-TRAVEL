package com.Ruben.BackEmpresa.reserva.application;

import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.bus.infrastructure.repository.BusRepository;
import com.Ruben.BackEmpresa.email.application.EmailService;
import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import com.Ruben.BackEmpresa.reserva.infrastructure.repository.ReservaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservaServiceImpl implements ReservaService {
    private final ReservaRepository reservaRepository;
    private final BusRepository busRepository;
    private final EmailService emailService;


    @Override
    public Reserva reservar(Reserva reserva) throws Exception {
        //Comprobamos que haya plazas en el autobus
        Bus bus = busRepository.findById(reserva.getBus().getId())
                .orElseThrow(() -> new Exception("No existe este autobus"));
        if (!check(bus)) {
            throw new Exception("No hay plazas disponibles en el autobus.");
        }
        //Comprobamos si el usuario ya ha reservado
        Optional<Reserva> reserva1 = reservaRepository
                .findByEmailAndFechaReservaAndHoraReservaAndCiudadDestino(reserva.getEmail(), reserva.getFechaReserva(), reserva.getHoraReserva(), reserva.getCiudadDestino());
        if (reserva1.isPresent())
            throw new Exception("Ya has reservado");
        //Realizamos reserva

        bus.getReservas().add(reserva);
        return reservaRepository.save(reserva);
    }

    @Override
    public void cancelar(Date fecha, Float hora, String ciudad) throws Exception {
        Bus bus = busRepository
                .findByCiudadDestinoAndFechaReservaAndHoraReserva(ciudad, fecha, hora)
                .orElseThrow(() -> new Exception("No se ha encontrado un autbus con estos requisitos"));
        for (Reserva reserva : bus.getReservas()) {
            emailService.emailCancelacion(reserva);
        }

    }

    @Override
    public List<Reserva> findReservaByConditions(Date fecha, Float hora, String ciudad) throws Exception {
        Bus bus = busRepository
                .findByCiudadDestinoAndFechaReservaAndHoraReserva(ciudad, fecha, hora)
                .orElseThrow(() -> new Exception("No se ha encontrado un autobus con estos requisitos."));
        return bus.getReservas();
    }

    //Comprueba si existen plazas disponibles
    @Override
    public boolean check(Bus bus) throws Exception {
        return bus.getReservas().size() < bus.getCapacidad();
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
