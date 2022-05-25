package com.Ruben.BackEmpresa.user.application;

import com.Ruben.BackEmpresa.shared.exceptions.NotFoundException;
import com.Ruben.BackEmpresa.shared.exceptions.UnprocesableException;
import com.Ruben.BackEmpresa.user.domain.User;
import com.Ruben.BackEmpresa.user.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public User findByCorreo(String correo) throws NotFoundException {
        return userRepository.findByCorreo(correo).orElseThrow(()->new NotFoundException("No existe un usuario con este correo: "+correo));
    }

    @Override
    public void addUser(User user) throws UnprocesableException {
        Optional<User> userCheck = userRepository.findByCorreo(user.getCorreo());
        if (userCheck.isPresent())
            throw new UnprocesableException("Ya existe un usuario con este correo: "+user.getCorreo());
        userRepository.save(user);
    }
}
