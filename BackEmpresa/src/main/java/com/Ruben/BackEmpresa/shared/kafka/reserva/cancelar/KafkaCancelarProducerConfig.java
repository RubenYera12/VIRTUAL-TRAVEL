//package com.Ruben.BackEmpresa.shared.kafka.reserva.cancelar;
//
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
//import org.springframework.stereotype.Component;
//import org.springframework.util.concurrent.ListenableFuture;
//import org.springframework.util.concurrent.ListenableFutureCallback;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//@Configuration
//public class KafkaCancelarProducerConfig {
//
//    @Value(value = "${spring.kafka.bootstrap-servers}")
//    private String bootstrapAddress;
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    public void sendMessage(String message) {
//
//        ListenableFuture<SendResult<String, String>> future =
//                kafkaTemplate.send("CANCELAR_RESERVA_FROM_EMPRESA", message);
//
//        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//
//            @Override
//            public void onSuccess(SendResult<String, String> result) {
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
//    public ProducerFactory<String, String> producerFactoryCancelar() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(
//                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                bootstrapAddress);
//        configProps.put(
//                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
//                StringSerializer.class);
//        configProps.put(
//                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//                StringSerializer.class);
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplateCancelar() {
//        return new KafkaTemplate<String,String>(producerFactoryCancelar());
//    }
//}
