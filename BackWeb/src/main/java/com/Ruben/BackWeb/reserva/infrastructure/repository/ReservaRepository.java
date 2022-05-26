package com.Ruben.BackWeb.reserva.infrastructure.repository;

import com.Ruben.BackWeb.reserva.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva,String> {
    Optional<Reserva> findByCorreoIgnoreCaseAndFechaReservaAndHoraReservaAndCiudadDestinoIgnoreCase(String correo, Date fechaReserva, Float horaReserva, String ciudadDestino);
}
