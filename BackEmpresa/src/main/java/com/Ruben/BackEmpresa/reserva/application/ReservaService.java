package com.Ruben.BackEmpresa.reserva.application;

import com.Ruben.BackEmpresa.reserva.domain.Reserva;

import java.util.List;

public interface ReservaService {
    Reserva addReserva(Reserva reserva) throws Exception;
    List<Reserva> findAll();
    Reserva findById(String id) throws Exception;
    String deleteById(String id) throws Exception;
    Reserva updateReserva(Reserva reserva,String id) throws Exception;
    Reserva reservar(Reserva reserva) throws Exception;
}
