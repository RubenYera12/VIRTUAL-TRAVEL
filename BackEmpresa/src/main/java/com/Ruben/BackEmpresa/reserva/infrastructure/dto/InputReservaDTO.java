package com.Ruben.BackEmpresa.reserva.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputReservaDTO {
    @NotNull(message = "Introduce una ciudad de destino")
    private String ciudadDestino;
    @NotNull(message = "Introduce tu nombre")
    private String nombre;
    @NotNull(message = "Introduce tu apellido")
    private String apellido;
    @NotNull(message = "Introduce tu telefono")
    @Pattern (regexp = "[6][0-9]{8}", message = "Error de formato de número de teléfono móvil")
    private String telefono;
    @Email(message = "Introduce un correo valido")
    private String correo;
    @NotNull(message = "Introduce una fecha de viaje")
    @FutureOrPresent
    private Date fechaReserva;
    @NotNull(message = "Introduce una hora de viaje")
    @Min(value = 0,message = "La hora no puede ser negativa")
    @Max(value = 24,message = "La hora no puede ser superior a 24")
    private Float HoraReserva;
}
