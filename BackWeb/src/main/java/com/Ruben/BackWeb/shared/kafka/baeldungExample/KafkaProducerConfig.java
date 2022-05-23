//package com.Ruben.BackWeb.shared.kafka.baeldungExample;
//
//import com.Ruben.BackWeb.reserva.domain.Reserva;
//import com.Ruben.BackWeb.shared.kafka.serializer.ReservaSerializer;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//import org.springframework.util.concurrent.ListenableFuture;
//import org.springframework.util.concurrent.ListenableFutureCallback;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaProducerConfig {
//
//    @Value(value = "${spring.kafka.bootstrap-servers}")
//    private String bootstrapAddress;
//
//    @Autowired
//    private KafkaTemplate<String, Reserva> kafkaTemplate;
//
//    public void sendMessage(Reserva message) {
//
//        ListenableFuture<SendResult<String, Reserva>> future =
//                kafkaTemplate.send("RESERVAR_FROM_WEB", message);
//
//        future.addCallback(new ListenableFutureCallback<SendResult<String, Reserva>>() {
//
//            @Override
//            public void onSuccess(SendResult<String, Reserva> result) {
//                System.out.println("Sent message=[" + message +
//                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
//            }
//            @Override
//            public void onFailure(Throwable ex) {
//                System.out.println("Unable to send message=["
//                        + message + "] due to : " + ex.getMessage());
//            }
//        });
//    }
//
//    @Bean
//    public ProducerFactory<String, Reserva> producerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(
//                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                bootstrapAddress);
//        configProps.put(
//                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
//                StringSerializer.class);
//        configProps.put(
//                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//                JsonSerializer.class);
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    @Bean
//    public KafkaTemplate<String, Reserva> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//}
