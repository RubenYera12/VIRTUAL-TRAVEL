package com.Ruben.BackEmpresa.reserva.application;

import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.bus.infrastructure.repository.BusRepository;
import com.Ruben.BackEmpresa.email.application.EmailService;
import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import com.Ruben.BackEmpresa.reserva.infrastructure.repository.ReservaRepository;
import com.Ruben.BackEmpresa.shared.exceptions.NotFoundException;
import com.Ruben.BackEmpresa.shared.exceptions.UnprocesableException;
import com.Ruben.BackEmpresa.shared.kafka.Producer.KafkaSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final BusRepository busRepository;
    private final EmailService emailService;
    private final KafkaSender kafkaSender;

    @Value("${server.port}")
    private String port;

    @Value("${topic}")
    private String topic;

    private final String CLASE = "RESERVA";

    public ReservaServiceImpl(ReservaRepository reservaRepository, BusRepository busRepository, EmailService emailService, KafkaSender kafkaSender) {
        this.reservaRepository = reservaRepository;
        this.busRepository = busRepository;
        this.emailService = emailService;
        this.kafkaSender = kafkaSender;
    }

    @Override
    public Reserva reservar(Reserva reserva) throws NotFoundException,UnprocesableException, UnsupportedEncodingException {
        //Comprobamos si el usuario ya ha reservado
        Optional<Reserva> reservaCheck = reservaRepository
                .findByCorreoAndFechaReservaAndHoraReservaAndCiudadDestino(reserva.getCorreo(), reserva.getFechaReserva(), reserva.getHoraReserva(), reserva.getCiudadDestino());
        if (reservaCheck.isPresent()) {
            //Comprobamos el estado de la reserva
            if (reservaCheck.get().getEstado().equals("ACEPTADO")) {
                throw new UnprocesableException("Ya has reservado");
            }
            reserva.setId(reservaCheck.get().getId());
        }
        //Buscamos el autobus con los datos de la reserva
        Bus bus = busRepository
                .findByCiudadDestinoAndFechaReservaAndHoraReserva(reserva.getCiudadDestino(), reserva.getFechaReserva(), reserva.getHoraReserva())
                .orElseThrow(() -> new NotFoundException("No existe un viaje con estos parámetros."));
        //Comprobamos que haya plazas en el autobus y el estado del autobus
        if (checkPlazas(bus) && bus.getEstado().equals("ACTIVO")) {
            reserva.setEstado("ACEPTADO");
            reserva.setBus(bus);
            bus.getReservas().add(reserva);
            reserva = reservaRepository.save(reserva);
            emailService.emailConfirmacion(reserva);
        } else {
            reserva.setEstado("CANCELADO");
            reserva.setBus(null);
            // No guardamos la reserva en la BDD Central
            //reservaRepository.save(reserva);
        }

        //Enviamos la reserva al BackWeb
        kafkaSender.sendMessage(topic, reserva, port, "reservar", CLASE);
        return reserva;
    }

    @Override
    public void listenTopic(String s, Reserva readValue) throws NotFoundException,UnprocesableException,UnsupportedEncodingException {
        switch (s) {
            case "reservar" -> reservar(readValue);
            case "cancelar1Reserva" -> cancelReservaById(readValue.getId());
        }
    }

    @Override
    public void cancelAllReservas(Date fecha, Float hora, String ciudad) throws NotFoundException,UnprocesableException,UnsupportedEncodingException {
        //Buscamos el autobus
        Bus bus = busRepository
                .findByCiudadDestinoAndFechaReservaAndHoraReserva(ciudad, fecha, hora)
                .orElseThrow(() -> new NotFoundException("No se ha encontrado un autobus con estos parámetros"));
        //Comprobamos el estado del autobus
        if (bus.getEstado().equals("CANCELADO"))
            throw new UnprocesableException("El viaje ya está cancelado");
        //Mandamos correo de cancelación a todos los usuarios y cancelamos las reservas
        List<Reserva> reservaList = new ArrayList<>(bus.getReservas());
        bus.getReservas().clear();
        for (Reserva reserva : reservaList) {
            emailService.emailCancelacionViaje(reserva);
            reservaRepository.delete(reserva);
        }
        //Cancelamos el autobus
        bus.setEstado("CANCELADO");
        busRepository.save(bus);
        kafkaSender.sendMessage(topic, bus, port, "cancelarBus", "BUS");
    }

    @Override
    public void cancelReservaById(String id_reserva) throws NotFoundException,UnprocesableException,UnsupportedEncodingException {
        //Buscamos la reserva en la BDD
        Reserva reserva = reservaRepository
                .findById(id_reserva)
                .orElseThrow(() -> new NotFoundException("No se ha encontrado una reserva con ID: " + id_reserva));

        //Comprobamos estado de la reserva
        if (reserva.getEstado().equals("CANCELADO"))
            throw new UnprocesableException("La reserva ya ha sido cancelada");

        //Cancelamos la reserva
        reservaRepository.delete(reserva);
        emailService.emailCancelacionReserva(reserva);
        kafkaSender.sendMessage(topic, reserva, port, "cancelar1Reserva", CLASE);
    }

    @Override
    public List<Reserva> findReservaByConditions(Date fecha, Float hora, String ciudad) throws NotFoundException {
        Bus bus = busRepository
                .findByCiudadDestinoAndFechaReservaAndHoraReserva(ciudad, fecha, hora)
                .orElseThrow(() -> new NotFoundException("No se ha encontrado un autobus con estos requisitos."));
        return bus.getReservas();
    }

    //Comprueba si existen plazas disponibles
    public boolean checkPlazas(Bus bus) {
        return bus.getReservas().size() < bus.getCapacidad();
    }


    @Override
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Override
    public Reserva findById(String id) throws NotFoundException {
        return reservaRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe una reserva con ID: " + id));
    }

    @Override
    public String deleteById(String id) throws NotFoundException {
        Optional<Reserva> reservaChecked = reservaRepository.findById(id);
        if (reservaChecked.isEmpty()) {
            throw new NotFoundException("No existe una reserva con ID: " + id);
        }
        reservaRepository.deleteById(id);
        kafkaSender.sendMessage(topic,reservaChecked.get(),port,"borrarReserva",CLASE);
        return "Se ha borrado correctamente la reserva";
    }
}
