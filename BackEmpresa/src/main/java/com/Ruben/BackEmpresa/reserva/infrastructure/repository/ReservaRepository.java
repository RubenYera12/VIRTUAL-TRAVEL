package com.Ruben.BackEmpresa.reserva.infrastructure.repository;

import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva,String> {

}
