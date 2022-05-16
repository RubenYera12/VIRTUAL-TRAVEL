package com.Ruben.BackEmpresa.bus.infrastructure.repository;

import com.Ruben.BackEmpresa.bus.domain.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, String> {
    //    Bus getData(HashMap<String, Object> conditions);

    Optional<Bus> findByCiudadDestinoAndFechaReservaAndHoraReserva(String ciudadDestino, Date fechaReserva, Float horaReserva);
}
