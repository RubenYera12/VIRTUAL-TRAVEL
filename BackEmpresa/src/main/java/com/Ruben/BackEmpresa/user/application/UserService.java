package com.Ruben.BackEmpresa.user.application;

import com.Ruben.BackEmpresa.shared.exceptions.NotFoundException;
import com.Ruben.BackEmpresa.shared.exceptions.UnprocesableException;
import com.Ruben.BackEmpresa.user.domain.User;

public interface UserService {
    User findByCorreo(String correo) throws NotFoundException;
    void addUser(User user) throws UnprocesableException;
}
