package com.Ruben.BackWeb.reserva.domain;

import com.Ruben.BackWeb.StringPrefixedSequenceIdGenerator;
import com.Ruben.BackWeb.bus.domain.Bus;
import com.Ruben.BackWeb.reserva.infrastructure.dto.InputReservaDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservaSeq")
    @GenericGenerator(
            name = "reservaSeq",
            strategy = "com.Ruben.BackWeb.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "RES"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
            })
    private String id;
    private String ciudadDestino;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private Date fechaReserva;
    private Float horaReserva;
    private String estado;
    @ManyToOne
    @JsonBackReference
    private Bus bus;

    public Reserva(InputReservaDTO inputReservaDTO){
        setCiudadDestino(inputReservaDTO.getCiudadDestino());
        setNombre(inputReservaDTO.getNombre());
        setApellido(inputReservaDTO.getApellido());
        setTelefono(inputReservaDTO.getTelefono());
        setCorreo(inputReservaDTO.getCorreo());
        setFechaReserva(inputReservaDTO.getFechaReserva());
        setHoraReserva(inputReservaDTO.getHoraReserva());
        setEstado(inputReservaDTO.getEstado());
    }
}
