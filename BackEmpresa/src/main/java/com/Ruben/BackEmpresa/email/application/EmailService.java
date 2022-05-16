package com.Ruben.BackEmpresa.email.application;

import com.Ruben.BackEmpresa.email.domain.Email;
import com.Ruben.BackEmpresa.reserva.domain.Reserva;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface EmailService {

    void emailConfirmacion(Reserva reserva) throws UnsupportedEncodingException;

    void emailCancelacion(Reserva reserva) throws UnsupportedEncodingException;

    List<Email> findByEmail(String email);
}
