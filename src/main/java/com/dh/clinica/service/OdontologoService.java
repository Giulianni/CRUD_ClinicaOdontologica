package com.dh.clinica.service;

import com.dh.clinica.dto.OdontologoDto;
import com.dh.clinica.persistence.entities.Odontologo;
import com.dh.clinica.persistence.repository.OdontologoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OdontologoService {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private OdontologoRepository odontologoRepository;
    private static final Logger logger = Logger.getLogger(OdontologoService.class);

    public Optional<OdontologoDto> save(OdontologoDto odontologoDto) {
        Odontologo odontologo = mapper.convertValue(odontologoDto, Odontologo.class); //.convertValue() maneja un posible valor null durante la serializacion.
        Optional<Odontologo> odontologoSaved = Optional.ofNullable(odontologoRepository.save(odontologo));

        if (odontologoSaved.isEmpty()) {
            throw new InternalError("No se pudo guardar al odontologo");
        }

        return Optional.ofNullable(mapper.convertValue(odontologoSaved.get(), OdontologoDto.class));
    }

    public List<OdontologoDto> getAll() {

        List<Odontologo> odontologos = odontologoRepository.findAll();

        return odontologos.stream()
                .map(odontologo -> mapper.convertValue(odontologo, OdontologoDto.class))
                .collect(Collectors.toList());
    }

    public Optional<OdontologoDto> getById(Integer odontologoId) {
        Odontologo odontologo = odontologoRepository.findById(odontologoId).orElse(null);
        OdontologoDto odontologoDto = mapper.convertValue(odontologo, OdontologoDto.class);
        return Optional.ofNullable(odontologoDto);
    }

    public void delete(Integer odontologoId) {
        odontologoRepository.deleteById(odontologoId);
    }

    public Optional<OdontologoDto> update(OdontologoDto odontologoDto) throws NotFoundException {
        Optional<Odontologo> result = odontologoRepository.findById(odontologoDto.getId());

        if (result.isEmpty()) {
            logger.debug(String.format("No se encuentra el odontologo con id %s", odontologoDto.getId()));
            throw new NotFoundException("El odontologo no se encuentra en la base de datos.");
        }
        Odontologo odontologo = result.get();
        odontologo.setNombre(Optional.ofNullable(odontologoDto.getNombre()).orElse(odontologo.getNombre()));
        odontologo.setApellido(Optional.ofNullable(odontologoDto.getApellido()).orElse(odontologo.getApellido()));
        odontologo.setMatricula(Optional.ofNullable(odontologoDto.getMatricula()).orElse(odontologo.getMatricula()));
        Odontologo odontologoUpdated = odontologoRepository.save(odontologo);

        return Optional.ofNullable(mapper.convertValue(odontologoUpdated, OdontologoDto.class));
    }


}
