package com.Ruben.BackEmpresa.user.application;

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
    public User findByCorreo(String correo) throws Exception {
        return userRepository.findByCorreo(correo).orElseThrow(()->new Exception("No existe un usuario con este correo: "+correo));
    }

    @Override
    public User addUser(User user) throws Exception {
        Optional<User> userCheck = userRepository.findByCorreo(user.getCorreo());
        if (userCheck.isPresent())
            throw new Exception("Ya existe un usuario con este correo: "+user.getCorreo());
        return userRepository.save(user);
    }
}
