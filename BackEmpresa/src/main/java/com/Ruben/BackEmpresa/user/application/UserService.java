package com.Ruben.BackEmpresa.user.application;

import com.Ruben.BackEmpresa.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByCorreo(String correo);
}
