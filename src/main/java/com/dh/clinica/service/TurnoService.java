package com.dh.clinica.service;

import com.dh.clinica.dto.DomicilioDto;
import com.dh.clinica.dto.OdontologoDto;
import com.dh.clinica.dto.PacienteDto;
import com.dh.clinica.dto.TurnoDto;
import com.dh.clinica.persistence.entities.Domicilio;
import com.dh.clinica.persistence.entities.Odontologo;
import com.dh.clinica.persistence.entities.Paciente;
import com.dh.clinica.persistence.entities.Turno;
import com.dh.clinica.persistence.repository.TurnoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    ObjectMapper mapper;
    private static final Logger logger = Logger.getLogger(TurnoService.class);

    public Optional<TurnoDto> save(TurnoDto turnoDto) throws NotFoundException {
       Optional<PacienteDto> pacienteDto = pacienteService.getById(turnoDto.getPaciente().getId());
        Optional<OdontologoDto> odontologoDto = odontologoService.getById(turnoDto.getOdontologo().getId());
        if (pacienteDto.isEmpty() || odontologoDto.isEmpty()) {
            throw new NotFoundException("Error al obtener datos para guardar el turno.");
        }
        Paciente paciente = mapper.convertValue(pacienteDto, Paciente.class);
        Odontologo odontologo = mapper.convertValue(odontologoDto, Odontologo.class);
        Turno turno = mapper.convertValue(turnoDto, Turno.class);

        turno.setOdontologo(odontologo);
        turno.setPaciente(paciente);

        turno = turnoRepository.save(turno);
        return Optional.of(mapper.convertValue(turno, TurnoDto.class));
    }

    public List<TurnoDto> getAll() {
        List<Turno> turnos = turnoRepository.findAll();
        return turnos.stream()
                .map(turno -> mapper.convertValue(turno, TurnoDto.class))
                .collect(Collectors.toList());
    }

    public Optional<TurnoDto> getById(Integer turnoId) {
        Optional<Turno> turno = turnoRepository.findById(turnoId);
        return Optional.ofNullable(mapper.convertValue(turno.orElse(null), TurnoDto.class));
    }

    public void delete(Integer id) {
        turnoRepository.deleteById(id);
    }

    public Optional<TurnoDto> update(TurnoDto turnoDto) throws NotFoundException {
        Optional<Turno> result = turnoRepository.findById(turnoDto.getId());
        if (result.isEmpty()) {
            throw new NotFoundException("Error al obtener datos para actualizar el turno.");
        }
        Paciente paciente;
        if (turnoDto.getPaciente() != null && turnoDto.getPaciente().getId() != null) {
            Optional<PacienteDto> pacienteDto = pacienteService.getById(turnoDto.getPaciente().getId());
            if (pacienteDto.isEmpty()) {
                throw new NotFoundException("Error al obtener datos del paciente al actualizar el turno.");
            }
            paciente = mapper.convertValue(pacienteDto.get(), Paciente.class);
        } else {
            paciente = result.get().getPaciente();
        }
        Odontologo odontologo;
        if (turnoDto.getOdontologo() != null && turnoDto.getOdontologo().getId() != null) {
            Optional<OdontologoDto> odontologoDto = odontologoService.getById(turnoDto.getOdontologo().getId());
            if (odontologoDto.isEmpty()) {
                throw new NotFoundException("Error al obtener datos del paciente al actualizar el turno.");
            }
            odontologo = mapper.convertValue(odontologoDto.get(), Odontologo.class);
        } else {
            odontologo = result.get().getOdontologo();
        }

        Turno turno = result.get();
        turno.setFecha(Optional.ofNullable(turnoDto.getFecha()).orElse(turno.getFecha()));
        turno.setHora(Optional.ofNullable(turnoDto.getHora()).orElse(turno.getHora()));
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);

        Turno turnoUpdated = turnoRepository.save(turno);
        return Optional.ofNullable(mapper.convertValue(turnoUpdated, TurnoDto.class));

    }

    public List<TurnoDto> getNextTurnos() {
        List<Turno> turnos = turnoRepository.findByFechaBetween(LocalDate.now(), LocalDate.now().plusWeeks(1));
        return turnos.stream()
                .map(turno -> mapper.convertValue(turno, TurnoDto.class))
                .collect(Collectors.toList());
    }
}
