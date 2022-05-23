package com.Ruben.BackEmpresa.user.application;

import com.Ruben.BackEmpresa.user.domain.User;

public interface UserService {
    User findByCorreo(String correo) throws Exception;
    User addUser(User user) throws Exception;
}
