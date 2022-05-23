package com.Ruben.BackWeb.user.application;

import com.Ruben.BackWeb.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public Optional<User> findByCorreo(String correo) {
        return null;
    }
}
