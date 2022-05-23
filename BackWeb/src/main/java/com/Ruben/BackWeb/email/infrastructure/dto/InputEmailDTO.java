package com.Ruben.BackWeb.email.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InputEmailDTO {

    private String ciudadDestino;
    private String email;
    private Date fechaReserva;
    private Float horaReserva;

}
