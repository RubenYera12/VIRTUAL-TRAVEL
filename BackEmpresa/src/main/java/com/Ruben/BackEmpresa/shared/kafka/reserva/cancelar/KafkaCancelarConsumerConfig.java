//package com.Ruben.BackEmpresa.shared.kafka.reserva.cancelar;
//
//import com.Ruben.BackEmpresa.reserva.application.ReservaService;
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
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@EnableKafka
//@Component
//public class KafkaCancelarConsumerConfig {
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
//    @KafkaListener(topics = "CANCELAR_RESERVA_FROM_WEB", groupId = "BackEmpresa")
//    public void listenGroupCancelar(String message){
//        try {
//            System.out.println(message);
//            reservaService.cancelReservaById(message);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
////        System.out.println("Received Message in group +"+group+": " + message);
//    }
//
//    @Bean
//    public ConsumerFactory<String, String> consumerFactoryCancelar() {
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
//                StringDeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String>
//    kafkaListenerContainerFactoryCancelar() {
//
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactoryCancelar());
//        return factory;
//    }
//}
