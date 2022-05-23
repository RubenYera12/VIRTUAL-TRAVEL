package com.Ruben.BackWeb.user.infrastructure.repository;

import com.Ruben.BackWeb.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByCorreo(String correo);
}
