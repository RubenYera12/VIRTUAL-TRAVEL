package com.Ruben.BackWeb.bus.application;

import com.Ruben.BackWeb.bus.domain.Bus;
import com.Ruben.BackWeb.shared.exceptions.NotFoundException;
import com.Ruben.BackWeb.shared.exceptions.UnprocesableException;

import java.util.Date;
import java.util.List;

public interface BusService {
    Bus addBus(Bus bus) throws UnprocesableException;
    List<Bus> findAll();
    Bus findById(String id) throws NotFoundException;
    String deleteById(String id) throws NotFoundException;
    Bus findByCiudadDestinoAndFechaReservaAndHoraReserva(String ciudad,Date fecha, Float hora) throws NotFoundException;
    void listenTopic(String s, Bus readValue);

    void cancelarBus(Bus readValue) throws NotFoundException,UnprocesableException;
}
