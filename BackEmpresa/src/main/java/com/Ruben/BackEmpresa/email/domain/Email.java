package com.Ruben.BackEmpresa.email.domain;

import com.Ruben.BackEmpresa.StringPrefixedSequenceIdGenerator;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emailSeq")
    @GenericGenerator(
            name = "emailSeq",
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


}
