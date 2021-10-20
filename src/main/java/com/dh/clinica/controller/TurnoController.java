package com.dh.clinica.controller;

import com.dh.clinica.dto.PacienteDto;
import com.dh.clinica.dto.TurnoDto;
import com.dh.clinica.persistence.entities.Odontologo;
import com.dh.clinica.persistence.entities.Turno;
import com.dh.clinica.service.OdontologoService;
import com.dh.clinica.service.TurnoService;
import javassist.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;
    private static final Logger logger = Logger.getLogger(TurnoController.class);

    @PostMapping("")
    public ResponseEntity<TurnoDto> create(@RequestBody TurnoDto turno) {
        Optional<TurnoDto> result;
        try {
            result = turnoService.save(turno);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return result.isPresent() ?  ResponseEntity.ok(result.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/proximosTurnos")
    public ResponseEntity<List<TurnoDto>> getNextTurnos() {
        return ResponseEntity.ok(turnoService.getNextTurnos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDto> getById(@PathVariable Integer id) {
        Optional<TurnoDto> result = turnoService.getById(id);
        return result.isPresent() ? ResponseEntity.ok(result.get()) :  ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            turnoService.delete(id);
        } catch (Exception e) {
            logger.debug(String.format("No se encuentra el turno con id %s", id));
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(String.format("El turno %s fue eliminado", id));
    }

    @PutMapping("")
    public ResponseEntity<TurnoDto> update(@RequestBody TurnoDto turno) {
        Optional<TurnoDto> result;
        try {
            result = turnoService.update(turno);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result.get());
    }
}
