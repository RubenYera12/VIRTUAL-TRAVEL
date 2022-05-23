package com.Ruben.BackWeb.reserva.infrastructure.dto;

import com.Ruben.BackWeb.bus.domain.Bus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputReservaDTO {
    private String ciudadDestino;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private Date fechaReserva;
    private Float HoraReserva;
    private String estado;
}
