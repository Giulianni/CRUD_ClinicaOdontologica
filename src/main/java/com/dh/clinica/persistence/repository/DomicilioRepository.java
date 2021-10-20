package com.dh.clinica.persistence.repository;

import com.dh.clinica.dto.DomicilioDto;
import com.dh.clinica.persistence.entities.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio,Integer> {
}
