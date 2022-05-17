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
        //Comprobamos si el usuario ya ha reservado
        Optional<Reserva> reservaCheck = reservaRepository
                .findByCorreoAndFechaReservaAndHoraReservaAndCiudadDestino(reserva.getCorreo(), reserva.getFechaReserva(), reserva.getHoraReserva(), reserva.getCiudadDestino());
        if (reservaCheck.isPresent()) {
            //Comprobamos el estado de la reserva
            if (reservaCheck.get().getEstado().equals("ACEPTADO")) {
                throw new Exception("Ya has reservado");
            }
            reserva.setId(reservaCheck.get().getId());
        }
        //Buscamos el autobus con los datos de la reserva
        Bus bus = busRepository
                .findByCiudadDestinoAndFechaReservaAndHoraReserva(reserva.getCiudadDestino(), reserva.getFechaReserva(), reserva.getHoraReserva())
                .orElseThrow(() -> new Exception("No existe un viaje con estos parámetros."));
        //Comprobamos que haya plazas en el autobus y el estado del autobus
        if (checkPlazas(bus) && bus.getEstado().equals("ACTIVO")) {
            reserva.setEstado("ACEPTADO");
            reserva.setBus(bus);
            bus.getReservas().add(reserva);
            emailService.emailConfirmacion(reserva);
        } else {
            reserva.setEstado("CANCELADO");
            reserva.setBus(null);
        }

        //Realizamos reserva
        return reservaRepository.save(reserva);
    }

    @Override
    public void cancelAllReservas(Date fecha, Float hora, String ciudad) throws Exception {
        //Buscamos el autobus
        Bus bus = busRepository
                .findByCiudadDestinoAndFechaReservaAndHoraReserva(ciudad, fecha, hora)
                .orElseThrow(() -> new Exception("No se ha encontrado un autobus con estos parámetros"));
        //Comprobamos el estado del autobus
        if (bus.getEstado().equals("CANCELADO"))
            throw new Exception("El viaje ya está cancelado");
        //Mandamos correo de cancelación a todos los usuarios y cancelamos las reservas
        for (Reserva reserva : bus.getReservas()) {
            emailService.emailCancelacionViaje(reserva);
            reserva.setBus(null);
            reserva.setEstado("CANCELADO");
        }
        bus.getReservas().clear();
        bus.setEstado("CANCELADO");
        busRepository.save(bus);
    }

    @Override
    public void cancelReservaById(String id_reserva) throws Exception {
        //Buscamos la reserva en la BDD
        Reserva reserva = reservaRepository.findById(id_reserva).orElseThrow(() -> new Exception("No se ha encontrado una reserva con ID: " + id_reserva));

        //Comprobamos estado de la reserva
        if (reserva.getEstado().equals("CANCELADO"))
            throw new Exception("La reserva ya ha sido cancelada");

        //Cancelamos la reserva
        Bus bus = reserva.getBus();
        bus.getReservas().remove(reserva);
        reserva.setBus(null);
        reserva.setEstado("CANCELADO");
        reservaRepository.save(reserva);
        emailService.emailCancelacionReserva(reserva);


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
    public boolean checkPlazas(Bus bus) throws Exception {
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
