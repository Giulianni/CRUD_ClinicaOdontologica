package com.dh.clinica.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dh.clinica.persistence.entities.Odontologo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo,Integer> {

    @Query("SELECT o FROM Odontologo o where o.nombre = ?1")
    Optional<List<Odontologo>> buscar(String nombre);

    @Query("SELECT o FROM Odontologo o where o.nombre = ?1 and o.apellido = ?2")
    Optional<List<Odontologo>> buscar(String nombre, String apellido);
}
