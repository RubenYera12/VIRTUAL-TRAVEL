package com.Ruben.BackEmpresa.reserva.application;

import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.reserva.domain.Reserva;

import java.util.Date;
import java.util.List;

public interface ReservaService {
    List<Reserva> findAll();
    Reserva findById(String id) throws Exception;
    String deleteById(String id) throws Exception;
    Reserva updateReserva(Reserva reserva,String id) throws Exception;

    void cancelAllReservas(Date fecha, Float hora, String ciudad) throws Exception;

    void cancelReservaById(String id_reserva) throws Exception;

    List<Reserva> findReservaByConditions(Date fecha, Float hora, String ciudad) throws Exception;

    boolean checkPlazas(Bus bus) throws Exception;

    Reserva reservar(Reserva reserva) throws Exception;
}
