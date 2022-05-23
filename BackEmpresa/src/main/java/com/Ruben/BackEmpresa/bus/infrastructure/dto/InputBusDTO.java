package com.Ruben.BackEmpresa.bus.infrastructure.dto;

import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputBusDTO {
    private String id;
    private final int capacidad = 40;
    private List<Reserva> reservas=new ArrayList<>();
    private String ciudadDestino;
    private Date fechaReserva;
    private Float HoraReserva;
    private String estado = "ACTIVO";
}
