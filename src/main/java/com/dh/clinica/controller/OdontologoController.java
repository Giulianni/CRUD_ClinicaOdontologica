package com.dh.clinica.controller;

import com.dh.clinica.dto.OdontologoDto;
import com.dh.clinica.persistence.entities.Odontologo;
import com.dh.clinica.service.OdontologoService;
import javassist.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    @Autowired
    private OdontologoService odontologoService;
    private static final Logger logger = Logger.getLogger(OdontologoController.class);

    @PostMapping("")
    public ResponseEntity<OdontologoDto> create(@RequestBody OdontologoDto odontologoDto) {
        Optional<OdontologoDto> result = odontologoService.save(odontologoDto);
        return result.isPresent() ?  ResponseEntity.ok(result.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("")
    public ResponseEntity<List<OdontologoDto>> getAll() {
        return ResponseEntity.ok(odontologoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontologoDto> getById(@PathVariable Integer id) {
        Optional<OdontologoDto> result = odontologoService.getById(id);
        return result.isPresent() ? ResponseEntity.ok(result.get()) :  ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            odontologoService.delete(id);
        } catch (Exception e) {
            logger.debug(String.format("No se encuentra el odontologo con id %s", id));
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(String.format("El odontologo %s fue eliminado", id));
    }

    @PutMapping("")
    public ResponseEntity<OdontologoDto> update(@RequestBody OdontologoDto odontologoDto) {
        Optional<OdontologoDto> result;
        try {
            result = odontologoService.update(odontologoDto);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result.get());
    }
}
