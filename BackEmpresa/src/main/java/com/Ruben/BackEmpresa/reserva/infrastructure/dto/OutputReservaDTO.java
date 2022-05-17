package com.Ruben.BackEmpresa.reserva.infrastructure.dto;

import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutputReservaDTO {
    private String id;
    private String ciudadDestino;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private Date fechaReserva;
    private Float HoraReserva;
    private String estado;
    private Bus bus;

    public OutputReservaDTO(Reserva reserva){
        setId(reserva.getId());
        setCiudadDestino(reserva.getCiudadDestino());
        setNombre(reserva.getNombre());
        setApellido(reserva.getApellido());
        setTelefono(reserva.getTelefono());
        setCorreo(reserva.getCorreo());
        setFechaReserva(reserva.getFechaReserva());
        setHoraReserva(reserva.getHoraReserva());
        setEstado(reserva.getEstado());
        setBus(reserva.getBus());
    }
}
