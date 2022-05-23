package com.Ruben.BackEmpresa.shared.kafka.listener;

import com.Ruben.BackEmpresa.bus.application.BusService;
import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.email.application.EmailService;
import com.Ruben.BackEmpresa.email.domain.Email;
import com.Ruben.BackEmpresa.reserva.application.ReservaService;
import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaRouter {
    @Value("${server.port}")
    String puerto;

    @Autowired
    BusService busService;

    @Autowired
    ReservaService reservaService;

    @Autowired
    EmailService emailService;

    @KafkaListener(topics = "BACKWEB", groupId = "${group}")
    public void listenTopic(@Payload ConsumerRecord<String, Object> record) throws JsonProcessingException {

        final String[] port = new String[1];
        final String[] action = new String[1];
        final String[] clase = new String[1];
        ObjectMapper mapper = new ObjectMapper();

        record.headers().headers("port").forEach(header -> {
            port[0] = new String(header.value());
        });

        record.headers().headers("action").forEach(header -> {
            action[0] = new String(header.value());
        });

        record.headers().headers("clase").forEach(header -> {
            clase[0] = new String(header.value());
        });

        if (!port[0].equals(puerto)) {
            System.out.println("Mensaje recibido");
            switch (clase[0]) {
                case "BUS" -> {
                    System.out.println("RECIBIDO BUS! accion: " + action[0]);
                    busService.listenTopic(action[0], mapper.readValue((String)record.value(), Bus.class));
                }
                case "RESERVA" -> {
                    System.out.println("RECIBIDO RESERVA! accion: " + action[0]);
                    try {
                        reservaService.listenTopic(action[0], mapper.readValue((String)record.value(), Reserva.class));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "EMAIL" -> {
                    System.out.println("RECIBIDO MAIL! accion: " + action[0]);
                    emailService.listenTopic(action[0], mapper.readValue((String)record.value(), Email.class));
                }
                default -> System.out.println("error");
            }
        }
    }
}
