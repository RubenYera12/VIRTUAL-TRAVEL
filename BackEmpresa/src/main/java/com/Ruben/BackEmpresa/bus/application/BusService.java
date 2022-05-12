package com.Ruben.BackEmpresa.bus.application;

import com.Ruben.BackEmpresa.bus.domain.Bus;

import java.util.List;

public interface BusService {
    Bus addBus(Bus bus) throws Exception;
    List<Bus> findAll();
    Bus findById(String id) throws Exception;
    String deleteById(String id) throws Exception;
    Bus updateBus(Bus bus,String id) throws Exception;
}
