//package com.Ruben.BackEmpresa.shared.kafka.reserva.reservar;
//
//import com.Ruben.BackEmpresa.reserva.application.ReservaService;
//import com.Ruben.BackEmpresa.reserva.domain.Reserva;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@EnableKafka
//@Component
//public class KafkaReservaConsumerConfig {
//
//    @Autowired
//    private ReservaService reservaService;
//
//    @Value(value = "${spring.kafka.bootstrap-servers}")
//    private String bootstrapAddress;
//
//    @Value(value = "${message.group.name}")
//    private String group;
//
//    @KafkaListener(topics = "RESERVAR_FROM_WEB", groupId = "BackEmpresa")
//    public void listenGroupReservar(Reserva message){
//        try {
//            System.out.println(message);
//            reservaService.reservar(message);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
////        System.out.println("Received Message in group +"+group+": " + message);
//    }
//
//    @Bean
//    public ConsumerFactory<String, Reserva> consumerFactoryReservar() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(
//                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                bootstrapAddress);
//        props.put(
//                ConsumerConfig.GROUP_ID_CONFIG,
//                group);
//        props.put(
//                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class);
//        props.put(
//                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//                JsonDeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Reserva>
//    kafkaListenerContainerFactoryReservar() {
//
//        ConcurrentKafkaListenerContainerFactory<String, Reserva> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactoryReservar());
//        return factory;
//    }
//}
