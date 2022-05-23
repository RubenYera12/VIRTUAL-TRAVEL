package com.Ruben.BackWeb.email.infrastructure.dto;

import com.Ruben.BackWeb.email.domain.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OutputEmailDTO {
    private String ciudadDestino;
    private String email;
    private Date fechaReserva;
    private Float horaReserva;

    public OutputEmailDTO(Email email) {
        setEmail(email.getEmail());
        setCiudadDestino(email.getCiudadDestino());
        setFechaReserva(email.getFechaReserva());
        setHoraReserva(email.getHoraReserva());
    }
}
