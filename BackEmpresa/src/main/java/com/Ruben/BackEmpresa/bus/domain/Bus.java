package com.Ruben.BackEmpresa.bus.domain;

import com.Ruben.BackEmpresa.StringPrefixedSequenceIdGenerator;
import com.Ruben.BackEmpresa.bus.infrastructure.dto.InputBusDTO;
import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "busSeq")
    @GenericGenerator(
            name = "busSeq",
            strategy = "com.Ruben.BackEmpresa.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "BUS"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
            })
    private String id;
    private final int capacidad = 40;
    @OneToMany
    @JsonIgnore
    private List<Reserva> reservas;
    private String ciudadDestino;
    @Temporal(TemporalType.DATE)
    private Date fechaReserva;
    private Float horaReserva;
    private String estado = "ACTIVO";

    public Bus(InputBusDTO inputBusDTO){
        setId(inputBusDTO.getId());
        setReservas(inputBusDTO.getReservas());
        setCiudadDestino(inputBusDTO.getCiudadDestino());
        setFechaReserva(inputBusDTO.getFechaReserva());
        setHoraReserva(inputBusDTO.getHoraReserva());
        setEstado(inputBusDTO.getEstado());
    }
}
