package com.Ruben.BackEmpresa.email.domain;

import com.Ruben.BackEmpresa.StringPrefixedSequenceIdGenerator;
import com.Ruben.BackEmpresa.bus.infrastructure.dto.InputBusDTO;
import com.Ruben.BackEmpresa.email.infrastructure.dto.InputEmailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EmailSeq")
    @GenericGenerator(
            name = "EmailSeq",
            strategy = "com.Ruben.BackEmpresa.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "EMAIL"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")
            })
    private String id;
    private String ciudadDestino;
    private String email;
    private Date fechaReserva;
    private Float horaReserva;

    public Email(String ciudadDestino,String email,Date fechaReserva, Float horaReserva){
        this.setCiudadDestino(ciudadDestino);
        this.setEmail(email);
        this.setFechaReserva(fechaReserva);
        this.setHoraReserva(horaReserva);
    }

    public Email(InputEmailDTO inputEmailDTO){
        this.setCiudadDestino(inputEmailDTO.getCiudadDestino());
        this.setEmail(inputEmailDTO.getEmail());
        this.setFechaReserva(inputEmailDTO.getFechaReserva());
        this.setHoraReserva(inputEmailDTO.getHoraReserva());
    }
}
