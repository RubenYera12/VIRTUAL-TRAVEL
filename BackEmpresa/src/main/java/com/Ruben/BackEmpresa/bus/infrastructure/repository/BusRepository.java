package com.Ruben.BackEmpresa.bus.infrastructure.repository;

import com.Ruben.BackEmpresa.bus.domain.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus,String> {
}
