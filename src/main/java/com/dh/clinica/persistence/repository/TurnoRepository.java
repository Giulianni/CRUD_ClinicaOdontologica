package com.dh.clinica.persistence.repository;

import com.dh.clinica.persistence.entities.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno,Integer> {
    List<Turno> findByFechaBetween(LocalDate startDate, LocalDate endDate);
}
