package com.Ruben.BackEmpresa.email.application;

import com.Ruben.BackEmpresa.email.domain.Email;
import com.Ruben.BackEmpresa.reserva.domain.Reserva;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

public interface EmailService {

    void emailConfirmacion(Reserva reserva) throws UnsupportedEncodingException;

    void emailCancelacionViaje(Reserva reserva) throws UnsupportedEncodingException;

    void emailCancelacionReserva(Reserva reserva) throws UnsupportedEncodingException;

    List<Email> findByEmail(String email);

    List<Email> getCorreos(String ciudadDestino, Date fechaInferior, Date fechaSuperior,Float horaInferior,Float horaSuperior);

    void listenTopic(String s, Email readValue);
}
