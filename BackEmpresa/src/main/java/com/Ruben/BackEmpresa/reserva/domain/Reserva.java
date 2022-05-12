package com.Ruben.BackEmpresa.reserva.domain;

import com.Ruben.BackEmpresa.StringPrefixedSequenceIdGenerator;
import com.Ruben.BackEmpresa.bus.domain.Bus;
import com.Ruben.BackEmpresa.reserva.infrastructure.dto.InputReservaDTO;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @GenericGenerator(
            name = "seq",
            strategy = "com.Ruben.BackEmpresa.StringPrefixedSequenceIdGenerator",
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
    private String email;
    private Date fechaReserva;
    private Float horaReserva;
    @ManyToOne
    @JsonIgnore
    private Bus bus;

    public Reserva(InputReservaDTO inputReservaDTO){
        setId(inputReservaDTO.getId());
        setCiudadDestino(inputReservaDTO.getCiudadDestino());
        setNombre(inputReservaDTO.getNombre());
        setApellido(inputReservaDTO.getApellido());
        setTelefono(inputReservaDTO.getTelefono());
        setEmail(inputReservaDTO.getEmail());
        setFechaReserva(inputReservaDTO.getFechaReserva());
        setHoraReserva(inputReservaDTO.getHoraReserva());
        setBus(inputReservaDTO.getBus());
    }
}
