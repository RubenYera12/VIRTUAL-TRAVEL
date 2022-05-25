package com.Ruben.BackWeb.bus.infrastructure.dto;

import com.Ruben.BackWeb.reserva.domain.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
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
    private Date fechaReserva;
    @NotNull(message = "Introduce una hora de viaje")
    private Float HoraReserva;
    private String estado = "ACTIVO";
}
