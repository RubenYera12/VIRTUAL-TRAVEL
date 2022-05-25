package com.Ruben.BackEmpresa.reserva.application;

import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import com.Ruben.BackEmpresa.shared.exceptions.NotFoundException;
import com.Ruben.BackEmpresa.shared.exceptions.UnprocesableException;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

public interface ReservaService {
    List<Reserva> findAll();
    Reserva findById(String id) throws NotFoundException;
    String deleteById(String id) throws NotFoundException;
    void listenTopic(String s, Reserva readValue) throws NotFoundException,UnprocesableException,UnsupportedEncodingException;
    void cancelAllReservas(Date fecha, Float hora, String ciudad) throws NotFoundException,UnprocesableException, UnsupportedEncodingException;
    void cancelReservaById(String id_reserva) throws NotFoundException,UnprocesableException,UnsupportedEncodingException;
    List<Reserva> findReservaByConditions(Date fecha, Float hora, String ciudad) throws UnprocesableException;
    Reserva reservar(Reserva reserva) throws NotFoundException,UnprocesableException,UnsupportedEncodingException;
}
