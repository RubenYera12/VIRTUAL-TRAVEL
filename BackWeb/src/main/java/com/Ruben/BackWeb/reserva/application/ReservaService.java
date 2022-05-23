package com.Ruben.BackWeb.reserva.application;

import com.Ruben.BackWeb.bus.domain.Bus;
import com.Ruben.BackWeb.reserva.domain.Reserva;

import java.util.Date;
import java.util.List;

public interface ReservaService {
    List<Reserva> findAll();
    Reserva findById(String id) throws Exception;
    String deleteById(String id) throws Exception;
    Reserva updateReserva(Reserva reserva,String id) throws Exception;

    void listenTopic(String s, Reserva readValue) throws Exception;

    void cancel(Reserva reserva) throws Exception;

    void cancelAllReservas(Date fecha, Float hora, String ciudad) throws Exception;

    //Viene una reserva desde el backEmpresa y la guarda
    void saveReservaFromBackEmpresa(Reserva reserva) throws Exception;


    void cancelReservaById(String id_reserva) throws Exception;

    List<Reserva> findReservaByConditions(Date fecha, Float hora, String ciudad) throws Exception;

    boolean checkPlazas(Bus bus) throws Exception;

    Reserva reservar(Reserva reserva) throws Exception;
}
