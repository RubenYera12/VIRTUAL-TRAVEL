package com.Ruben.BackEmpresa.reserva.infrastructure.repository;

import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva,String> {
    Optional<Reserva> findByCorreoAndFechaReservaAndHoraReservaAndCiudadDestino(String correo, Date fechaReserva, Float horaReserva, String ciudadDestino);
}
