package com.Ruben.BackEmpresa.shared.security;

import com.Ruben.BackEmpresa.user.application.UserService;
import com.Ruben.BackEmpresa.user.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoginController {

    @Autowired UserService userService;

    @GetMapping("login")
    public ResponseEntity<String> login(
            @RequestParam("correo") String correo, @RequestParam("password") String pwd)
            throws Exception {
        User user = userService.findByCorreo(correo).orElseThrow(()->new Exception("Usuario " + correo + " no encontrado"));
        String password = user.getPassword();

        if (!pwd.equals(password)) throw new Exception("Password erroneo");
        String rol = (user.isAdmin()) ? "ROLE_ADMIN" : "ROLE_USER";
        return new ResponseEntity<>(getJWTToken(correo, rol), HttpStatus.OK);
    }

    private String getJWTToken(String username, String rol) {

        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities =
                AuthorityUtils.commaSeparatedStringToAuthorityList(rol);

        String token =
                Jwts.builder()
                        .setId("softtekJWT")
                        .setSubject(username)
                        .claim(
                                "authorities",
                                grantedAuthorities.stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList()))
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 600000))
                        .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
                        .compact();

        return "Bearer " + token;
    }
}
