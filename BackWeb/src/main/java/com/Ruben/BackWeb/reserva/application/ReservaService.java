package com.Ruben.BackWeb.reserva.application;

import com.Ruben.BackWeb.reserva.domain.Reserva;
import com.Ruben.BackWeb.shared.exceptions.NotFoundException;
import com.Ruben.BackWeb.shared.exceptions.UnprocesableException;

import java.util.Date;
import java.util.List;

public interface ReservaService {
    List<Reserva> findAll();
    Reserva findById(String id) throws NotFoundException;
    void deleteById(String id) throws NotFoundException;
    void listenTopic(String s, Reserva readValue) throws Exception;
    void cancelCancelFromBackEmpresa(Reserva reserva);
    void cancelAllReservas(Date fecha, Float hora, String ciudad) throws NotFoundException,UnprocesableException;
    //Viene una reserva desde el backEmpresa y la guarda
    void saveReservaFromBackEmpresa(Reserva reserva) throws NotFoundException;
    void cancelReservaById(String id_reserva) throws NotFoundException,UnprocesableException;
    List<Reserva> findByCiudadDestinoAndFechaReservaAndHoraReserva(String ciudad,Date fecha, Float hora) throws NotFoundException;
    Reserva reservar(Reserva reserva) throws UnprocesableException,NotFoundException;
}
