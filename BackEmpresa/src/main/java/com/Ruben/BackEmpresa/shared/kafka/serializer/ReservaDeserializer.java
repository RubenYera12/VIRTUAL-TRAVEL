package com.Ruben.BackEmpresa.shared.kafka.serializer;

import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ReservaDeserializer implements Deserializer<Reserva> {

    @Override public void close() {

    }

    @Override public void configure(Map<String, ?> arg0, boolean arg1) {

    }

    @Override
    public Reserva deserialize(String arg0, byte[] arg1) {
        ObjectMapper mapper = new ObjectMapper();
        Reserva reserva = null;
        try {
            reserva = mapper.readValue(arg1, Reserva.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return reserva;
    }

}