package com.Ruben.BackWeb.bus.application;

import com.Ruben.BackWeb.bus.domain.Bus;

import java.util.Date;
import java.util.List;

public interface BusService {
    Bus addBus(Bus bus) throws Exception;
    List<Bus> findAll();
    Bus findById(String id) throws Exception;
    String deleteById(String id) throws Exception;
    Bus updateBus(Bus bus,String id) throws Exception;
    Bus findByCiudadDestinoAndFechaReservaAndHoraReserva(String ciudad,Date fecha, Float hora) throws Exception;
    void listenTopic(String s, Bus readValue);
}
