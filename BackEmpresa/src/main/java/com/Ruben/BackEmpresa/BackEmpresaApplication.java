package com.Ruben.BackEmpresa;

import com.Ruben.BackEmpresa.user.application.UserService;
import com.Ruben.BackEmpresa.user.domain.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackEmpresaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEmpresaApplication.class, args);
    }
//	@Bean
//	CommandLineRunner commandLineRunner(KafkaTemplate<String, Reserva> kafkaTemplate) {
//		return args -> {
//			kafkaTemplate.send("myNewTopic", "hasta luego kafka");
//		};
//	}

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            System.out.println(
                    userService.addUser(
                            new User(
                                    null,
                                    "Ruben",
                                    "Yera",
                                    "53452342",
                                    "ruben@gmail.com",
                                    "Ruben123",
                                    true)));
            System.out.println(
                    userService.addUser(
                            new User(
                                    null,
                                    "Pepe",
                                    "Lopez",
                                    "53452342",
                                    "pepe@gmail.com",
                                    "Ruben123",
                                    false)));
        };

    }


}
