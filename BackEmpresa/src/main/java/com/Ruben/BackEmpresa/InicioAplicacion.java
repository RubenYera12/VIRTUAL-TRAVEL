package com.Ruben.BackEmpresa;

import com.Ruben.BackEmpresa.shared.exceptions.UnprocesableException;
import com.Ruben.BackEmpresa.shared.kafka.Producer.KafkaSender;
import com.Ruben.BackEmpresa.user.application.UserService;
import com.Ruben.BackEmpresa.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InicioAplicacion implements CommandLineRunner {
    private final UserService userService;
    @Autowired
    KafkaSender sender;


    @Override
    public void run(String... args) throws Exception {


        try {
            userService.addUser(
                    new User(
                            null,
                            "Ruben",
                            "Yera",
                            "53452342",
                            "ruben@gmail.com",
                            "Ruben123",
                            true));
        } catch (UnprocesableException e) {
            System.out.println("El usuario ruben@gmail.com ya existe");
        }
        try {
            userService.addUser(
                    new User(
                            null,
                            "Pepe",
                            "Lopez",
                            "53452342",
                            "pepe@gmail.com",
                            "Ruben123",
                            false));
        } catch (UnprocesableException e) {
            System.out.println("El usuario pepe@gmail.com ya existe");
        }
    }
}