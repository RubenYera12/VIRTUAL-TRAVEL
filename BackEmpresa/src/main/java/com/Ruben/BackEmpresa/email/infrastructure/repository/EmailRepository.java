package com.Ruben.BackEmpresa.email.infrastructure.repository;

import com.Ruben.BackEmpresa.email.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email,String> {
    public List<Email> findByEmail(String email);
    public List<Email> findByCiudadDestinoLikeAndFechaReservaBetweenAndHoraReservaBetween(String ciudadDestino, Date fechaDestino_dateStart,Date fechaDestino_dateEnd,Float horaInferior,Float horaSuperior);
}
