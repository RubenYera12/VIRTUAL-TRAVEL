package com.Ruben.BackEmpresa.shared.security.config;


import com.Ruben.BackEmpresa.shared.security.filter.JWTAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    //TODO: Quitar el metodo es solo para el desarrollo
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/login")
                .permitAll()
                .antMatchers( "/api/Empresa/Bus/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/api/Empresa/Reserva/cancel/trip")
                .hasAnyRole("ADMIN")
                .antMatchers("/api/Empresa/**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }
}