package com.Ruben.BackEmpresa.email.application;

import com.Ruben.BackEmpresa.email.domain.Email;
import com.Ruben.BackEmpresa.email.infrastructure.repository.EmailRepository;
import com.Ruben.BackEmpresa.reserva.domain.Reserva;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender emailSender;
    private final EmailRepository emailRepository;

    @Override
    public void emailConfirmacion(Reserva reserva) throws UnsupportedEncodingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(String.valueOf(new InternetAddress("no_reply@example.com", "Virtual Travel")));
        message.setTo(reserva.getEmail());
        message.setSubject("Su reserva ha sido confirmada.");
        String contenido = "Buenas " + reserva.getNombre() + ", le informamos que su viaje con destino " + reserva.getCiudadDestino() + " ha sido confirmado.\n" +
                "Datos de la reserva:\n" + "Fecha del viaje: " + new Date(reserva.getFechaReserva().getTime()).toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " a las " + String.format("%.2f", reserva.getHoraReserva()) + "\n Un saludo.";
        message.setText(contenido);
        emailSender.send(message);
        Email email = new Email(reserva.getCiudadDestino(), reserva.getEmail(), reserva.getFechaReserva(), reserva.getHoraReserva());
        emailRepository.save(email);
    }

    @Override
    public void emailCancelacion(Reserva reserva) throws UnsupportedEncodingException{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(String.valueOf(new InternetAddress("no_reply@example.com", "Virtual Travel")));
        message.setTo(reserva.getEmail());
        message.setSubject("Su reserva ha sido cancelada.");
        String contenido = "Buenas " + reserva.getNombre() + ", le informamos que su viaje con destino " + reserva.getCiudadDestino() + " ha sido cancelado.\n" +
                "Datos de la reserva:\n" + "Fecha del viaje: " + new Date(reserva.getFechaReserva().getTime()).toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " a las " + String.format("%.2f", reserva.getHoraReserva()) +
                "\n Un saludo.";
        message.setText(contenido);
        emailSender.send(message);
        Email email = new Email(reserva.getCiudadDestino(), reserva.getEmail(), reserva.getFechaReserva(), reserva.getHoraReserva());
        emailRepository.save(email);
    }

    @Override
    public List<Email> findByEmail(String email){
        return emailRepository.findByEmail(email);
    }

}