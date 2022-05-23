package com.Ruben.BackEmpresa.shared.kafka.serializer;

import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ReservaSerializer implements Serializer<Reserva> {

    @Override public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Reserva reserva) {
        byte[] retValue = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retValue = objectMapper.writeValueAsString(reserva).getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return retValue;
    }

    @Override public void close() {

    }
}
