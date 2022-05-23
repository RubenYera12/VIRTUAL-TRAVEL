//package com.Ruben.BackEmpresa.shared.kafka.chuchiExample;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("api/Empresa/messages")
//public class KafkaTestController {
//    @Autowired
//    KafkaMessageProducer kafkaMessageProducer;
//
//    @PostMapping("/add/{topic}")
//    public void addIdCustomer(@PathVariable String topic, @RequestBody String body)
//    {
//        kafkaMessageProducer.sendMessage(topic,body);
//    }
//}
