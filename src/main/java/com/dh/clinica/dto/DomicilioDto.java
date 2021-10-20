package com.dh.clinica.dto;

import com.dh.clinica.persistence.entities.Domicilio;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DomicilioDto {

    private Integer id;
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;

    public DomicilioDto() {
    }

    public DomicilioDto(Integer id, String calle, String numero, String localidad, String provincia) {
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public DomicilioDto(String calle, String numero, String localidad, String provincia) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public DomicilioDto(Domicilio domicilio){
        id = domicilio.getId();
        calle = domicilio.getCalle();
        numero = domicilio.getNumero();
        localidad = domicilio.getLocalidad();
        provincia = domicilio.getProvincia();
    }

    public Domicilio toEntity() {
        return new Domicilio(id, calle, numero, localidad, provincia);
    }

}
