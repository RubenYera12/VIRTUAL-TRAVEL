package com.Ruben.BackEmpresa.reserva.infrastructure.repository;

import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva,String> {
    Optional<Reserva> findByEmailAndFechaReservaAndHoraReservaAndCiudadDestino(String email, Date fechaReserva, Float horaReserva, String ciudadDestino);
}
