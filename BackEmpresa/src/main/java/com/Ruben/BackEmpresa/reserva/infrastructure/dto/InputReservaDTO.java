package com.Ruben.BackEmpresa.reserva.infrastructure.dto;

import com.Ruben.BackEmpresa.bus.domain.Bus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputReservaDTO {
    private String id;
    private String ciudadDestino;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private Date fechaReserva;
    private Float HoraReserva;
    private Bus bus;
}
