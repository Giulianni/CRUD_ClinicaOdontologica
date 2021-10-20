package com.dh.clinica.persistence.entities;

import javax.persistence.*;
import java.time.*;

@Entity
@Table(name = "turnos")
public class Turno {

    @Id
    @SequenceGenerator(name = "turnoSequence", sequenceName = "turnoSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turnoSequence")
    @Column(name = "turno_id")
    private Integer id;
    private LocalDate fecha;
    private LocalTime hora;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "odontologo_id", nullable = false)
    private Odontologo odontologo;

    public Turno() {
    }

    public Turno(LocalDate fecha, LocalTime hora, Paciente paciente, Odontologo odontologo) {
        this.fecha = fecha;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.hora = hora;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Odontologo getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }



    @Override
    public String toString() {
        return "Turno{" +
            "id=" + id +
            ", fecha=" + fecha +
            ", paciente=" + paciente +
            ", odontologo=" + odontologo +
        '}';
    }
}
