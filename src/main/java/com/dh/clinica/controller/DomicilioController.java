package com.dh.clinica.controller;

import com.dh.clinica.dto.DomicilioDto;
import com.dh.clinica.persistence.entities.Domicilio;
import com.dh.clinica.service.DomicilioService;
import javassist.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/domicilios")
public class DomicilioController {

    @Autowired
    private DomicilioService domicilioService;
    private static final Logger logger = Logger.getLogger(DomicilioController.class);

    @PostMapping("")
    public ResponseEntity<DomicilioDto> create(@RequestBody DomicilioDto domicilioDto) {
        Optional<DomicilioDto> result = domicilioService.save(domicilioDto);
        return result.isPresent() ?  ResponseEntity.ok(result.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("")
    public ResponseEntity<List<DomicilioDto>> getAll() {
        return ResponseEntity.ok(domicilioService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DomicilioDto> getById(@PathVariable Integer id) {
        Optional<DomicilioDto> result = domicilioService.getById(id);
        return result.isPresent() ? ResponseEntity.ok(result.get()) :  ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            domicilioService.delete(id);
        } catch (Exception e) {
            logger.debug(String.format("No se encuentra el domicilio con id %s", id));
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(String.format("El domicilio %s fue eliminado", id));
    }

    @PutMapping("")
    public ResponseEntity<DomicilioDto> update(@RequestBody DomicilioDto domicilio) {
        Optional<DomicilioDto> result;
        try {
            result = domicilioService.update(domicilio);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result.get());
    }

}
