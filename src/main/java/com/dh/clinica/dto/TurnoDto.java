package com.dh.clinica.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.*;

@Setter
@Getter
public class TurnoDto {

    private Integer id;
    private LocalDate fecha;
    private LocalTime hora;
    private PacienteDto paciente;
    private OdontologoDto odontologo;


    public TurnoDto() {
    }

    public TurnoDto(LocalDate fecha, LocalTime hora, PacienteDto paciente, OdontologoDto odontologo) {
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fecha = fecha;
        this.hora = hora;
    }

}
