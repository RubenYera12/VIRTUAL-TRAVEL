package com.Ruben.BackEmpresa.email.infrastructure.controllers;

import com.Ruben.BackEmpresa.email.application.EmailService;
import com.Ruben.BackEmpresa.email.domain.Email;
import com.Ruben.BackEmpresa.email.infrastructure.dto.OutputEmailDTO;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v0/correos")
@AllArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("sentTo")
    public ResponseEntity<List<OutputEmailDTO>> emailSentTo(@RequestParam String correo) {

        List<OutputEmailDTO> outputEmailDTOList = new ArrayList<>();
        for (Email email : emailService.findByEmail(correo)) {
            outputEmailDTOList.add(new OutputEmailDTO(email));
        }
        return ResponseEntity.ok(outputEmailDTOList);
    }

    @GetMapping("getCorreos")
    public ResponseEntity<List<OutputEmailDTO>> getCorreos(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInferior,
                                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaSuperior, @RequestParam(defaultValue = "%") String ciudadDestino, @RequestParam(defaultValue = "00") @NumberFormat(pattern = "99") Float horaInferior,
                                           @RequestParam(defaultValue = "24") @NumberFormat(pattern = "99") Float horaSuperior) {
        List<OutputEmailDTO> outputEmailDTOList = new ArrayList<>();
        for (Email email : emailService.getCorreos(ciudadDestino, fechaInferior, fechaSuperior, horaInferior, horaSuperior)) {
            outputEmailDTOList.add(new OutputEmailDTO(email));
        }
        return ResponseEntity.ok(outputEmailDTOList);
    }
}
