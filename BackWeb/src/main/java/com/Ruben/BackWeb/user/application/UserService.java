package com.Ruben.BackWeb.user.application;

import com.Ruben.BackWeb.user.domain.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByCorreo(String correo);
}
