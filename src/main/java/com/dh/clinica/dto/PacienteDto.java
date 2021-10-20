package com.dh.clinica.dto;

import com.dh.clinica.persistence.entities.Paciente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Setter
@Getter
public class PacienteDto {

    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaIngreso;
    private DomicilioDto domicilio;

    public PacienteDto(){

    }

    public PacienteDto(String nombre, String apellido, String dni, LocalDate fechaIngreso, DomicilioDto domicilio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    public Paciente toEntity() {
        return new Paciente(id, nombre, apellido, dni, fechaIngreso, domicilio.toEntity());
    }

    @JsonIgnore
    public Optional<Integer> getDomicilioId() {
        return Optional.ofNullable(domicilio != null ? domicilio.getId() : null);
    }
}
