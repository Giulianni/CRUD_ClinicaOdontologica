package com.dh.clinica.service;

import com.dh.clinica.dto.DomicilioDto;
import com.dh.clinica.dto.PacienteDto;
import com.dh.clinica.persistence.entities.Domicilio;
import com.dh.clinica.persistence.entities.Paciente;
import com.dh.clinica.persistence.repository.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private DomicilioService domicilioService;
    @Autowired
    ObjectMapper mapper;
    private static final Logger logger = Logger.getLogger(PacienteService.class);

    public Optional<PacienteDto> save(PacienteDto pacienteDto) throws NotFoundException {
        /*
        * 1. Valido domicilioId del pacienteDTO: Si existe el domicilioId, obtengo el Domicilio del repositrio, sino creo el domicilio
        * 1a. Valido existencia de domicilio
        * 2. Convierto pacienteDTO a Paciente (xq es lo que voy a tener que guardar en el repositrio)
        * 3. Guardo el domiclio en el paciente (ahora mi paciente esta completo de informacion)
        * 4. Guardo el paciente en el repositorio (esto me retorna el paciente)
        * 5. Transformo el paciente a pacienteDto
        * 6. Retorno el optional del pacienteDto
        * */
        Optional<DomicilioDto> pacienteDomicilio = pacienteDto.getDomicilioId().isPresent() ? domicilioService.getById(pacienteDto.getDomicilioId().get()) : domicilioService.save(pacienteDto.getDomicilio());
        if (pacienteDomicilio.isEmpty()) {
            logger.debug(String.format("Error al obtener domicilio del paciente. Docimilio: %s", pacienteDto.getDomicilio().toString()));
            throw new NotFoundException("El domicilio asociado no se encuentra en la base de datos.");
        }
        Paciente paciente = mapper.convertValue(pacienteDto, Paciente.class);
        paciente.setDomicilio(mapper.convertValue(pacienteDomicilio.get(), Domicilio.class));
        paciente = pacienteRepository.save(paciente);
        return Optional.of(mapper.convertValue(paciente, PacienteDto.class));
    }

    public List<PacienteDto> getAll() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        return pacientes.stream()
                .map(paciente -> mapper.convertValue(paciente, PacienteDto.class))
                .collect(Collectors.toList());
    }

    public Optional<PacienteDto> getById(Integer pacienteId) {
        Optional<Paciente> paciente = pacienteRepository.findById(pacienteId);
        return Optional.ofNullable(mapper.convertValue(paciente.orElse(null), PacienteDto.class));
    }

    public void delete(Integer id) {
        pacienteRepository.deleteById(id);
    }

    public Optional<PacienteDto> update(PacienteDto pacienteDto) throws NotFoundException {
        Optional<Paciente> result = pacienteRepository.findById(pacienteDto.getId());
        if (result.isEmpty()) {
            logger.debug(String.format("No se encuentra el paciente con id %s", pacienteDto.getId()));
            throw new NotFoundException("El paciente no se encuentra en la base de datos.");
        }
        Paciente paciente = result.get();
        paciente.setNombre(Optional.ofNullable(pacienteDto.getNombre()).orElse(paciente.getNombre()));
        paciente.setApellido(Optional.ofNullable(pacienteDto.getApellido()).orElse(paciente.getApellido()));
        paciente.setDni(Optional.ofNullable(pacienteDto.getDni()).orElse(paciente.getDni()));
        paciente.setFechaIngreso(Optional.ofNullable(pacienteDto.getFechaIngreso()).orElse(paciente.getFechaIngreso()));
        Paciente pacienteUpdated = pacienteRepository.save(paciente);
        return Optional.ofNullable(mapper.convertValue(pacienteUpdated, PacienteDto.class));
    }
}