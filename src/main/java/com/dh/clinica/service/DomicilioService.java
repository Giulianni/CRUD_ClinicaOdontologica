package com.dh.clinica.service;

import com.dh.clinica.dto.DomicilioDto;
import com.dh.clinica.dto.OdontologoDto;
import com.dh.clinica.persistence.entities.Domicilio;
import com.dh.clinica.persistence.entities.Odontologo;
import com.dh.clinica.persistence.repository.DomicilioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DomicilioService {

    @Autowired
    private DomicilioRepository domicilioRepository;
    @Autowired
    ObjectMapper mapper;

    private static final Logger logger = Logger.getLogger(DomicilioService.class);

    public Optional<DomicilioDto> save(DomicilioDto domicilioDto) {
        Domicilio domicilio = mapper.convertValue(domicilioDto, Domicilio.class);
        domicilio = domicilioRepository.save(domicilio);
        return Optional.ofNullable(mapper.convertValue(domicilio, DomicilioDto.class));
    }

    public List<DomicilioDto> getAll() {
        List<Domicilio> domicilios = domicilioRepository.findAll();
        return domicilios.stream()
                .map(domicilio -> mapper.convertValue(domicilio, DomicilioDto.class))
                .collect(Collectors.toList());
    }

    public Optional<DomicilioDto> getById(Integer domicilioId) {
        Optional<Domicilio> domicilio = domicilioRepository.findById(domicilioId);
        return Optional.ofNullable(mapper.convertValue(domicilio.orElse(null), DomicilioDto.class));
    }

    public void delete(Integer domicilioId) {
        domicilioRepository.deleteById(domicilioId);
    }

    public Optional<DomicilioDto> update(DomicilioDto domicilioDto) throws NotFoundException {
        /*
        * 1. Obtenemos un domDto me lo da el cliente
        * 2. Buscamos (la entidad) en el repositorio el Domicilio  a partir de un domicilioId (viene de domDto.getId())
        * 3. Actualiza Domicilio a partir del DTO que me dio el cliente
        * 4. Guradar el domicilio actualizado en el repositorio
        * 5. Convertir el domicilio actualizado a DTO
        * 6. Retorno el domicilioDto
        * */
        Optional<Domicilio> result = domicilioRepository.findById(domicilioDto.getId());
        if (result.isEmpty()) {
            logger.debug(String.format("No se encuentra el domicilio con id %s", domicilioDto.getId()));
            throw new NotFoundException("El domicilio no se encuentra en la base de datos.");
        }
        Domicilio domicilio = result.get();
        domicilio.setCalle(Optional.ofNullable(domicilioDto.getCalle()).orElse(domicilio.getCalle()));
        domicilio.setNumero(Optional.ofNullable(domicilioDto.getNumero()).orElse(domicilio.getNumero()));
        domicilio.setLocalidad(Optional.ofNullable(domicilioDto.getLocalidad()).orElse(domicilio.getLocalidad()));
        domicilio.setProvincia(Optional.ofNullable(domicilioDto.getProvincia()).orElse(domicilio.getProvincia()));
        Domicilio domicilioUpdated = domicilioRepository.save(domicilio);
        return Optional.ofNullable(mapper.convertValue(domicilioUpdated, DomicilioDto.class));
    }
}
