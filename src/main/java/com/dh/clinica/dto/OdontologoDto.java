package com.dh.clinica.dto;

import com.dh.clinica.persistence.entities.Odontologo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OdontologoDto {

    private Integer id;
    private String nombre;
    private String apellido;
    private String matricula;

    public OdontologoDto() {
    }

    public OdontologoDto(String nombre, String apellido, String matricula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }

}
