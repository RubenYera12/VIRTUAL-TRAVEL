package com.Ruben.BackWeb.reserva.application;

import com.Ruben.BackWeb.bus.domain.Bus;
import com.Ruben.BackWeb.bus.infrastructure.repository.BusRepository;
import com.Ruben.BackWeb.reserva.domain.Reserva;
import com.Ruben.BackWeb.reserva.infrastructure.repository.ReservaRepository;
import com.Ruben.BackWeb.shared.exceptions.NotFoundException;
import com.Ruben.BackWeb.shared.exceptions.UnprocesableException;
import com.Ruben.BackWeb.shared.kafka.Producer.KafkaSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImpl implements ReservaService {
    private final ReservaRepository reservaRepository;
    private final BusRepository busRepository;

    private final KafkaSender kafkaSender;
    @Value("${server.port}")
    private String port;

    @Value("${topic}")
    private String topic;

    private final String CLASE = "RESERVA";

    public ReservaServiceImpl(ReservaRepository reservaRepository, BusRepository busRepository, KafkaSender kafkaSender) {
        this.reservaRepository = reservaRepository;
        this.busRepository = busRepository;
        this.kafkaSender = kafkaSender;
    }

    @Override
    public Reserva reservar(Reserva reserva) throws UnprocesableException,NotFoundException {
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
        } else {
            reserva.setEstado("CANCELADO");
            reserva.setBus(null);
        }

        //Se lo mandamos a BACKEMPRESA para que valide
        kafkaSender.sendMessage(topic, reserva, port, "reservar", CLASE);
        return reserva;
    }

    @Override
    public void listenTopic(String s, Reserva readValue) throws NotFoundException {
        switch (s) {
            case "reservar" -> saveReservaFromBackEmpresa(readValue);
            case "cancelar1Reserva" -> cancelCancelFromBackEmpresa(readValue);
            case "borrarReserva" -> deleteById(readValue.getId());
        }
    }

    @Override
    public void cancelCancelFromBackEmpresa(Reserva reserva)  {
        reserva.setBus(null);
        reserva.setEstado("CANCELADO");
        reservaRepository.save(reserva);
    }

    @Override
    public void cancelAllReservas(Date fecha, Float hora, String ciudad) throws NotFoundException,UnprocesableException {
        //Buscamos el autobus
        Bus bus = busRepository
                .findByCiudadDestinoAndFechaReservaAndHoraReserva(ciudad, fecha, hora)
                .orElseThrow(() -> new NotFoundException("No se ha encontrado un autobus con estos parámetros"));
        //Comprobamos el estado del autobus
        if (bus.getEstado().equals("CANCELADO"))
            throw new UnprocesableException("El viaje ya está cancelado");
        //Mandamos correo de cancelación a todos los usuarios y cancelamos las reservas
        for (Reserva reserva : bus.getReservas()) {
            reserva.setBus(null);
            reserva.setEstado("CANCELADO");
        }
        bus.getReservas().clear();
        bus.setEstado("CANCELADO");
        busRepository.save(bus);
    }

    //Viene una reserva desde el backEmpresa y la guarda
    @Override
    public void saveReservaFromBackEmpresa(Reserva reserva) throws NotFoundException {
        Bus bus = busRepository
                .findByCiudadDestinoAndFechaReservaAndHoraReserva(reserva.getCiudadDestino(), reserva.getFechaReserva(), reserva.getHoraReserva())
                .orElseThrow(() -> new NotFoundException("No se ha encontrado un autobus con estos parametros."));
        reserva.setBus(bus);
        reservaRepository.save(reserva);
    }

    @Override
    public void cancelReservaById(String id_reserva) throws NotFoundException,UnprocesableException {
        //Buscamos la reserva en la BDD
        Reserva reserva = reservaRepository.findById(id_reserva).orElseThrow(() -> new NotFoundException("No se ha encontrado una reserva con ID: " + id_reserva));

        //Comprobamos estado de la reserva
        if (reserva.getEstado().equals("CANCELADO"))
            throw new UnprocesableException("La reserva ya ha sido cancelada");
        //Pedimos al BackEmpresa que cancele esta reserva
        kafkaSender.sendMessage(topic, reserva, port, "cancelar1Reserva", CLASE);
    }

    @Override
    public List<Reserva> findByCiudadDestinoAndFechaReservaAndHoraReserva(String ciudad, Date fecha, Float hora) throws NotFoundException {
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
    public void deleteById(String id) throws NotFoundException {
        Optional<Reserva> reservaChecked = reservaRepository.findById(id);
        if (reservaChecked.isEmpty()) {
            throw new NotFoundException("No existe una reserva con ID: " + id);
        }
        reservaRepository.deleteById(id);
    }
}
