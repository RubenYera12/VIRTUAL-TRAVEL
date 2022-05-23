package com.Ruben.BackWeb.email.application;

import com.Ruben.BackWeb.email.domain.Email;
import com.Ruben.BackWeb.email.infrastructure.repository.EmailRepository;
import com.Ruben.BackWeb.reserva.domain.Reserva;
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
        message.setTo(reserva.getCorreo());
        message.setSubject("Su reserva ha sido confirmada.");
        String contenido = "Buenas " + reserva.getNombre() + ", le informamos que su viaje con destino " + reserva.getCiudadDestino() + " ha sido confirmado.\n" +
                "Datos de la reserva:\n" + "Fecha del viaje: " + new Date(reserva.getFechaReserva().getTime()).toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " a las " + String.format("%.2f", reserva.getHoraReserva()) + "\n Un saludo.";
        message.setText(contenido);
        emailSender.send(message);
        Email email = new Email(reserva.getCiudadDestino(), reserva.getCorreo(), reserva.getFechaReserva(), reserva.getHoraReserva());
        emailRepository.save(email);
    }

    @Override
    public void emailCancelacionViaje(Reserva reserva) throws UnsupportedEncodingException{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(String.valueOf(new InternetAddress("no_reply@example.com", "Virtual Travel")));
        message.setTo(reserva.getCorreo());
        message.setSubject("Su viaje ha sido cancelada.");
        String contenido = "Buenas " + reserva.getNombre() + ", le informamos que su viaje con destino " + reserva.getCiudadDestino() + " ha sido cancelado por motivos internos de la compañia.\n" +
                "Datos de la reserva:\n" + "Fecha del viaje: " + new Date(reserva.getFechaReserva().getTime()).toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " a las " + String.format("%.2f", reserva.getHoraReserva()) +
                "\n Un saludo.";
        message.setText(contenido);
        emailSender.send(message);
        Email email = new Email(reserva.getCiudadDestino(), reserva.getCorreo(), reserva.getFechaReserva(), reserva.getHoraReserva());
        emailRepository.save(email);
    }

    @Override
    public void emailCancelacionReserva(Reserva reserva) throws UnsupportedEncodingException{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(String.valueOf(new InternetAddress("no_reply@example.com", "Virtual Travel")));
        message.setTo(reserva.getCorreo());
        message.setSubject("Su reserva ha sido cancelada.");
        String contenido = "Buenas " + reserva.getNombre() + ", le informamos que su viaje con destino " + reserva.getCiudadDestino() + " ha sido cancelado.\n" +
                "Datos de la reserva:\n" + "Fecha del viaje: " + new Date(reserva.getFechaReserva().getTime()).toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " a las " + String.format("%.2f", reserva.getHoraReserva()) +
                "\n Un saludo.";
        message.setText(contenido);
        emailSender.send(message);
        Email email = new Email(reserva.getCiudadDestino(), reserva.getCorreo(), reserva.getFechaReserva(), reserva.getHoraReserva());
        emailRepository.save(email);
    }

    @Override
    public List<Email> findByEmail(String email){
        return emailRepository.findByEmail(email);
    }

    @Override
    public List<Email> getCorreos(String ciudadDestino, java.util.Date fechaInferior, java.util.Date fechaSuperior, Float horaInferior, Float horaSuperior) {
        return emailRepository.findByCiudadDestinoLikeAndFechaReservaBetweenAndHoraReservaBetween(ciudadDestino, fechaInferior, fechaSuperior, horaInferior, horaSuperior);
    }

    @Override
    public void listenTopic(String s, Email readValue) {

    }

}
