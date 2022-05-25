package com.Ruben.BackEmpresa.bus.infrastructure.dto;

import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputBusDTO {
    @PositiveOrZero(message = "La capacidad no puede ser negativa")
    private final int capacidad = 40;
    private List<Reserva> reservas=new ArrayList<>();
    @NotNull(message = "Introduce una ciudad de destino")
    private String ciudadDestino;
    @NotNull(message = "Introduce una fecha de viaje")
    @FutureOrPresent
    private Date fechaReserva;
    @NotNull(message = "Introduce una hora de viaje")
    @Min(value = 0,message = "La hora no puede ser negativa")
    @Max(value = 24,message = "La hora no puede ser superior a 24")
    private Float HoraReserva;
    private String estado = "ACTIVO";
}
