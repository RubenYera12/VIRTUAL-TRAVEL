package com.Ruben.BackEmpresa.bus.infrastructure.repository;

import com.Ruben.BackEmpresa.bus.domain.Bus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BusRepositoryImpl {
    @PersistenceContext private EntityManager entityManager;

    Bus getData(HashMap<String,Object> conditions){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bus> query = cb.createQuery(Bus.class);
        Root<Bus> root = query.from(Bus.class);

        List<Predicate> predicates = new ArrayList<>();

        conditions.forEach((field, value)->{
            switch (field){
                case "ciudadDestino" -> predicates.add(cb.like(root.get(field),"%"+value+"%"));
//                case "fechaReserva"-> predicates.add(cb.equal(root.<Date>get(field),value));
                case "horaReserva"-> predicates.add(cb.equal(root.get(field),value));
            }
        });
        query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(query).getResultList().get(0);
    }
}
