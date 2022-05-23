//package com.Ruben.BackWeb.shared.kafka.baeldungExample;
//
//import com.Ruben.BackWeb.reserva.domain.Reserva;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("api/Empresa/baeldung")
//public class KafkaController {
//    @Autowired
//    KafkaProducerConfig kafkaMessageProducer;
//
//    @PostMapping("/add")
//    public void addIdCustomer(@RequestBody Reserva reserva)
//    {
//        kafkaMessageProducer.sendMessage(reserva);
//    }
//}