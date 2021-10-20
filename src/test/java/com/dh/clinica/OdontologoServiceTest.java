package com.dh.clinica;

import com.dh.clinica.dto.OdontologoDto;
import com.dh.clinica.service.OdontologoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OdontologoServiceTest {

    @Autowired
    OdontologoService odontologoService;

    @Test
    public void testSaveOdontologo()
    {
        OdontologoDto odontologo = new OdontologoDto();
        odontologo.setNombre("Jorge");
        odontologo.setApellido("Pepin");
        odontologo.setMatricula("A-345");

        Optional<OdontologoDto> odontologoGuardado = odontologoService.save(odontologo);
        assertTrue(odontologoGuardado.isPresent());
        OdontologoDto odontologoGuardado1 = odontologoGuardado.get();
        assertEquals(odontologoGuardado1.getNombre(), odontologo.getNombre());
        assertEquals(odontologoGuardado1.getMatricula(), odontologo.getMatricula());
    }

    @Test
    public void testSaveOdontologoRoto()
    {
        assertThrows(InvalidDataAccessApiUsageException.class,
                ()->{
                    //do whatever you want to do here
                    odontologoService.save(null);
                });
    }
}