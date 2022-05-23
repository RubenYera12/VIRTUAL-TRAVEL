//package com.Ruben.BackWeb.shared.kafka.baeldungExample;
//
//import com.Ruben.BackWeb.reserva.application.ReservaService;
//import com.Ruben.BackWeb.reserva.domain.Reserva;
//import com.Ruben.BackWeb.shared.kafka.serializer.ReservaDeserializer;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@EnableKafka
//@Configuration
//public class KafkaConsumerConfig {
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
//    @KafkaListener(topics = "RESERVAR_FROM_EMPRESA", groupId = "BackWeb")
//    public void listenGroupReservar(Reserva message) {
//        try {
//            reservaService.saveReservaFromBackEmpresa(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Reserva Guardada Correctamente");
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
//                ReservaDeserializer.class);
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
