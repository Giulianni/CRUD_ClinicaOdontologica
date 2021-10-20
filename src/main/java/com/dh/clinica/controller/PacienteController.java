package com.dh.clinica.controller;

import com.dh.clinica.dto.PacienteDto;
import com.dh.clinica.persistence.entities.Paciente;
import com.dh.clinica.service.PacienteService;
import javassist.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;
    private static final Logger logger = Logger.getLogger(PacienteController.class);

    @PostMapping("")
    public ResponseEntity<PacienteDto> create(@RequestBody PacienteDto paciente) {
        Optional<PacienteDto> result;
        try {
            result = pacienteService.save(paciente);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
        return result.isPresent() ?  ResponseEntity.ok(result.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("")
    public ResponseEntity<List<PacienteDto>> getAll() {
        return ResponseEntity.ok(pacienteService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteDto> getById(@PathVariable Integer id) {
        Optional<PacienteDto> result = pacienteService.getById(id);
        return result.isPresent() ? ResponseEntity.ok(result.get()) :  ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            pacienteService.delete(id);
        } catch (Exception e) {
            logger.debug(String.format("No se encuentra el paciente con id %s", id));
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(String.format("El paciente %s fue eliminado", id));
    }

    @PutMapping("")
    public ResponseEntity<PacienteDto> update(@RequestBody PacienteDto paciente) {
        Optional<PacienteDto> result;
        try {
            result = pacienteService.update(paciente);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result.get());
    }
}
