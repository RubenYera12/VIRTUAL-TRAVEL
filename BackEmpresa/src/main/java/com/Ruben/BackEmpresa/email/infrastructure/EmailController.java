package com.Ruben.BackEmpresa.email.infrastructure;

import com.Ruben.BackEmpresa.email.application.EmailService;
import com.Ruben.BackEmpresa.email.domain.Email;
import com.Ruben.BackEmpresa.email.infrastructure.dto.OutputEmailDTO;
import com.Ruben.BackEmpresa.email.infrastructure.repository.EmailRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/Empresa/correos")
@AllArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("sentTo")
    public List<OutputEmailDTO> emailSentTo(@RequestParam String correo){

        List<OutputEmailDTO> outputEmailDTOList = new ArrayList<>();
        for (Email email: emailService.findByEmail(correo)) {
            outputEmailDTOList.add(new OutputEmailDTO(email));
        }
        return outputEmailDTOList;
    }
}
