package com.Ruben.BackEmpresa.bus.application;

import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.shared.exceptions.NotFoundException;
import com.Ruben.BackEmpresa.shared.exceptions.UnprocesableException;

import java.util.Date;
import java.util.List;

public interface BusService {
    Bus addBus(Bus bus) throws UnprocesableException;
    List<Bus> findAll();
    Bus findById(String id) throws NotFoundException;
    String deleteById(String id) throws NotFoundException;
    Bus findByCiudadDestinoAndFechaReservaAndHoraReserva(String ciudad,Date fecha, Float hora) throws NotFoundException;

    Bus activarViaje(String ciudad, Date fecha, Float hora);
}
