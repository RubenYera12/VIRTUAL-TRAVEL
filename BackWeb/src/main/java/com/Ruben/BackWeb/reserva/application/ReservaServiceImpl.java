package com.Ruben.BackWeb.reserva.application;

import com.Ruben.BackWeb.bus.domain.Bus;
import com.Ruben.BackWeb.bus.infrastructure.repository.BusRepository;
import com.Ruben.BackWeb.email.application.EmailService;
import com.Ruben.BackWeb.reserva.domain.Reserva;
import com.Ruben.BackWeb.reserva.infrastructure.repository.ReservaRepository;
import com.Ruben.BackWeb.shared.kafka.Producer.KafkaSender;
import org.hibernate.annotations.Proxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
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
    //    private KafkaProducerConfig kafkaProducerConfig;
//    private KafkaCancelarProducerConfig kafkaCancelarProducerConfig;

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
        } else {
            reserva.setEstado("CANCELADO");
            reserva.setBus(null);
        }

        //Se lo mandamos a BACKEMPRESA para que valide
        kafkaSender.sendMessage(topic,reserva,port,"reservar",CLASE);
        //kafkaProducerConfig.sendMessage(reserva);
        return reserva;
    }

    @Override
    public void listenTopic(String s, Reserva readValue) throws Exception {
        switch (s){
            case "reservar" ->{
                saveReservaFromBackEmpresa(readValue);
            }
            case "cancelar1Reserva"->{
                cancel(readValue);
            }
        }
    }

    @Override
    public void cancel(Reserva reserva) throws Exception {
        reserva.setBus(null);
        reserva.setEstado("CANCELADO");
        reservaRepository.save(reserva);
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
    //Viene una reserva desde el backEmpresa y la guarda
    @Override
    public void saveReservaFromBackEmpresa(Reserva reserva) throws Exception {
        Bus bus = busRepository.findByCiudadDestinoAndFechaReservaAndHoraReserva(reserva.getCiudadDestino(),reserva.getFechaReserva(),reserva.getHoraReserva()).orElseThrow(()->new Exception("No se ha encontrado un autobus con estos parametros."));
        reserva.setBus(bus);
        reservaRepository.save(reserva);
    }

    @Override
    public void cancelReservaById(String id_reserva) throws Exception {
        //Buscamos la reserva en la BDD
        Reserva reserva = reservaRepository.findById(id_reserva).orElseThrow(() -> new Exception("No se ha encontrado una reserva con ID: " + id_reserva));

        //Comprobamos estado de la reserva
        if (reserva.getEstado().equals("CANCELADO"))
            throw new Exception("La reserva ya ha sido cancelada");
        kafkaSender.sendMessage(topic,reserva,port,"cancelar1Reserva",CLASE);
        //Cancelamos la reserva
//        Bus bus = reserva.getBus();
//        bus.getReservas().remove(reserva);
//        reserva.setBus(null);
//        reserva.setEstado("CANCELADO");
//        reservaRepository.save(reserva);
//        emailService.emailCancelacionReserva(reserva);
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
